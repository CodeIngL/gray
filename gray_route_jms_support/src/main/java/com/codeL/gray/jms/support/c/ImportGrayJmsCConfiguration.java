package com.codeL.gray.jms.support.c;

import com.codeL.gray.jms.c.beanfactory.GrayBeanFactoryAwarer;
import com.codeL.gray.jms.c.beanfactory.GrayLazyBeanFactoryProcessor;
import com.codeL.gray.core.support.condition.AutoCondition;
import com.codeL.gray.core.support.condition.strategy.Calculation;
import com.codeL.gray.core.watch.FileWatchDog;
import com.codeL.gray.core.watch.WatchDog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Conditional({AutoCondition.class})
@Calculation(value = "com.codeL.gray.auto.JmsC")
public class ImportGrayJmsCConfiguration {

    @Bean
    WatchDog watchDog() {
        return new FileWatchDog();
    }

    @Bean
    GrayBeanFactoryAwarer grayBeanFactoryAwarer() {
        return new GrayBeanFactoryAwarer();
    }

    @Bean
    GrayLazyBeanFactoryProcessor grayLazyBeanFactoryProcessor() {
        return new GrayLazyBeanFactoryProcessor();
    }
}
