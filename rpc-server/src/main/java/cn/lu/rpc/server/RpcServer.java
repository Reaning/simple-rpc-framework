package cn.lu.rpc.server;

import cn.lu.rpc.annotation.RpcScan;
import cn.lu.rpc.provider.ServerChannelProvider;
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
