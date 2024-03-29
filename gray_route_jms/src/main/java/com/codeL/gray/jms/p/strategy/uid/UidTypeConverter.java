package com.codeL.gray.jms.p.strategy.uid;

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
public class UidTypeConverter implements SmartTypeConverter<Policy, JmsUidPolicy> {

    @Override
    public Class<?> determineType() {
        return JmsUidPolicy.class;
    }

    @Override
    public JmsUidPolicy convert(Policy source) {
        return PolicyUidUtil.covertFromPolicy(source);
    }
}
