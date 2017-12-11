package com.jredic.network;

import com.jredic.network.protocol.data.ArraysData;
import com.jredic.network.protocol.data.BulkStringsData;
import com.jredic.network.protocol.data.Data;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David.W
 */
public class RedisClientTest {

    private static final String HOST = System.getProperty("host", "10.10.40.120");
    private static final int PORT = Integer.parseInt(System.getProperty("port", "6379"));

    public static void main(String[] args){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new RESPDecoder());
                            p.addLast(new RESPEncoder());
                            p.addLast(new RedisClientHandler());
                        }
                    });

            // Start the connection attempt.
            Channel ch = b.connect(HOST, PORT).sync().channel();
            List<Data> element = new ArrayList<>(3);
            element.add(new BulkStringsData("set"));
            element.add(new BulkStringsData("jredic"));
            element.add(new BulkStringsData("cool"));
            ArraysData cmd = new ArraysData(element);
            ch.write(cmd);
            ch.flush();
            Thread.sleep(1000000);
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            group.shutdownGracefully();
        }
    }

}
