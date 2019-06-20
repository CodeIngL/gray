package com.codeL.gray.jms.p.beanProcessor;

import com.codeL.gray.jms.p.destination.GrayDestinationResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class JmsTemplateProcessor implements MergedBeanDefinitionPostProcessor {

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if (beanType.isAssignableFrom(JmsTemplate.class)) {
            RootBeanDefinition grayBeanDefinition = new RootBeanDefinition();
            grayBeanDefinition.setBeanClass(GrayDestinationResolver.class);
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            constructorArgumentValues.addIndexedArgumentValue(0, new DynamicDestinationResolver());
            grayBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
            BeanDefinitionHolder holder = new BeanDefinitionHolder(grayBeanDefinition,"grayDynamicDestinationResolver");
            beanDefinition.getPropertyValues().addPropertyValue("destinationResolver", holder);
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
