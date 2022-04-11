package cn.lu.rpc;

import cn.lu.rpc.annotation.RpcScan;
import cn.lu.rpc.controller.HelloController;
import cn.lu.rpc.entity.RpcMessageRequest;
import cn.lu.rpc.example.HelloService;
import cn.lu.rpc.provider.ClientChannelProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * cn.lu.gpc.client
 *
 * @author lkxBruce
 * @date 2022/4/4 16:21
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@RpcScan(basePackage = {"cn.lu.rpc"})
// 通过basePackage标识扫描的包
public class RpcClient {

    public static void main(String[] args) {
//        new ClientChannelProvider()
        ClientChannelProvider.newChannel("cn.lu.rpc.example.HelloService","127.0.0.1",8080).writeAndFlush(new RpcMessageRequest(0,
                HelloService.class.getName(),
                "hello",
                String.class,
                new Class[0],
                null));
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RpcClient.class);
        HelloController helloController = context.getBean("helloController", HelloController.class);
        helloController.hello();
    }
}
