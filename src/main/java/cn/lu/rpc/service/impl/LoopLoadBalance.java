package cn.lu.rpc.service.impl;

import cn.lu.rpc.service.LoadBalance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * cn.lu.rpc.service.impl
 *
 * @author lkxBruce
 * @date 2022/4/8 20:07
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class LoopLoadBalance implements LoadBalance {

    private AtomicInteger atomicInteger;
    private List<String> services;

    public LoopLoadBalance(List<String> servicePath){
        atomicInteger = new AtomicInteger();
        services = new ArrayList<>(servicePath);
    }

    @Override
    public String getLoadBalancePath(List<String> servicePath) {
        if (!servicePath.equals(services)){
            services = new ArrayList<>(servicePath);
            atomicInteger.set(0);
        }
        return servicePath.get(atomicInteger.getAndIncrement() % servicePath.size());
    }

}
