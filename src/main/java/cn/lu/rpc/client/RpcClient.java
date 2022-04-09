package cn.lu.rpc.client;

import cn.lu.rpc.annotation.RpcScan;
import cn.lu.rpc.proxy.RpcClientProxy;
import cn.lu.rpc.example.HelloService;

/**
 * cn.lu.gpc.client
 *
 * @author lkxBruce
 * @date 2022/4/4 16:21
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@RpcScan(basePackage = {"cn.lu.rpc"})
public class RpcClient {

    public static void main(String[] args) {
        HelloService hello = RpcClientProxy.getProxy(HelloService.class);
        String hello1 = hello.hello();
        System.out.println(HelloService.class.getName());
    }
}
