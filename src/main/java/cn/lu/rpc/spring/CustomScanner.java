package cn.lu.rpc.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * cn.lu.rpc.spring
 *
 * @author lkxBruce
 * @date 2022/4/9 11:16
 * @email lkxbruce@gmail.com
 * @project simple-gpc-framework
 */
public class CustomScanner extends ClassPathBeanDefinitionScanner {

    public CustomScanner(BeanDefinitionRegistry beanDefinitionRegistry, Class<? extends Annotation> annoType){
        super(beanDefinitionRegistry);
        super.addIncludeFilter(new AnnotationTypeFilter(annoType));

    }

    public CustomScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }
}
