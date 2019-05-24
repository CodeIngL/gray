package com.codeL.gray.dubbo.strategy.extract;

import com.codeL.gray.common.ServerTypeHolder;
import com.codeL.gray.common.convert.TypeConverterDelegate;
import com.codeL.gray.common.convert.TypeHolder;
import com.codeL.gray.core.context.GrayContext;
import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.core.strategy.PolicyGroup;
import com.codeL.gray.dubbo.select.Selector;
import com.codeL.gray.dubbo.select.WrappedSelector;
import com.codeL.gray.dubbo.strategy.IndexedInvoker;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.codeL.gray.core.GrayType.P;
import static com.codeL.gray.core.context.DefaultGrayContext.EMPTY_PG;
import static com.codeL.gray.core.context.GrayContextBinder.getGlobalGrayContext;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class Extractor {

    private static final String DUBBO_SERVER_TYPE = "dubbo";

    private static final ConcurrentHashMap<TypeHolder, Selector> mappingSelector = new ConcurrentHashMap<>();

    private TypeConverterDelegate delegate;

    public Extractor(TypeConverterDelegate delegate) {
        this.delegate = delegate;
    }

    public <T> IndexedInvoker<T> extract(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        GrayContext context = getGlobalGrayContext();
        PolicyGroup group = context.getPolicyGroup();
        if (group == null || group == EMPTY_PG) {
            return null;
        }
        Map<ServerTypeHolder, List<Policy>> serverTypePolicies = group.getGroup();
        if (!serverTypePolicies.containsKey(new ServerTypeHolder(DUBBO_SERVER_TYPE))) {
            return null;
        }
        List<Policy> usedPolicy = serverTypePolicies.get(new ServerTypeHolder(DUBBO_SERVER_TYPE));
        if (usedPolicy == null || usedPolicy.size() == 0) {
            return null;
        }

        List<Selector> chociedSelectors = new ArrayList<>();
        String prefix = DUBBO_SERVER_TYPE + ":" + P.name() + ":";
        for (Policy policy : usedPolicy) {
            TypeHolder typeHolder = new TypeHolder(prefix + policy.getDivtype());
            Selector selector = mappingSelector.get(typeHolder);
            if (selector == null) {
                return null;
            }
            chociedSelectors.add(new WrappedSelector(selector, policy));
        }
        IndexedInvoker<T>  result = null;
        for (Selector selector : chociedSelectors) {
            result = selector.select(invokers, url, invocation, null);
            if (result == null || !result.isChoiced()) {
                return result;
            }
        }
        return result;
    }

    public static void register(TypeHolder typeHolder, Selector selector){
        mappingSelector.put(typeHolder, selector);
    }
}
