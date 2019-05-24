package com.codeL.gray.activemq.p.strategy.uid;

import com.codeL.gray.activemq.p.strategy.uid.ActiveMqUidPolicy.UidSets;
import com.codeL.gray.core.strategy.Policy;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class PolicyUidUtil {

    public static ActiveMqUidPolicy covertFromPolicy(Policy policy) {
        if (policy == null) {
            return null;
        }
        ActiveMqUidPolicy activeMqUidPolicy = new ActiveMqUidPolicy();
        List<DivdataBean> divdataBeans = JSONObject.parseArray(policy.getDivdata(), DivdataBean.class);
        Map<String, UidSets> divData = new HashMap<>();
        for (DivdataBean bean : divdataBeans) {
            String originName = bean.getOriginName();
            if (!divData.containsKey(originName)) {
                UidSets uidSets = new UidSets();
                uidSets.setGoalName(bean.getGoalName());
                uidSets.getUids().addAll(bean.getUidset());
                divData.put(originName, uidSets);
            } else {
                UidSets uidSets = divData.get(originName);
                uidSets.getUids().addAll(bean.getUidset());
            }
        }
        if (divData.size() != 0) {
            activeMqUidPolicy.setDivdata(divData);
            return activeMqUidPolicy;
        }
        return null;
    }


    @Getter
    @Setter
    public static class DivdataBean {
        private String originName;
        private String goalName;
        private List<String> uidset;
    }
}
