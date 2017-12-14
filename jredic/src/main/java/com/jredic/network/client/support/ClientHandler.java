package com.jredic.network.client.support;

import com.jredic.network.protocol.data.Data;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * The client handler for dealing some event. .
 *
 * @author David.W
 */
public class ClientHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    //client ref.
    private DefaultClient client;

    //for redis data transmitting.
    private SynchronousQueue<Data> transmitter;

    //to execute reconnection task.
    private Executor reconnectExecutor;

    public ClientHandler(DefaultClient client, SynchronousQueue<Data> transmitter, Executor reconnectExecutor) {
        this.client = client;
        this.transmitter = transmitter;
        this.reconnectExecutor = reconnectExecutor;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) {
        try {
            //when finish the redis data reading,put it into the 'transmitter'.
            transmitter.put((Data) data);
        } catch (InterruptedException e) {
            ctx.fireExceptionCaught(e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("ClientHandler caught the exception!", cause);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        /*
         * we call the Client#isRunning() to find if we shutdown the client ourselves,
         * if we didn't shutdown client and it can't connect to the server, then we
         * start a task and try to reconnect to the server.
         */
        if(client.isRunning() && !client.isConnected()){
            reconnectExecutor.execute(new ReconnectTask(client, 5, 5, 5));
        }
    }

    /**
     * Reconnection Task.
     */
    private static class ReconnectTask implements Runnable{

        private DefaultClient client;
        //the first delay time for reconnect.
        private long initialDelay;
        //the max times to reconnect.
        private long reconnectTimes;
        /*
         * the increment time after first reconnect.
         * for example, if the first delay ('initialDelay') for reconnect is 10s,
         * and the 'delayIncrement' is 5s, then the second delay for reconnect is 15s,
         * and the third delay is 20s ans so on ,until the 'reconnectTimes'.
         */
        private long delayIncrement;

        public ReconnectTask(DefaultClient client, long initialDelay, long reconnectTimes, long delayIncrement) {
            this.client = client;
            this.initialDelay = initialDelay;
            this.reconnectTimes = reconnectTimes;
            this.delayIncrement = delayIncrement;
        }

        @Override
        public void run() {
            for(int i=0; i<reconnectTimes; i++){
                try {
                    //delay
                    TimeUnit.SECONDS.sleep(initialDelay + delayIncrement * i);
                    try {
                        logger.warn("start to reconnect to server...");
                        client.connect();
                        logger.warn("reconnect successful!");
                        //success
                        return;
                    } catch (Throwable e) {
                        logger.error("reconnect failed! [{}] times left!", reconnectTimes - 1 - i);
                        //continue to next try.
                    }
                } catch (InterruptedException e) {
                    //ignore
                }
            }
            //Unfortunately, we can't wait, bye world!
            logger.error("can't reconnect to server after trying {} times, so stop the client...", reconnectTimes);
            client.stop();
        }
    }

}
