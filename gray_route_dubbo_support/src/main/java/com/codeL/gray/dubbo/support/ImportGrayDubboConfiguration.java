package com.codeL.gray.dubbo.support;

import com.codeL.gray.common.convert.TypeConverterDelegate;
import com.codeL.gray.common.convert.TypeHolder;
import com.codeL.gray.core.support.condition.AutoCondition;
import com.codeL.gray.core.support.condition.strategy.Calculation;
import com.codeL.gray.core.watch.FileWatchDog;
import com.codeL.gray.core.watch.WatchDog;
import com.codeL.gray.dubbo.loadbalance.LoadBalanceAwarer;
import com.codeL.gray.dubbo.strategy.extract.Extractor;
import com.codeL.gray.dubbo.strategy.uid.UidSelector;
import com.codeL.gray.dubbo.strategy.uid.UidTypeConverter;
import com.codeL.gray.dubbo.strategy.uip.UipSelector;
import com.codeL.gray.dubbo.strategy.uip.UipTypeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

import javax.annotation.PostConstruct;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Conditional({AutoCondition.class})
@Calculation(value = "com.codeL.gray.auto.Dubbo")
public class ImportGrayDubboConfiguration {

    @Bean
    LoadBalanceAwarer loadBalanceAwarer() {
        LoadBalanceAwarer loadBalanceAwarer = new LoadBalanceAwarer();
        loadBalanceAwarer.setDelegate(typeConverterDelegate());
        return loadBalanceAwarer;
    }

    @Bean
    WatchDog watchDog() {
        return new FileWatchDog();
    }

    @Bean
    TypeConverterDelegate typeConverterDelegate() {
        TypeConverterDelegate delegate = new TypeConverterDelegate();
        delegate.addTypeConverter(new TypeHolder("uid"), new UidTypeConverter());
        return delegate;
    }


    @PostConstruct
    public void init() {
        Extractor.register(new TypeHolder("dubbo:P:uid"), new UidSelector(new UidTypeConverter()));
        Extractor.register(new TypeHolder("dubbo:P:ip"), new UipSelector(new UipTypeConverter()));
    }

}
