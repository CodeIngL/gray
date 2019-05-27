package com.codeL.gray.activemq.c.select;

import com.codeL.gray.core.strategy.Policy;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
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
    public String select(String destinationName, Policy policy) {
        if (policy == null) {
            return selector.select(destinationName, this.policy);
        }
        return selector.select(destinationName, policy);
    }
}
