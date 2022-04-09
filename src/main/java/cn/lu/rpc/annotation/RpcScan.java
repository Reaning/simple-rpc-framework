package cn.lu.rpc.annotation;

import cn.lu.rpc.spring.CustomScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * cn.lu.rpc.annotation
 *
 * @author lkxBruce
 * @date 2022/4/9 10:47
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegistrar.class)
@Documented
public @interface RpcScan {

    String[] basePackage();

}
