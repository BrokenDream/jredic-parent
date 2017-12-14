package com.jredic.network.client.support;

import com.jredic.JredicException;
import com.jredic.network.client.Client;
import com.jredic.network.protocol.RESPDecoder;
import com.jredic.network.protocol.RESPEncoder;
import com.jredic.network.protocol.data.ArraysData;
import com.jredic.network.protocol.data.Data;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The default implementation base on Netty.
 *
 * @author David.W
 */
public class DefaultClient implements Client {

    //start flag
    private AtomicBoolean isStarted = new AtomicBoolean(false);

    //server host.
    private String serverHost;

    //server port.
    private int serverPort;

    //if true,we add a LoggingHandler.
    private boolean logFlag;

    //netty components
    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private volatile Channel channel;

    /*
     * the transmitter of Redis Data.
     * as we know that ops always asynchronous in netty,
     * but we want send the request and wait for the response.
     * so we set a 'transmitter' here and wait on it for response.
     */
    private SynchronousQueue<Data> transmitter = new SynchronousQueue<>();

    //make true the Request-Response sequentially.
    private Semaphore semaphore = new Semaphore(1);

    private Executor reconnectExecutor = Executors.newSingleThreadExecutor();

    public DefaultClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public DefaultClient(String serverHost, int serverPort, boolean logFlag) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.logFlag = logFlag;
    }

    @Override
    public void start() {
        try {
            if(isStarted.compareAndSet(false, true)){
                group = new NioEventLoopGroup();
                bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .remoteAddress(serverHost, serverPort)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline p = ch.pipeline();
                                if(logFlag){
                                    p.addLast(new LoggingHandler(LogLevel.INFO));
                                }
                                p.addLast(new RESPDecoder());
                                p.addLast(new RESPEncoder());
                                p.addLast(new ClientHandler(DefaultClient.this, transmitter, reconnectExecutor));
                            }
                        });
                connect();
            }
        } catch (Exception e){
            throw new JredicException("Jredic Client Start Failed!", e);
        }
    }

    public void connect() throws Exception {
        channel = bootstrap.connect().sync().channel();
    }

    public boolean isConnected(){
        if(channel == null){
            return false;
        }
        return channel.isActive() && channel.isOpen();
    }

    @Override
    public void stop() {
        if(isStarted.compareAndSet(true, false)){
            if(group != null){
                group.shutdownGracefully();
            }
        }
    }

    @Override
    public boolean isRunning() {
        return isStarted.get();
    }

    @Override
    public Data send(ArraysData request) {
        try{
            semaphore.acquire();
            channel.writeAndFlush(request).sync();
            return transmitter.take();
        } catch (Exception e) {
            throw new JredicException("error occur in send process!", e);
        } finally {
            semaphore.release();
        }
    }

}
