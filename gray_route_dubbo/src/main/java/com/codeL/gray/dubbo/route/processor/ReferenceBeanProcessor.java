package com.codeL.gray.dubbo.route.processor;

import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.*;

/**
 * <p>Description: </p>
 *
 * @author laihj
 * 2019/6/10
 */
public class ReferenceBeanProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        return;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] dubbors = beanFactory.getBeanNamesForType(ReferenceBean.class, false, false);
        if (dubbors != null && dubbors.length > 0) {
            for (String dubbor : dubbors) {
                addParameters(beanFactory, dubbor);
            }
        }
        String[] dubbrrs = beanFactory.getBeanNamesForType(RegistryConfig.class, false, false);
        if (dubbrrs != null && dubbrrs.length > 0) {
            for (String dubborr : dubbrrs) {
                addParameters(beanFactory, dubborr);
            }
        }
        return;
    }

    private void addParameters(ConfigurableListableBeanFactory beanFactory, String dubbor) {
        BeanDefinition bd = null;
        if (dubbor.startsWith("&")) {
            bd = beanFactory.getBeanDefinition(dubbor.substring(1));
        } else {
            bd = beanFactory.getBeanDefinition(dubbor);
        }
        if (bd == null) {
            return;
        }
        ManagedMap map = (ManagedMap) bd.getPropertyValues().get("parameters");
        if (map == null) {
            map = new ManagedMap();
        }
        if (map.containsKey("router") || map.containsKey(".router")) {
            return;
        }
        map.put("router", new TypedStringValue("graystub", String.class));
        bd.getPropertyValues().add("parameters", map);
    }
}
