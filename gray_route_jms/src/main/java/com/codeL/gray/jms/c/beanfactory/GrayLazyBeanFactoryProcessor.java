package com.codeL.gray.jms.c.beanfactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class GrayLazyBeanFactoryProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GrayBeanFactory grayBeanFactory = new GrayBeanFactory(applicationContext, beanFactory);
        String[] defaultBeanNames = registerDefault(beanFactory, grayBeanFactory);
        String[] simpleBeanNames = registerSimple(beanFactory, grayBeanFactory);
        removeParent(defaultBeanNames, simpleBeanNames, beanFactory);
        GrayBeanFactoryBinder.bindGlobalGrayBeanFactory(grayBeanFactory);
    }

    private void removeParent(String[] defaultBeanNames, String[] simpleBeanNames, ConfigurableListableBeanFactory parentFactory) {
        if (parentFactory.getClass().isAssignableFrom(DefaultListableBeanFactory.class)) {
            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) parentFactory;
            BeanDefinitionRegistry registry = defaultListableBeanFactory;
            if (defaultBeanNames != null) {
                for (String name : defaultBeanNames) {
                    registry.removeBeanDefinition(name);
                }
            }
            if (simpleBeanNames != null) {
                for (String name : simpleBeanNames) {
                    registry.removeBeanDefinition(name);
                }
            }
        }
    }

    private String[] registerDefault(ConfigurableListableBeanFactory beanFactory, GrayBeanFactory grayBeanFactory) {
        return registerContainer(beanFactory, grayBeanFactory, DefaultMessageListenerContainer.class);
    }

    private String[] registerSimple(ConfigurableListableBeanFactory beanFactory, GrayBeanFactory grayBeanFactory) {
        return registerContainer(beanFactory, grayBeanFactory, SimpleMessageListenerContainer.class);
    }

    private String[] registerContainer(ConfigurableListableBeanFactory beanFactory, GrayBeanFactory grayBeanFactory, Class<? extends MessageListenerContainer> lc) {
        String[] cBeanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
                beanFactory, lc, true, false);
        if (cBeanNames != null && cBeanNames.length > 0) {
            for (String beanName : cBeanNames) {
                BeanDefinition definition = beanFactory.getBeanDefinition(beanName);
                grayBeanFactory.registerBeanDefinition(beanName, definition);
            }
        }
        return cBeanNames;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
