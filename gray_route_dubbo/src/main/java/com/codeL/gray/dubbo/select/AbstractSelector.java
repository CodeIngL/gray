package com.codeL.gray.dubbo.select;

import com.codeL.gray.common.convert.TypeConverter;
import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.dubbo.strategy.IndexedInvoker;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public abstract class AbstractSelector<T> implements Selector<T> {

    private final TypeConverter typeConverter;

    public AbstractSelector(TypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }

    @Override
    public IndexedInvoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation, Policy policy) {
        return doSelect(invokers, url, invocation, policy);
    }

    protected abstract IndexedInvoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation, Policy policy);

    protected TypeConverter getTypeConverter() {
        return typeConverter;
    }
}
