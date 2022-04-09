package cn.lu.rpc.proxy;

import cn.lu.rpc.entity.RpcMessageRequest;
import cn.lu.rpc.protocol.SequenceNum;
import cn.lu.rpc.provider.ClientChannelProvider;
import io.netty.channel.Channel;
import io.netty.util.concurrent.DefaultPromise;

import java.lang.reflect.Proxy;

/**
 * cn.lu.cn.lu.rpc.proxy
 *
 * @author lkxBruce
 * @date 2022/4/7 23:23
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class RpcClientProxy{

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
            Channel channel = ClientChannelProvider.getChannel(clazz.getName());
            channel.writeAndFlush(request);
            System.out.println();
            DefaultPromise<Object> promise = new DefaultPromise<>(channel.eventLoop());
            SequenceNum.invokeResultMap.put(sequenceNum,promise);
            promise.sync();
            Object now = promise.getNow();
            return now;
        });
    }

}
