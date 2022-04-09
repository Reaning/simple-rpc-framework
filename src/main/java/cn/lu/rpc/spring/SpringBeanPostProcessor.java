package cn.lu.rpc.spring;

import cn.lu.rpc.annotation.RpcService;
import cn.lu.rpc.service.ServiceRegistry;
import cn.lu.rpc.service.impl.ServiceRegistryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * cn.lu.rpc.spring
 *
 * @author lkxBruce
 * @date 2022/4/9 11:28
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Component
@Slf4j
public class SpringBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 在初始化之前注册Service到Zookeeper
        if(bean.getClass().isAnnotationPresent(RpcService.class)){
            log.debug("[{}] is annotated with [{}]",bean.getClass().getName(),RpcService.class.getName());
            ServiceRegistry serviceRegistry = new ServiceRegistryImpl();
            for(Class<?> interfaceName : bean.getClass().getInterfaces()){
                // 注册接口
                serviceRegistry.registry(interfaceName.getName(),8080);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
