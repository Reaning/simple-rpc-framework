package cn.lu.rpc.annotation;

import java.lang.annotation.*;

/**
 * cn.lu.rpc.annotation
 *
 * @author lkxBruce
 * @date 2022/4/9 10:26
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RpcService {

    String beanName();

}
