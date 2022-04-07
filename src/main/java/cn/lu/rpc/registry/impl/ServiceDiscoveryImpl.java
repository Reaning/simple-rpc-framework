package cn.lu.rpc.registry.impl;

import cn.lu.rpc.registry.ServiceDiscovery;
import cn.lu.rpc.utils.CuratorUtils;

import java.util.List;

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
    public String discover(String serviceName) {
//        InetSocketAddress inetSocketAddress = CuratorUtils.getService(serviceName);
        List<String> service = CuratorUtils.getService(serviceName);
        return service.get(0);
    }
}
