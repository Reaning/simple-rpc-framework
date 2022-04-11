package cn.lu.rpc.spring;

import cn.lu.rpc.annotation.RpcReference;
import cn.lu.rpc.annotation.RpcService;
import cn.lu.rpc.entity.ServiceMetaData;
import cn.lu.rpc.proxy.RpcClientProxy;
import cn.lu.rpc.service.ServiceRegistry;
import cn.lu.rpc.service.impl.ServiceRegistryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * cn.lu.cn.lu.rpc.spring
 *
 * @author lkxBruce
 * @date 2022/4/9 11:28
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
@Component
@Slf4j
public class SpringBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private ServiceRegistry serviceRegistry;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 在初始化之前注册Service到Zookeeper
        if(bean.getClass().isAnnotationPresent(RpcService.class)){
            log.debug("[{}] is annotated with [{}]",bean.getClass().getName(),RpcService.class.getName());
            for(Class<?> interfaceName : bean.getClass().getInterfaces()){
                // 注册接口
                ServiceMetaData serviceMetaData = ServiceMetaData.builder()
                        .serviceName(interfaceName.getName())
                        .service(bean)
                        .port(8080)
                        .build();
                serviceRegistry.registry(serviceMetaData);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 在Bean初始化后，对Bean中的服务进行远程调用
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields){
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if (rpcReference != null){
                Object proxy = RpcClientProxy.getProxy(field.getType());
                field.setAccessible(true);
                try {
                    field.set(bean,proxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}
