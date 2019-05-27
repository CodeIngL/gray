package com.codeL.gray.activemq.p.strategy.uid;

import com.codeL.gray.activemq.p.select.AbstractSelector;
import com.codeL.gray.activemq.p.strategy.uid.ActiveMqUidPolicy.UidSets;
import com.codeL.gray.common.convert.TypeConverter;
import com.codeL.gray.core.context.GrayEnvContextBinder;
import com.codeL.gray.core.strategy.Policy;

import java.util.Map;


/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class UidSelector<T> extends AbstractSelector<T> {

    public UidSelector(TypeConverter typeConverter) {
        super(typeConverter);
    }

    @Override
    protected String doSelect(String destinationName, boolean pubSubDomain, Policy policy) {
        ActiveMqUidPolicy mqUidPolicy = (ActiveMqUidPolicy) getTypeConverter().convert(policy);
        if (mqUidPolicy == null) {
            return destinationName;
        }
        Map<String, UidSets> uidSetsMap = mqUidPolicy.getDivdata();

        UidSets uidSets = uidSetsMap.get(destinationName);
        if (uidSets == null) {
            return destinationName;
        }
        String keyId = GrayEnvContextBinder.getGlobalGrayEnvContext().get("userId");
        if (uidSets.contains(keyId)) {
            return uidSets.getGoalName();
        }
        return destinationName;
    }

}
