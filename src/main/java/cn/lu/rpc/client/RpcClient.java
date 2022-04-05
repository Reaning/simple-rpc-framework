package cn.lu.rpc.client;

import cn.lu.rpc.Service.HelloService;
import cn.lu.rpc.entity.RpcMessageRequest;
import cn.lu.rpc.handler.RpcResponseHandler;
import cn.lu.rpc.protocol.FrameDecoder;
import cn.lu.rpc.protocol.MessageCodec;
import cn.lu.rpc.protocol.SequenceNum;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import java.lang.reflect.Proxy;

/**
 * cn.lu.gpc.client
 *
 * @author lkxBruce
 * @date 2022/4/4 16:21
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class RpcClient {

    public static volatile Channel channel;

    public static Channel getChannel(){
        if(channel != null){
            return channel;
        }
        synchronized (RpcClient.class){
            if(channel != null){
                return channel;
            }
            return initChannel();
        }
    }

    public static Channel initChannel(){
        try {
            NioEventLoopGroup group = new NioEventLoopGroup();
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
                    }).connect("localhost", 8090).sync().channel();
            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
        } catch (InterruptedException ignored) {
        }
        return channel;
    }

    public static<T> T getProxy(Class<T> clazz){
        Class<?>[] classes = {clazz};
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), classes, (obj, method, args) -> {
            Integer sequenceNum = SequenceNum.getSequenceNum();
            RpcMessageRequest request = new RpcMessageRequest(
                    sequenceNum,
                    clazz.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args
            );
            getChannel().writeAndFlush(request);
            DefaultPromise<Object> promise = new DefaultPromise<>(channel.eventLoop());
            SequenceNum.invokeResultMap.put(sequenceNum,promise);
            promise.sync();
            Object now = promise.getNow();
            return now;
        });
    }

    public static void main(String[] args) {
        HelloService hello = getProxy(HelloService.class);
        String hello1 = hello.hello();
    }
}
