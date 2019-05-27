package com.codeL.gray.core.meta;

import com.codeL.gray.common.ServerTypeHolder;
import com.codeL.gray.core.constant.DivSteps;
import com.codeL.gray.core.strategy.InternalPolicyGroup;
import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.core.strategy.PolicyGroup;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Slf4j
public class DefaultMetaReader implements MetaReader {

    @Override
    public PolicyGroup readPolicyGroup(InputStream inputStream) {
        try {
            InternalPolicyGroup internalPolicyGroup = JSONObject.parseObject(inputStream, InternalPolicyGroup.class);
            if (internalPolicyGroup == null) {
                return null;
            }
            Map<String, Policy> policyMap = internalPolicyGroup.getGroup();
            if (policyMap == null || policyMap.size() == 0) {
                return null;
            }
            PolicyGroup policyGroup = new PolicyGroup();
            Map<ServerTypeHolder, List<Policy>> resultMap = policyGroup.getGroup();
            for (String divstep : DivSteps.divsteps) {
                Policy policy = policyMap.get(divstep);
                if (policy == null) {
                    continue;
                }
                String serverType = policy.getServertype();
                ServerTypeHolder serverTypeHolder = new ServerTypeHolder(serverType);
                if (resultMap.containsKey(serverTypeHolder)) {
                    List<Policy> singles = resultMap.get(serverTypeHolder);
                    singles.add(policy);
                    continue;
                }
                List<Policy> singles = new LinkedList<>();
                singles.add(policy);
                resultMap.put(serverTypeHolder, singles);
            }
            return policyGroup;
        } catch (Exception e) {
            log.error("read policyGroup error", e);
        }
        return null;
    }
}
