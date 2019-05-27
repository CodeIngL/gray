package com.codeL.gray.activemq.c.strategy.extract;

import com.codeL.gray.activemq.c.select.PolicyUtil;
import com.codeL.gray.activemq.c.select.Selector;
import com.codeL.gray.activemq.c.select.WrappedSelector;
import com.codeL.gray.common.ServerTypeHolder;
import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.core.strategy.extract.Extract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.codeL.gray.core.GrayType.C;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class Extractor implements Extract {

    private static final String ACTIVE_MQ_SERVER_TYPE = "activemq:" + C.name();

    public String extract(String destinationName, Boolean pubSubDomain) {
        List<Policy> policies = extract();
        if (policies == null || policies.size() == 0) {
            return null;
        }
        List<Selector> chosenSelectors = new ArrayList<>();
        for (Policy policy : policies) {
            chosenSelectors.add(
                    new WrappedSelector((originalName, policy1) -> {
                        Map<String, String> resultM = PolicyUtil.covertFromPolicy(policy1);
                        String goalName = resultM.get(originalName);
                        if (goalName == null || "".equals(goalName)) {
                            return originalName;
                        }
                        return goalName;
                    }, policy));
        }
        for (Selector selector : chosenSelectors) {
            String result = selector.select(destinationName, null);
            if (result != null) {
                return result;
            }
        }
        return destinationName;
    }

    @Override
    public ServerTypeHolder extractServerType() {
        return new ServerTypeHolder(ACTIVE_MQ_SERVER_TYPE);
    }
}
