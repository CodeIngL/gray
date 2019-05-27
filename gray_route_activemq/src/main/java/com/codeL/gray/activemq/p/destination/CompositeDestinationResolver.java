package com.codeL.gray.activemq.p.destination;

import org.springframework.jms.support.destination.DestinationResolver;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public interface CompositeDestinationResolver {

    DestinationResolver getCompositeLoadBalance();
}
