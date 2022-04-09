package cn.lu.rpc.spring;

import cn.lu.rpc.annotation.RpcScan;
import cn.lu.rpc.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * cn.lu.cn.lu.rpc.spring
 *
 * @author lkxBruce
 * @date 2022/4/9 10:31
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Slf4j
public class CustomScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    // 设置扫描器
    private ResourceLoader resourceLoader;
    private final String BASE_PACKAGE_ATTRIBUTE_NAME = "basePackage";

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(RpcScan.class.getName()));
        // 通过RpcScan获取扫描的范围
        String[] rpcScanBasePackage = new String[0];
        if(annotationAttributes != null){
            rpcScanBasePackage = annotationAttributes.getStringArray(BASE_PACKAGE_ATTRIBUTE_NAME);
        }
        if(rpcScanBasePackage.length == 0){
            rpcScanBasePackage = new String[]{((StandardAnnotationMetadata)annotationMetadata).getIntrospectedClass().getPackage().getName()};
        }
        // 分别进行扫描
        CustomScanner rpcScanner = new CustomScanner(registry, RpcService.class);
        CustomScanner springScanner = new CustomScanner(registry, Component.class);
        if(resourceLoader != null){
            rpcScanner.setResourceLoader(resourceLoader);
            springScanner.setResourceLoader(resourceLoader);
        }
        int springCount = springScanner.scan(rpcScanBasePackage);
        log.debug("springScanner:{}",springCount);
        int rpcCount = rpcScanner.scan(rpcScanBasePackage);
        log.debug("rpcScanner:{}",rpcCount);
    }
}
