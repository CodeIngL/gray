package com.codeL.gray.core.strategy.extract;

import com.codeL.gray.common.ServerTypeHolder;
import com.codeL.gray.core.context.GrayContext;
import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.core.strategy.PolicyGroup;

import java.util.List;
import java.util.Map;

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
public interface Extract {

    default List<Policy> extract() {
        GrayContext context = getGlobalGrayContext();
        PolicyGroup group = context.getPolicyGroup();
        if (group == null || group == EMPTY_PG) {
            return null;
        }
        Map<ServerTypeHolder, List<Policy>> serverTypePolicies = group.getGroup();
        List<Policy> usedPolicies = serverTypePolicies.get(extractServerType());
        if (usedPolicies == null || usedPolicies.size() == 0) {
            return null;
        }
        return usedPolicies;
    }

    ServerTypeHolder extractServerType();

}
