package cn.lu.rpc.server;

import cn.lu.rpc.annotation.RpcScan;
import cn.lu.rpc.provider.ServerChannelProvider;
import cn.lu.rpc.service.ServiceDiscovery;
import cn.lu.rpc.service.impl.ServiceDiscoveryImpl;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

/**
 * cn.lu.gpc.server
 *
 * @author lkxBruce
 * @date 2022/4/4 15:01
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@ComponentScan(basePackages = {"cn.lu.rpc"})
@RpcScan(basePackage = {"cn.lu.rpc"})
public class RpcServer {

//    @Autowired
//    private static ServiceDiscovery serviceDiscovery;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RpcServer.class);
//        context.getBean("")

        try {
            new ServiceDiscoveryImpl().discover("abc");
            Channel channel = ServerChannelProvider.getChannel();
//            new ServiceRegistryImpl().registry(HelloService.class.getName(),8080);
            System.in.read();
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
