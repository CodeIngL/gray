package com.codeL.gray.dubbo.strategy.uid;

import com.codeL.gray.common.convert.TypeConverter;
import com.codeL.gray.core.context.GrayEnvContextBinder;
import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.dubbo.select.AbstractSelector;
import com.codeL.gray.dubbo.strategy.IndexedInvoker;
import com.codeL.gray.dubbo.strategy.uid.DubboUidPolicy.UidSets;
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
public class UidSelector<T> extends AbstractSelector<T> {

    public UidSelector(TypeConverter typeConverter) {
        super(typeConverter);
    }

    @Override
    protected IndexedInvoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation, Policy policy) {
        DubboUidPolicy dubboPolicy = (DubboUidPolicy) getTypeConverter().convert(policy);
        if (dubboPolicy == null) {
            return null;
        }
        Map<String, UidSets> uidSetsMap = dubboPolicy.getDivdata();
        IndexedInvoker result = null;
        String keyId = GrayEnvContextBinder.getGlobalGrayEnvContext().get("userId");
        int index = -1;
        for (Invoker invoker : invokers) {
            String ip = invoker.getUrl().getIp();
            String port = String.valueOf(invoker.getUrl().getPort());
            String key = ip + port;
            if (uidSetsMap.containsKey(key)) {
                UidSets uidSets = uidSetsMap.get(key);
                if (uidSets.contains(keyId)) {
                    return new IndexedInvoker(invoker, ++index, true);
                }
                return new IndexedInvoker(invoker, ++index, false);
            }
        }
        return result;
    }
}
