package cn.lu.rpc.service.impl;

import cn.lu.rpc.entity.ServiceMetaData;
import cn.lu.rpc.example.HelloService;
import cn.lu.rpc.service.ServiceRegistry;
import cn.lu.rpc.utils.CuratorUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * cn.lu.cn.lu.rpc.registry.impl
 *
 * @author lkxBruce
 * @date 2022/4/5 19:46
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Service
public class ServiceRegistryImpl implements ServiceRegistry {

    private final ConcurrentHashMap<String,Object> serviceMap;

    public ServiceRegistryImpl() {
        serviceMap = new ConcurrentHashMap<>();
    }


    @Override
    public void registry(ServiceMetaData serviceMetaData) {
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String path = "/" + serviceMetaData.getServiceName() + "/" + host + ":" + serviceMetaData.getPort();
        serviceMap.put(serviceMetaData.getServiceName(), serviceMetaData.getService());
//        CuratorUtils.addNode(path);
    }

    @Override
    public Object getService(String serviceName) {
        return serviceMap.get(serviceName);
    }


//    public static void main(String[] args) throws InterruptedException, IOException {
//        ServiceRegistry serviceRegistry = new ServiceRegistryImpl();
////        serviceRegistry.registry(HelloService.class.getName(),8080);
//        System.in.read();
//    }

}
