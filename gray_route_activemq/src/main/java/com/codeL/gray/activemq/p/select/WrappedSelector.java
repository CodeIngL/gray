package com.codeL.gray.activemq.p.select;

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
    public String select(String destinationName, boolean pubSubDomain, Policy policy) {
        if (policy == null) {
            return selector.select(destinationName, pubSubDomain, this.policy);
        }
        return selector.select(destinationName, pubSubDomain, policy);
    }
}
