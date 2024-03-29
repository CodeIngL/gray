package com.codeL.gray.jms.p.strategy.uip;

import com.codeL.gray.jms.p.strategy.uip.JmsUipPolicy.UipSets;
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
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class PolicyUipUtil {

    public static JmsUipPolicy covertFromPolicy(Policy policy) {
        if (policy == null) {
            return null;
        }
        JmsUipPolicy jmsUipPolicy = new JmsUipPolicy();
        List<DivdataBean> divdataBeans = JSONObject.parseArray(policy.getDivdata(), DivdataBean.class);
        Map<String, UipSets> divData = new HashMap<>();
        for (DivdataBean bean : divdataBeans) {
            String originName = bean.getOriginName();
            if (!divData.containsKey(originName)) {
                UipSets uipSets = new UipSets();
                uipSets.setGoalName(bean.getGoalName());
                uipSets.getUips().addAll(bean.getUipset());
                divData.put(originName, uipSets);
            } else {
                UipSets uipSets = divData.get(originName);
                uipSets.getUips().addAll(bean.getUipset());
            }
        }
        if (divData.size() != 0) {
            jmsUipPolicy.setDivdata(divData);
            return jmsUipPolicy;
        }
        return null;
    }


    @Getter
    @Setter
    public static class DivdataBean {
        private String originName;
        private String goalName;
        private List<String> uipset;
    }
}
