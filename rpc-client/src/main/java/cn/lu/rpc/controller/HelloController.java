package cn.lu.rpc.controller;

import cn.lu.rpc.annotation.RpcReference;
import cn.lu.rpc.example.HelloService;
import org.springframework.stereotype.Component;

/**
 * cn.lu.rpc.client.controller
 *
 * @author lkxBruce
 * @date 2022/4/9 12:39
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Component
public class HelloController {

    @RpcReference
    private HelloService helloService;

    public void hello(){
        System.out.println(helloService.hello());
    }

}
