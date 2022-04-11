package cn.lu.rpc.service.impl;

import cn.lu.rpc.service.LoadBalance;
import cn.lu.rpc.service.ServiceDiscovery;
import cn.lu.rpc.service.ServiceRegistry;
import cn.lu.rpc.utils.CuratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

//import javax.imageio.spi.ServiceRegistry;
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
@Service
@ComponentScan(basePackages = "cn.lu.rpc")
public class ServiceDiscoveryImpl implements ServiceDiscovery {
    @Autowired
    private ServiceRegistry serviceRegistry;

    private static final Map<String, LoadBalance> LOAD_BALANCE_MAP = new HashMap<>();

    @Override
    public String discover(String serviceName) {
//        InetSocketAddress inetSocketAddress = CuratorUtils.getService(serviceName);
        serviceRegistry.hashCode();
        List<String> service = CuratorUtils.getService(serviceName);
        LoadBalance loadBalance = LOAD_BALANCE_MAP.get(serviceName);
        if (loadBalance == null){
            loadBalance = new LoopLoadBalance(service);
            LOAD_BALANCE_MAP.put(serviceName,loadBalance);
        }
        return loadBalance.getLoadBalancePath(service);
    }
}
