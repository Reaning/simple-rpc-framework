package cn.lu.rpc.service;

import java.util.List;

/**
 * cn.lu.cn.lu.rpc.service
 *
 * @author lkxBruce
 * @date 2022/4/8 20:07
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public interface LoadBalance {

    String getLoadBalancePath(List<String> servicePath);

}
