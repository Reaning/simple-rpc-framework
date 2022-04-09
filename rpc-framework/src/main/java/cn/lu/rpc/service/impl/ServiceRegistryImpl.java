package cn.lu.rpc.service.impl;

import cn.lu.rpc.example.HelloService;
import cn.lu.rpc.service.ServiceRegistry;
import cn.lu.rpc.utils.CuratorUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * cn.lu.cn.lu.rpc.registry.impl
 *
 * @author lkxBruce
 * @date 2022/4/5 19:46
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class ServiceRegistryImpl implements ServiceRegistry {
    @Override
    public void registry(String serviceName, int port) {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String path = "/" + serviceName + "/" + host + ":" + port;
        CuratorUtils.addNode(path);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        ServiceRegistry serviceRegistry = new ServiceRegistryImpl();
        serviceRegistry.registry(HelloService.class.getName(),8080);
        System.in.read();
    }

}
