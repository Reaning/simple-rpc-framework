package cn.lu.rpc.utils;

import cn.lu.rpc.config.ClientConfig;
import cn.lu.rpc.service.LoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * cn.lu.rpc.utils
 *
 * @author lkxBruce
 * @date 2022/4/5 19:47
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Slf4j
public class CuratorUtils {

    public static volatile CuratorFramework service;
    public static final Set<String> PATH_SET = ConcurrentHashMap.newKeySet();
    public static final Map<String,List<String>> SERVICE_ADDRESS_MAP= new ConcurrentHashMap<>();
//    private static final Map<String, LoadBalance> LOAD_BALANCE_MAP = new ConcurrentHashMap<>();
//    public static final

    static{
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(3000, 10);
        service = CuratorFrameworkFactory.builder()
                .connectString(ClientConfig.getZookeeperAddress())
                .sessionTimeoutMs(20 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retry)
                .namespace("service")
                .build();
        service.start();
    }

    private CuratorUtils(){}

    public static void addNode(String path){
        try {
            if(PATH_SET.contains(path) || service.checkExists().forPath(path) != null ){
                log.debug("The node already exists, PATH:{}",path);
            }else{
                service.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
//                service.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getService(String serviceName) {
        if(SERVICE_ADDRESS_MAP.containsKey(serviceName)){
            return SERVICE_ADDRESS_MAP.get(serviceName);
        }
        List<String> result = null;
        try {
            result = service.getChildren().forPath("/" + serviceName);
            SERVICE_ADDRESS_MAP.put(serviceName,result);
            // 可以增加监听器监听节点更新状态
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void closeClient(){
        service.close();
    }
}
