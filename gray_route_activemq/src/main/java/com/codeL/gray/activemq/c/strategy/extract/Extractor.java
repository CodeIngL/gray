package com.codeL.gray.activemq.c.strategy.extract;

import com.codeL.gray.activemq.c.select.PolicyUtil;
import com.codeL.gray.activemq.c.select.Selector;
import com.codeL.gray.activemq.c.select.WrappedSelector;
import com.codeL.gray.common.ServerTypeHolder;
import com.codeL.gray.core.context.GrayContext;
import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.core.strategy.PolicyGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.codeL.gray.core.GrayType.C;
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

    private static final String ACTIVE_MQ_SERVER_TYPE = "activemq:" + C.name();

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
        List<Policy> usedPolicies = serverTypePolicies.get(new ServerTypeHolder(ACTIVE_MQ_SERVER_TYPE));
        if (usedPolicies == null || usedPolicies.size() == 0) {
            return null;
        }

        List<Selector> chociedSelectors = new ArrayList<>();
        for (Policy policy : usedPolicies) {
            chociedSelectors.add(
                    new WrappedSelector(new Selector() {
                        @Override
                        public String select(String destinationName, Policy policy) {
                            Map<String, String> resultM = PolicyUtil.covertFromPolicy(policy);
                            String goalName = resultM.get(destinationName);
                            if (goalName == null || "".equals(goalName)) {
                                return destinationName;
                            }
                            return goalName;
                        }
                    }, policy));
        }
        String result = null;
        for (Selector selector : chociedSelectors) {
            result = selector.select(destinationName, null);
            if (result != null) {
                return result;
            }
        }
        return destinationName;
    }
}
