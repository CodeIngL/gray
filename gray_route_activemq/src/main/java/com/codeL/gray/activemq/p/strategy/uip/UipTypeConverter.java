package com.codeL.gray.activemq.p.strategy.uip;

import com.codeL.gray.common.convert.SmartTypeConverter;
import com.codeL.gray.core.strategy.Policy;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class UipTypeConverter implements SmartTypeConverter<Policy, ActiveMqUipPolicy> {
    @Override
    public Class<?> determineType() {
        return ActiveMqUipPolicy.class;
    }

    @Override
    public ActiveMqUipPolicy convert(Policy source) {
        return PolicyUipUtil.covertFromPolicy(source);
    }
}
