package com.jredic.network.client.support;

import com.jredic.JredicException;
import com.jredic.network.client.Client;
import com.jredic.network.protocol.RESPDecoder;
import com.jredic.network.protocol.RESPEncoder;
import com.jredic.network.protocol.data.ArraysData;
import com.jredic.network.protocol.data.BulkStringsData;
import com.jredic.network.protocol.data.Data;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The client base on Netty.
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

    //netty components
    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private Channel channel;

    //the transmitter of Redis Data
    private SynchronousQueue<Data> transmitter = new SynchronousQueue<>();

    //make true the Request-Response sequentially.
    private Semaphore semaphore = new Semaphore(1);

    public DefaultClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
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
                              //  p.addLast(new LoggingHandler(LogLevel.INFO));
                                p.addLast(new RESPDecoder());
                                p.addLast(new RESPEncoder());
                                p.addLast(new ClientHandler(transmitter));
                            }
                        });
                connect();
            }
        } catch (Exception e){
            throw new JredicException("Jredic Client Start Failed!", e);
        }
    }

    private void connect() throws InterruptedException {
         ChannelFuture channelFuture = bootstrap.connect().addListener(new ChannelFutureListener() {
             @Override
             public void operationComplete(ChannelFuture future) throws Exception {
                 //
             }
         });
         channel = channelFuture.sync().channel();
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
    public Data send(ArraysData request) {
        try{
            semaphore.acquire();
            channel.writeAndFlush(request);
            Data data = transmitter.take();
            semaphore.release();
            return data;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
