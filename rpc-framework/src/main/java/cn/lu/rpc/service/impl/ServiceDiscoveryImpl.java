package cn.lu.rpc.service.impl;

import cn.lu.rpc.service.LoadBalance;
import cn.lu.rpc.service.ServiceDiscovery;
import cn.lu.rpc.utils.CuratorUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * cn.lu.cn.lu.rpc.registry.impl
 *
 * @author lkxBruce
 * @date 2022/4/5 20:45
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class ServiceDiscoveryImpl implements ServiceDiscovery {

    private static final Map<String, LoadBalance> LOAD_BALANCE_MAP = new HashMap<>();

    @Override
    public String discover(String serviceName) {
//        InetSocketAddress inetSocketAddress = CuratorUtils.getService(serviceName);
        List<String> service = CuratorUtils.getService(serviceName);
        LoadBalance loadBalance = LOAD_BALANCE_MAP.get(serviceName);
        if (loadBalance == null){
            loadBalance = new LoopLoadBalance(service);
            LOAD_BALANCE_MAP.put(serviceName,loadBalance);
        }
        return loadBalance.getLoadBalancePath(service);
    }
}
