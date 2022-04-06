package cn.lu.rpc.registry.impl;

import cn.lu.rpc.registry.ServiceDiscovery;
import cn.lu.rpc.utils.CuratorUtils;

import java.net.InetSocketAddress;

/**
 * cn.lu.rpc.registry.impl
 *
 * @author lkxBruce
 * @date 2022/4/5 20:45
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class ServiceDiscoveryImpl implements ServiceDiscovery {
    @Override
    public InetSocketAddress discover(String serviceName) {
        InetSocketAddress inetSocketAddress = CuratorUtils.getService(serviceName);
    }
}
