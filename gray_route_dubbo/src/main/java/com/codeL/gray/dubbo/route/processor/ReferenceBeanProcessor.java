package com.codeL.gray.dubbo.route.processor;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * <p>Description: </p>
 *
 * @author laihj
 * 2019/6/10
 */
public class ReferenceBeanProcessor implements MergedBeanDefinitionPostProcessor {

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if (beanDefinition.getBeanClass() != ReferenceBean.class) {
            return;
        }
        ManagedMap map = (ManagedMap) beanDefinition.getPropertyValues().get("parameters");
        if (map.containsKey("router") || map.containsKey(".router")) {
            return;
        }
        map.put("router", new TypedStringValue("gray", String.class));
        return;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

}
