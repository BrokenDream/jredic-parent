package com.jredic.network.client.support;

import com.jredic.network.protocol.data.Data;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.SynchronousQueue;

public class ClientHandler extends ChannelDuplexHandler {

    public ClientHandler(SynchronousQueue<Data> transmitter) {
        this.transmitter = transmitter;
    }

    private SynchronousQueue<Data> transmitter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) {
        try {
            transmitter.put((Data) data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.print("exceptionCaught: ");
        cause.printStackTrace(System.err);
        ctx.close();
    }



}
