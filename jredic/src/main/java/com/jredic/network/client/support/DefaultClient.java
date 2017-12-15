package com.jredic.network.client.support;

import com.jredic.exception.JredicException;
import com.jredic.command.Commands;
import com.jredic.command.ConnectionCommand;
import com.jredic.command.ServerCommand;
import com.jredic.command.sub.Section;
import com.jredic.network.client.Client;
import com.jredic.network.protocol.RESPDecoder;
import com.jredic.network.protocol.RESPEncoder;
import com.jredic.network.protocol.data.*;
import com.jredic.util.Strings;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The default implementation base on Netty.
 *
 * @author David.W
 */
public class DefaultClient implements Client {

    private static final Logger logger = LoggerFactory.getLogger(DefaultClient.class);

    //start flag
    private AtomicBoolean isStarted = new AtomicBoolean(false);

    //server host.
    private String serverHost;

    //server port.
    private int serverPort;

    //password to server.
    private String password;

    //db index.
    private int dbIndex = 0;

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

    //executor for reconnect task.
    private Executor reconnectExecutor = Executors.newSingleThreadExecutor();

    //server info.
    private volatile ServerInfo serverInfo;

    public DefaultClient() {
    }

    public DefaultClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public DefaultClient(String serverHost, int serverPort, boolean logFlag) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.logFlag = logFlag;
    }

    public DefaultClient(String serverHost, int serverPort, String password) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.password = password;
    }

    public DefaultClient(String serverHost, int serverPort, int dbIndex) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.dbIndex = dbIndex;
    }

    public DefaultClient(String serverHost, int serverPort, String password, int dbIndex) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.password = password;
        this.dbIndex = dbIndex;
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
        initServerInfo();
        //if we set password, do auth.
        if(!Strings.isNullOrEmpty(password)){
            auth();
        }
        //if we set dbIndex, select it.
        if(dbIndex != 0){
            selectDB();
        }
    }

    private void selectDB() {
        ArraysData request = Commands.createRequest(ConnectionCommand.SELECT, Integer.toString(dbIndex));
        Data response = send(request);
        if(DataType.SIMPLE_STRINGS.equals(response.getType())){
            SimpleStringsData simpleStringsData = (SimpleStringsData) response;
            if("OK".equals(simpleStringsData.getContent())){
                logger.info("[{}:{}],db {} is selected!", serverHost, serverPort, dbIndex);
            }else{
                throw new JredicException(simpleStringsData.getContent());
            }
        }else{
            throw new JredicException(((ErrorsData)response).getErrorMsg());
        }
    }

    private void auth() {
        ArraysData request = Commands.createRequest(ConnectionCommand.AUTH, password);
        Data response = send(request);
        if(DataType.SIMPLE_STRINGS.equals(request.getType())){
            SimpleStringsData simpleStringsData = (SimpleStringsData) response;
            if("OK".equals(simpleStringsData.getContent())){
                logger.info("auth ok!");
            }else{
                throw new JredicException(simpleStringsData.getContent());
            }
        }else{
            throw new JredicException(((ErrorsData)response).getErrorMsg());
        }
    }

    private void initServerInfo(){
        /*
         * we obtain server info from the Redis Server using the 'INFO server' command.
         */
        BufferedReader reader = null;
        try{
            ArraysData request = Commands.createRequest(ServerCommand.INFO, Section.SERVER.value());
            BulkStringsData response = (BulkStringsData) send(request);
            reader = new BufferedReader(new StringReader(response.getStringContent()));
            ServerInfo info = new ServerInfo();
            String line;
            while ((line = reader.readLine()) != null){
                String[] pair = line.split(":");
                if(pair.length == 2){
                    if("redis_version".equals(pair[0])){
                        info.setVersion(pair[1]);
                        continue;
                    }
                    if("os".equals(pair[0])){
                        info.setOs(pair[1]);
                        continue;
                    }
                    if("arch_bits".equals(pair[0])){
                        info.setArch(pair[1]);
                        continue;
                    }
                    if("process_id".equals(pair[0])){
                        info.setPid(pair[1]);
                        continue;
                    }
                    if("run_id".equals(pair[0])){
                        info.setRunId(pair[1]);
                    }
                }
            }
            serverInfo = info;
        } catch (Throwable e){
            logger.error("init server info failed!", e);
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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
            serverInfo = null;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        boolean startFlag = isStarted.get();
        builder.append("[");
        builder.append("running=").append(startFlag);
        builder.append(",connectionInfo=").append(channel);
        if(startFlag){
            if(!Strings.isNullOrEmpty(password)){
                builder.append(",(password is set)");
            }else{
                builder.append(",(no password)");
            }
            builder.append(",(current db index is ").append(dbIndex).append(")");
        }
        builder.append(",serverInfo=").append(serverInfo);
        return builder.toString();
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }

    public void setLogFlag(boolean logFlag) {
        this.logFlag = logFlag;
    }
}
