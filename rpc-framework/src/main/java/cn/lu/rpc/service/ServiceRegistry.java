package cn.lu.rpc.service;

import cn.lu.rpc.entity.ServiceMetaData;

/**
 * cn.lu.cn.lu.rpc.registry
 *
 * @author lkxBruce
 * @date 2022/4/5 19:43
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public interface ServiceRegistry {
    void registry(ServiceMetaData serviceMetaData) ;
    Object getService(String serviceName);
}
