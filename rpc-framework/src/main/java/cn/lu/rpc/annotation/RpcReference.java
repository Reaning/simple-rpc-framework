package cn.lu.rpc.annotation;

import java.lang.annotation.*;

/**
 * cn.lu.cn.lu.rpc.annotation
 *
 * @author lkxBruce
 * @date 2022/4/9 12:02
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Documented
@Inherited
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcReference {

}
