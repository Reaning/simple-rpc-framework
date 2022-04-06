package cn.lu.rpc.service.impl;

import cn.lu.rpc.service.HelloService;

/**
 * cn.lu.rpc.Service.impl
 *
 * @author lkxBruce
 * @date 2022/4/5 1:11
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello() {
        return "hello123";
    }
}
