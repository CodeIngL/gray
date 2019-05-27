package com.codeL.gray.dubbo.strategy.uip;

import com.codeL.gray.common.convert.TypeConverter;
import com.codeL.gray.core.context.GrayEnvContextBinder;
import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.dubbo.select.AbstractSelector;
import com.codeL.gray.dubbo.strategy.IndexedInvoker;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

import java.util.List;
import java.util.Map;

import static com.codeL.gray.core.context.GrayEnvContextBinder.getGlobalGrayEnvContext;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class UipSelector<T> extends AbstractSelector<T> {

    public UipSelector(TypeConverter typeConverter) {
        super(typeConverter);
    }

    @Override
    protected IndexedInvoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation, Policy policy) {
        DubboUipPolicy uipPolicy = (DubboUipPolicy) getTypeConverter().convert(policy);
        if (uipPolicy == null) {
            return null;
        }
        Map<String, DubboUipPolicy.UIPSets> uipSetsMap = uipPolicy.getDivdata();
        String keyId = getGlobalGrayEnvContext().get("sourceIp");
        /**
         * now we use userId matched invoker
         */
        IndexedInvoker<T> head = new IndexedInvoker<>(null, 0, false);
        IndexedInvoker<T> tmp = head;
        int index = -1;
        for (Invoker invoker : invokers) {
            URL remoteUrl = invoker.getUrl();
            String key = remoteUrl.getIp() + remoteUrl.getPort();
            if (uipSetsMap.containsKey(key)) {
                DubboUipPolicy.UIPSets uipSets = uipSetsMap.get(key);
                tmp.setNext(new IndexedInvoker(invoker, ++index, uipSets.contains(keyId)));
                tmp = tmp.getNext();
            }
        }
        return head;
    }

}
