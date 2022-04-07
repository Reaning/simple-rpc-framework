package cn.lu.rpc.registry.impl;

import cn.lu.rpc.registry.ServiceRegistry;
import cn.lu.rpc.service.HelloService;
import cn.lu.rpc.utils.CuratorUtils;
import org.apache.curator.framework.CuratorFrameworkFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;

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
