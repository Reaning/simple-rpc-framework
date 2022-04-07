package cn.lu.rpc.provider;

import cn.lu.rpc.handler.RpcResponseHandler;
import cn.lu.rpc.protocol.FrameDecoder;
import cn.lu.rpc.protocol.MessageCodec;
import cn.lu.rpc.registry.ServiceDiscovery;
import cn.lu.rpc.registry.impl.ServiceDiscoveryImpl;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * cn.lu.rpc.provider
 *
 * @author lkxBruce
 * @date 2022/4/7 22:27
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class ClientChannelProvider {

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
        NioEventLoopGroup group = new NioEventLoopGroup();
        Channel channel = null;
        try {
            channel = new Bootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new FrameDecoder())
                                    .addLast(new LoggingHandler())
                                    .addLast(new MessageCodec())
                                    .addLast(new RpcResponseHandler());
                        }
                    }).connect(host, port).sync().channel();
            PATH_CHANNEL_MAP.put(serviceName,channel);
            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
        } catch (InterruptedException ignored) {
        }
        return channel;
    }

}
