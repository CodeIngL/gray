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

import static com.codeL.gray.core.context.GrayEnvContextBinder.getGlobalGrayEnvContext;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class UidSelector<T> extends AbstractSelector<T> {

    public UidSelector(TypeConverter typeConverter) {
        super(typeConverter);
    }

    /**
     * deal with invoker using one policy
     *
     * @param invokers
     * @param url
     * @param invocation
     * @param policy
     * @return
     */
    @Override
    protected IndexedInvoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation, Policy policy) {
        DubboUidPolicy uidPolicy = (DubboUidPolicy) getTypeConverter().convert(policy);
        if (uidPolicy == null) {
            return null;
        }
        Map<String, UidSets> uidSetsMap = uidPolicy.getDivdata();
        String userId = getGlobalGrayEnvContext().get("userId");
        int index = -1;
        /**
         * now we use userId matched invoker
         */
        IndexedInvoker<T> head = new IndexedInvoker<>(null, 0, false);
        IndexedInvoker<T> tmp = head;
        for (Invoker invoker : invokers) {
            URL remoteUrl = invoker.getUrl();
            String key = remoteUrl.getIp() + remoteUrl.getPort();
            if (uidSetsMap.containsKey(key)) {
                UidSets uidSets = uidSetsMap.get(key);
                tmp.setNext(new IndexedInvoker(invoker, ++index, uidSets.contains(userId)));
                tmp = tmp.getNext();
            }
        }
        return head;
    }
}
