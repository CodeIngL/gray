package com.codeL.gray.dubbo.loadbalance;

import com.codeL.gray.common.convert.TypeConverterDelegate;
import com.codeL.gray.dubbo.ExtensionLoaderAware;
import com.alibaba.dubbo.rpc.cluster.LoadBalance;
import com.alibaba.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Slf4j
public class LoadBalanceAwarer extends ExtensionLoaderAware<LoadBalance> {

    public LoadBalanceAwarer() {
        super(LoadBalance.class);
    }

    @Setter
    private TypeConverterDelegate delegate = null;

    @Override
    protected WrapperResult<LoadBalance> doWrapper(List<LoadBalance> loadBalances) {
        boolean allWrapped = true;
        List<LoadBalance> wrappedLoadBalances = new ArrayList<>();
        for (LoadBalance loadBalance : loadBalances) {
            LoadBalance wrappedLoadBalance = loadBalance;
            if (!loadBalance.getClass().isAssignableFrom(GrayLoadBalance.class) &&
                    AbstractLoadBalance.class.isAssignableFrom(loadBalance.getClass())) {
                wrappedLoadBalance = new GrayLoadBalance(loadBalance, delegate == null ? new TypeConverterDelegate() : delegate);
                allWrapped = false;
            }
            wrappedLoadBalances.add(wrappedLoadBalance);
        }
        return new WrapperResult<>(wrappedLoadBalances, allWrapped);
    }
}
