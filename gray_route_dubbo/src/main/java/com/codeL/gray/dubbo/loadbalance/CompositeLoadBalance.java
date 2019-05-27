package com.codeL.gray.dubbo.loadbalance;

import com.alibaba.dubbo.rpc.cluster.LoadBalance;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public interface CompositeLoadBalance {

    LoadBalance getCompositeLoadBalance();
}
