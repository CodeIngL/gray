package com.codeL.gray.activemq.p.strategy.extract;

import com.codeL.gray.activemq.p.select.Selector;
import com.codeL.gray.activemq.p.select.WrappedSelector;
import com.codeL.gray.common.ServerTypeHolder;
import com.codeL.gray.common.convert.TypeHolder;
import com.codeL.gray.core.context.GrayContext;
import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.core.strategy.PolicyGroup;

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
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class Extractor {

    private static final String ACTIVE_MQ_SERVER_TYPE = "activemq:" + P.name();

    private static final String ACTIVE_MQ_SERVER_TYPE_PREFIX = ACTIVE_MQ_SERVER_TYPE + ":";

    private static final ConcurrentHashMap<TypeHolder, Selector> mappingSelector = new ConcurrentHashMap<>();

    public String extract(String destinationName, Boolean pubSubDomain) {
        GrayContext context = getGlobalGrayContext();
        PolicyGroup group = context.getPolicyGroup();
        if (group == null || group == EMPTY_PG) {
            return null;
        }
        Map<ServerTypeHolder, List<Policy>> serverTypePolicies = group.getGroup();
        if (!serverTypePolicies.containsKey(new ServerTypeHolder(ACTIVE_MQ_SERVER_TYPE))) {
            return null;
        }
        List<Policy> usedPolicy = serverTypePolicies.get(new ServerTypeHolder(ACTIVE_MQ_SERVER_TYPE));
        if (usedPolicy == null || usedPolicy.size() == 0) {
            return null;
        }
        List<Selector> chociedSelectors = new ArrayList<>();
        /**
         * if config error we do nothing rather then filter some correct config
         */
        for (Policy policy : usedPolicy) {
            TypeHolder typeHolder = new TypeHolder(ACTIVE_MQ_SERVER_TYPE_PREFIX + policy.getDivtype());
            Selector selector = mappingSelector.get(typeHolder);
            if (selector == null) {
                return null;
            }
            chociedSelectors.add(new WrappedSelector(selector, policy));
        }

        String result = null;
        for (Selector selector : chociedSelectors) {
            result = selector.select(destinationName, pubSubDomain, null);
            if (result != null) {
                return result;
            }
        }
        return result;
    }

    public static void register(TypeHolder typeHolder, Selector selector) {
        mappingSelector.put(typeHolder, selector);
    }
}
