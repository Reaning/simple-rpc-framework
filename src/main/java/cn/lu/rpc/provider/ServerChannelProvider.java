package cn.lu.rpc.provider;

import cn.lu.rpc.handler.RpcRequestHandler;
import cn.lu.rpc.handler.RpcResponseHandler;
import cn.lu.rpc.protocol.FrameDecoder;
import cn.lu.rpc.protocol.MessageCodec;
import cn.lu.rpc.registry.ServiceDiscovery;
import cn.lu.rpc.registry.impl.ServiceDiscoveryImpl;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * cn.lu.rpc.provider
 *
 * @author lkxBruce
 * @date 2022/4/7 23:32
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class ServerChannelProvider {
    private static final Map<String, Channel> PATH_CHANNEL_MAP = new ConcurrentHashMap<>();
    private static final ServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl();


    public static Channel getChannel(String serviceName){
        if(PATH_CHANNEL_MAP.containsKey(serviceName)){
            Channel channel = PATH_CHANNEL_MAP.get(serviceName);
            if(channel == null || !channel.isActive()){
                PATH_CHANNEL_MAP.remove(serviceName);
                return newChannel(serviceName);
            }
            return channel;
        }else{
            return newChannel(serviceName);
        }
    }

    public static Channel newChannel(String serviceName){
        String path = serviceDiscovery.discover(serviceName);
        String[] servicePath = path.split(":");
        return newChannel(serviceName,servicePath[0],Integer.parseInt(servicePath[1]));
    }

    public static Channel newChannel(String serviceName,String host,int port){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Channel serverChannel = new ServerBootstrap()
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
                    .bind(8080)
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
    }
}
