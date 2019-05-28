package com.codeL.gray.jms.support.p;

import com.codeL.gray.jms.p.beanProcessor.JmsTemplateProcessor;
import com.codeL.gray.jms.p.strategy.extract.Extractor;
import com.codeL.gray.jms.p.strategy.uid.UidSelector;
import com.codeL.gray.jms.p.strategy.uid.UidTypeConverter;
import com.codeL.gray.jms.p.strategy.uip.UipSelector;
import com.codeL.gray.jms.p.strategy.uip.UipTypeConverter;
import com.codeL.gray.common.convert.TypeHolder;
import com.codeL.gray.core.support.condition.AutoCondition;
import com.codeL.gray.core.support.condition.strategy.Calculation;
import com.codeL.gray.core.watch.FileWatchDog;
import com.codeL.gray.core.watch.WatchDog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

import javax.annotation.PostConstruct;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Conditional({AutoCondition.class})
@Calculation(value = "com.codeL.gray.auto.JmsP")
public class ImportGrayJmsPConfiguration {

    @Bean
    WatchDog watchDog() {
        return new FileWatchDog();
    }

    @Bean
    public JmsTemplateProcessor jmsTemplateProcessor() {
        return new JmsTemplateProcessor();
    }

    @PostConstruct
    public void init() {
        Extractor.register(new TypeHolder("jms:P:uid"), new UidSelector(new UidTypeConverter()));
        Extractor.register(new TypeHolder("jms:P:ip"), new UipSelector(new UipTypeConverter()));
    }
}
