package cn.lu.rpc.entity;

import lombok.Builder;
import lombok.Data;

/**
 * cn.lu.rpc.entity
 *
 * @author lkxBruce
 * @date 2022/4/11 0:39
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Data
@Builder
public class ServiceMetaData {
    private String serviceName;
    private Object service;
    private int port;
}
