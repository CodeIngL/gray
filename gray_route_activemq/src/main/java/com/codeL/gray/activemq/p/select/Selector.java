package com.codeL.gray.activemq.p.select;

import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.core.strategy.select.Select;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public interface Selector extends Select {
    String select(String destinationName, boolean pubSubDomain, Policy policy);
}
