package com.codeL.gray.dubbo.select;

import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.dubbo.strategy.IndexedInvoker;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class WrappedSelector implements Selector {

    final Selector selector;

    final Policy policy;

    public WrappedSelector(Selector selector, Policy policy) {
        this.selector = selector;
        this.policy = policy;
    }

    @Override
    public IndexedInvoker select(List list, URL url, Invocation invocation, Policy policy) {
        if (policy == null) {
            return selector.select(list, url, invocation, this.policy);
        }
        return selector.select(list, url, invocation, policy);
    }
}
