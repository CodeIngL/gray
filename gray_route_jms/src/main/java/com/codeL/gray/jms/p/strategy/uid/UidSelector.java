package com.codeL.gray.jms.p.strategy.uid;

import com.codeL.gray.jms.p.select.AbstractSelector;
import com.codeL.gray.jms.p.strategy.uid.JmsUidPolicy.UidSets;
import com.codeL.gray.common.convert.TypeConverter;
import com.codeL.gray.core.strategy.Policy;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class UidSelector<T> extends AbstractSelector<T> {

    public UidSelector(TypeConverter typeConverter) {
        super(typeConverter);
    }

    @Override
    protected String doSelect(String destinationName, boolean pubSubDomain, Policy policy) {
        JmsUidPolicy mqUidPolicy = (JmsUidPolicy) getTypeConverter().convert(policy);
        if (mqUidPolicy == null) {
            return null;
        }
        Map<String, UidSets> uidSetsMap = mqUidPolicy.getDivdata();

        UidSets uidSets = uidSetsMap.get(destinationName);
        if (uidSets == null) {
            return null;
        }
        String keyId = getGlobalGrayEnvContext().get("userId");
        if (uidSets.contains(keyId)) {
            String grayGoalName = uidSets.getGoalName();
            if (grayGoalName == null || "".equals(grayGoalName)) {
                log.error("userId policy has error, no goalName,userId:{}", keyId);
                return destinationName;
            }
            return grayGoalName;
        }
        return null;
    }

}
