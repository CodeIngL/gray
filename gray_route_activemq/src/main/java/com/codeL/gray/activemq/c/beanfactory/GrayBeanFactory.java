package com.codeL.gray.activemq.c.beanfactory;

import com.codeL.gray.activemq.c.strategy.extract.Extractor;
import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.context.GrayContext;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static com.codeL.gray.core.context.GrayContextBinder.getGlobalGrayContext;
/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class GrayBeanFactory {

    public GrayBeanFactory(ApplicationContext applicationContext, ConfigurableListableBeanFactory parent) {
        this.parentApplication = (applicationContext);
    }

    private ApplicationContext parentApplication;

    private StaticApplicationContext staticApplicationContext = null;

    private SimpleBeanDefinitionRegistry originRegistry = new SimpleBeanDefinitionRegistry();

    private Map<String, String> originMapping = new HashMap<>();

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        originRegistry.registerBeanDefinition(beanName, beanDefinition);
    }

    public void refresh() {
        if (staticApplicationContext != null) {
            staticApplicationContext.getBeanFactory().destroySingletons();
        }
        staticApplicationContext = new StaticApplicationContext(parentApplication);
        String[] beanNames = originRegistry.getBeanDefinitionNames();
        if (beanNames.length == 0) {
            return;
        }
        for (String beaName : beanNames) {
            BeanDefinition definition = originRegistry.getBeanDefinition(beaName);
            MutablePropertyValues propertyValues = definition.getPropertyValues();
            String originName = originMapping.get(beaName);
            if (originName == null || "".equals(originName)) {
                originName = (String) propertyValues.get("destinationName");
            }
            if (originName == null || "".equals(originName)) {
                continue;
            }
            originMapping.put(beaName, originName);
            String goalName = originName;
            GrayContext context = getGlobalGrayContext();
            if (context.getGrayStatus() == GrayStatus.Open) {
                goalName = new Extractor().extract(originName, null);
            }
            propertyValues.addPropertyValue("destinationName", goalName);
            staticApplicationContext.registerBeanDefinition(beaName, definition);
        }
        staticApplicationContext.refresh();
    }

}
