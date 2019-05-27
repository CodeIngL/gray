package com.codeL.gray.dubbo.loadbalance;

import com.codeL.gray.common.convert.TypeConverterDelegate;
import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.context.GrayContext;
import com.codeL.gray.dubbo.strategy.CompositeIndexedInvoker;
import com.codeL.gray.dubbo.strategy.IndexedInvoker;
import com.codeL.gray.dubbo.strategy.extract.Extractor;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.cluster.LoadBalance;
import com.alibaba.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.codeL.gray.core.context.GrayContextBinder.getGlobalGrayContext;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class GrayLoadBalance extends AbstractLoadBalance implements CompositeLoadBalance {

    public GrayLoadBalance(LoadBalance originLoadBalance, TypeConverterDelegate delegate) {
        this.originLoadBalance = originLoadBalance;
        this.extractor = new Extractor(delegate);
    }

    private LoadBalance originLoadBalance;
    private Extractor extractor;

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        GrayContext context = getGlobalGrayContext();
        GrayStatus status = context.getGrayStatus();
        if (!(GrayStatus.Open == status)) {
            return originLoadBalance.select(invokers, url, invocation);
        }

        CompositeIndexedInvoker<T> invoker = extractor.extract(invokers, url, invocation);
        if (invoker == null) {
            return originLoadBalance.select(invokers, url, invocation);
        }
        List<Invoker<T>> changedInvokers = new ArrayList<>();

        List<IndexedInvoker> chosen = invoker.getAllChosen();
        if (chosen != null || chosen.size() > 0) {
            if (chosen.size() == 1){
                return chosen.get(0).getInvoker();
            }
            for (int i = 0; i < chosen.size(); i++) {
                changedInvokers.add(chosen.get(i).getInvoker());
            }
            return originLoadBalance.select(changedInvokers, url, invocation);
        }

        List<IndexedInvoker> unChosen = invoker.getAllUnChosen();
        for (int i = 0; i < invokers.size(); i++) {
            boolean has = false;
            for (int j = 0; j < unChosen.size(); j++) {
                if (invokers.get(i).equals(unChosen.get(j))) {
                    has = true;
                    break;
                }
            }
            if (has){
                changedInvokers.add(invokers.get(i));
            }
        }
        return originLoadBalance.select(changedInvokers, url, invocation);
    }

    @Override
    public LoadBalance getCompositeLoadBalance() {
        return originLoadBalance;
    }
}
