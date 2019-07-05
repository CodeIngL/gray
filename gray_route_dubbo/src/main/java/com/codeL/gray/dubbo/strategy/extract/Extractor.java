package com.codeL.gray.dubbo.strategy.extract;

import com.codeL.gray.common.ServerTypeHolder;
import com.codeL.gray.common.convert.TypeHolder;
import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.core.strategy.extract.PExtract;
import com.codeL.gray.dubbo.select.Selector;
import com.codeL.gray.dubbo.select.WrappedSelector;
import com.codeL.gray.dubbo.strategy.CompositeIndexedInvoker;
import com.codeL.gray.dubbo.strategy.IndexedInvoker;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

import java.util.ArrayList;
import java.util.List;

import static com.codeL.gray.core.GrayType.P;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class Extractor<S extends Selector> implements PExtract {

    private static final String DUBBO_SERVER_TYPE = "dubbo";

    private static final String DUBBO_SERVER_TYPE_PREFIX = DUBBO_SERVER_TYPE + ":" + P.name() + ":";

    public Extractor() {
    }

    public <T> CompositeIndexedInvoker<T> extract(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        List<Policy> policies = extract();
        if (policies == null || policies.size() == 0) {
            return null;
        }

        List<Selector> chosenSelectors = new ArrayList<>();
        /**
         * if config error we do nothing rather then filter some correct config
         */
        for (Policy policy : policies) {
            TypeHolder typeHolder = new TypeHolder(DUBBO_SERVER_TYPE_PREFIX + policy.getDivtype());
            Selector selector = (S) mappingSelector.get(typeHolder);
            if (selector == null) {
                return null;
            }
            chosenSelectors.add(new WrappedSelector(selector, policy));
        }
        /**
         * now we filter all selector
         */
        CompositeIndexedInvoker result = new CompositeIndexedInvoker();
        for (Selector selector : chosenSelectors) {
            IndexedInvoker<T> indexedInvoker = selector.select(invokers, url, invocation, null);
            if (indexedInvoker == null) {
                continue;
            }
            while (indexedInvoker.getNext() != null) {
                indexedInvoker = indexedInvoker.getNext();
                result.add(indexedInvoker, indexedInvoker.isChoosed());
            }
        }
        return result;
    }

    public static void register(TypeHolder typeHolder, Selector selector) {
        mappingSelector.put(typeHolder, selector);
    }

    @Override
    public ServerTypeHolder extractServerType() {
        return new ServerTypeHolder(DUBBO_SERVER_TYPE);
    }
}
