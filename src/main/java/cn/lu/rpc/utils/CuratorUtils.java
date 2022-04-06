package cn.lu.rpc.utils;

import cn.lu.rpc.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;
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
//    public static final

    static{
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(3000, 10);
        service = CuratorFrameworkFactory.builder()
                .connectString("120.25.86.126:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retry)
                .namespace("service")
                .build();
        service.start();
    }
    public static void addPersistentNode(String path){
        try {
            if(PATH_SET.contains(path) || service.checkExists().forPath(path) != null ){
                log.debug("The node already exists, PATH:{}",path);
            }else{
                service.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
    }

    public static InetSocketAddress getService(String serviceName) {
        try {
            List<String> result = service.getChildren().forPath(serviceName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
