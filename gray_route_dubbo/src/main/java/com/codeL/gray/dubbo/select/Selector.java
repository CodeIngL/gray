package com.codeL.gray.dubbo.select;

import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.core.strategy.select.Select;
import com.codeL.gray.dubbo.strategy.IndexedInvoker;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public interface Selector<T> extends Select {
    IndexedInvoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation, Policy policy);
}
