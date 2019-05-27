package com.codeL.gray.activemq.p.select;

import com.codeL.gray.common.convert.TypeConverter;
import com.codeL.gray.core.strategy.Policy;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public abstract class AbstractSelector<T> implements Selector {

    private final TypeConverter typeConverter;

    public AbstractSelector(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }

    @Override
    public String select(String destinationName, boolean pubSubDomain, Policy policy) {
        return doSelect(destinationName, pubSubDomain, policy);
    }

    protected abstract String doSelect(String destinationName, boolean pubSubDomain, Policy policy);

    protected TypeConverter getTypeConverter() {
        return typeConverter;
    }
}
