package cn.lu.rpc.service.impl;

import cn.lu.rpc.service.ServiceRegistry;
import cn.lu.rpc.example.HelloService;
import cn.lu.rpc.utils.CuratorUtils;

import java.io.IOException;

/**
 * cn.lu.rpc.registry.impl
 *
 * @author lkxBruce
 * @date 2022/4/5 19:46
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class ServiceRegistryImpl implements ServiceRegistry {
    @Override
    public void registry(String serviceName, String hostAddress) {
        String path = "/" + serviceName + "/" + hostAddress;
        CuratorUtils.addNode(path);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        ServiceRegistry serviceRegistry = new ServiceRegistryImpl();
        serviceRegistry.registry(HelloService.class.getName(),"127.0.0.1:8080");
        System.in.read();
    }

}
