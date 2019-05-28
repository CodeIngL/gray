package com.codeL.gray.jms.p.strategy.extract;

import com.codeL.gray.jms.p.select.Selector;
import com.codeL.gray.jms.p.select.WrappedSelector;
import com.codeL.gray.common.ServerTypeHolder;
import com.codeL.gray.common.convert.TypeHolder;
import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.core.strategy.extract.PExtract;

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
public class Extractor<T extends Selector> implements PExtract {

    private static final String JMS_SERVER_TYPE = "jms:" + P.name();

    private static final String JMS_SERVER_TYPE_PREFIX = JMS_SERVER_TYPE + ":";

    public String extract(String destinationName, Boolean pubSubDomain) {
        List<Policy> policies = extract();
        if (policies == null || policies.size() == 0) {
            return null;
        }

        List<Selector> chosenSelectors = new ArrayList<>();
        /**
         * if config error we do nothing rather then filter some correct config
         */
        for (Policy policy : policies) {
            TypeHolder typeHolder = new TypeHolder(JMS_SERVER_TYPE_PREFIX + policy.getDivtype());
            Selector selector = (T) mappingSelector.get(typeHolder);
            if (selector == null) {
                return null;
            }
            chosenSelectors.add(new WrappedSelector(selector, policy));
        }

        for (Selector selector : chosenSelectors) {
            String goalName = selector.select(destinationName, pubSubDomain, null);
            if (goalName != null) {
                return goalName;
            }
        }
        return destinationName;
    }

    public static void register(TypeHolder typeHolder, Selector selector) {
        mappingSelector.put(typeHolder, selector);
    }

    @Override
    public ServerTypeHolder extractServerType() {
        return new ServerTypeHolder(JMS_SERVER_TYPE);
    }
}
