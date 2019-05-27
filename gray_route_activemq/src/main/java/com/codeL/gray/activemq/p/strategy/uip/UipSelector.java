package com.codeL.gray.activemq.p.strategy.uip;

import com.codeL.gray.activemq.p.select.AbstractSelector;
import com.codeL.gray.common.convert.TypeConverter;
import com.codeL.gray.core.context.GrayEnvContextBinder;
import com.codeL.gray.core.strategy.Policy;
import lombok.extern.slf4j.Slf4j;

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
public class UipSelector<T> extends AbstractSelector<T> {

    public UipSelector(TypeConverter typeConverter) {
        super(typeConverter);
    }

    protected String doSelect(String destinationName, boolean pubSubDomain, Policy policy) {
        ActiveMqUipPolicy mqUipPolicy = (ActiveMqUipPolicy) getTypeConverter().convert(policy);
        if (mqUipPolicy == null) {
            return null;
        }
        Map<String, ActiveMqUipPolicy.UipSets> uipSetsMap = mqUipPolicy.getDivdata();

        ActiveMqUipPolicy.UipSets uipSets = uipSetsMap.get(destinationName);
        if (uipSets == null) {
            return null;
        }
        String keyId = GrayEnvContextBinder.getGlobalGrayEnvContext().get("sourceIp");
        if (uipSets.contains(keyId)) {
            String grayGoalName = uipSets.getGoalName();
            if (grayGoalName == null || "".equals(grayGoalName)) {
                log.error("ip policy has error, no goalName,ip:{}", keyId);
                return destinationName;
            }
            return grayGoalName;
        }
        return null;
    }
}
