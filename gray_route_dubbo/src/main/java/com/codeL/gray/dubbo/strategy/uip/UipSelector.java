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

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
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
        DubboUipPolicy dubboPolicy = (DubboUipPolicy) getTypeConverter().convert(policy);
        if (dubboPolicy == null) {
            return null;
        }
        Map<String, DubboUipPolicy.UIPSets> uipSetsMap = dubboPolicy.getDivdata();
        IndexedInvoker result = null;
        String keyId = GrayEnvContextBinder.getGlobalGrayEnvContext().get("sourceIp");
        int index = -1;
        for (Invoker invoker : invokers) {
            index++;
            String ip = invoker.getUrl().getIp();
            String port = String.valueOf(invoker.getUrl().getPort());
            String key = ip + port;
            if (uipSetsMap.containsKey(key)) {
                DubboUipPolicy.UIPSets uipSets = uipSetsMap.get(key);
                if (uipSets.contains(keyId)) {
                    return new IndexedInvoker(invoker, index, true);
                }
                return new IndexedInvoker(invoker, index, false);
            }
        }
        return result;
    }

}
