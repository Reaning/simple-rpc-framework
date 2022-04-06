package cn.lu.rpc.registry.impl;

import cn.lu.rpc.registry.ServiceRegistry;
import cn.lu.rpc.utils.CuratorUtils;
import org.apache.curator.framework.CuratorFrameworkFactory;

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
        CuratorUtils.addPersistentNode(path);
    }

    public static void main(String[] args) {
        ServiceRegistry serviceRegistry = new ServiceRegistryImpl();
        serviceRegistry.registry("cn.lu.HelloService","120.1.1.0:8080");
    }

}
