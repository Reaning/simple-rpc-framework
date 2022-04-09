package cn.lu.rpc.service;

/**
 * cn.lu.cn.lu.rpc.registry
 *
 * @author lkxBruce
 * @date 2022/4/5 19:43
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public interface ServiceRegistry {
    void registry(String serviceName, int port) ;
}
