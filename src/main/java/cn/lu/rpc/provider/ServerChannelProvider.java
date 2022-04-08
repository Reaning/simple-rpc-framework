package cn.lu.rpc.provider;

import cn.lu.rpc.config.ServerConfig;
import cn.lu.rpc.handler.RpcRequestHandler;
import cn.lu.rpc.protocol.FrameDecoder;
import cn.lu.rpc.protocol.MessageCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * cn.lu.rpc.provider
 *
 * @author lkxBruce
 * @date 2022/4/7 23:32
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class ServerChannelProvider {
    private static Channel channel;


    public static Channel getChannel(String serviceName){
        if(channel != null){
            return channel;
        }
        synchronized (ServerChannelProvider.class){
            if(channel != null){
                return channel;
            }
            return newChannel();
        }
    }

    private static Channel newChannel(){
        int port = Integer.parseInt(ServerConfig.getLocalIp().split(":")[1]);
        return newChannel(port);
    }

    private static Channel newChannel(int port){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        Channel serverChannel = null;
        try {
            serverChannel = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new FrameDecoder())
                                    .addLast(new LoggingHandler(LogLevel.DEBUG))
                                    .addLast(new MessageCodec())
                                    .addLast(new RpcRequestHandler());
                        }
                    })
                    .bind(port)
                    .sync()
                    .channel();
            serverChannel.closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    boss.shutdownGracefully();
                    worker.shutdownGracefully();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverChannel;
    }
}
