package cn.lu.rpc.server;

import cn.lu.rpc.annotation.RpcScan;
import cn.lu.rpc.example.HelloService;
import cn.lu.rpc.handler.RpcRequestHandler;
import cn.lu.rpc.protocol.FrameDecoder;
import cn.lu.rpc.protocol.MessageCodec;
import cn.lu.rpc.provider.ServerChannelProvider;
import cn.lu.rpc.service.impl.ServiceRegistryImpl;
import cn.lu.rpc.utils.CuratorUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * cn.lu.gpc.server
 *
 * @author lkxBruce
 * @date 2022/4/4 15:01
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@RpcScan(basePackage = {"cn.lu.rpc"})
public class RpcServer {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RpcServer.class);
//        context.getBean("")

        try {
            Channel channel = ServerChannelProvider.getChannel();
//            new ServiceRegistryImpl().registry(HelloService.class.getName(),8080);
            System.in.read();
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
