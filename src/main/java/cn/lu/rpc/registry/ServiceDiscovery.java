package cn.lu.rpc.registry;

/**
 * cn.lu.rpc.registry
 *
 * @author lkxBruce
 * @date 2022/4/5 19:43
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public interface ServiceDiscovery {
    String discover(String serviceName);
}
