package com.codeL.gray.dubbo.strategy.uid;

import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.dubbo.strategy.uid.DubboUidPolicy.UidSets;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class PolicyUidUtil {


    public static DubboUidPolicy covertFromPolicy(Policy policy) {
        if (policy == null) {
            return null;
        }
        DubboUidPolicy dubboPolicy = new DubboUidPolicy();
        List<DivdataBean> divdataBeans = JSONObject.parseArray(policy.getDivdata(), DivdataBean.class);
        Map<String, UidSets> divData = new HashMap<>();
        for (DivdataBean bean : divdataBeans) {
            if (!divData.containsKey(bean.getUpstream())) {
                UidSets uidSets = new UidSets();
                uidSets.getUids().addAll(bean.getUidset());
                divData.put(bean.getUpstream(), uidSets);
            } else {
                UidSets uidSets = divData.get(bean.getUpstream());
                uidSets.getUids().addAll(bean.getUidset());
            }
        }
        if (divData.size() != 0) {
            dubboPolicy.setDivdata(divData);
            return dubboPolicy;
        }
        return null;
    }


    @Getter
    @Setter
    public static class DivdataBean {

        /**
         * divdata : [{"uidset":["1234","5124","653"],"upstream":"beta1"},{"uidset":["3214","652","145"],"upstream":"beta2"}]
         */
        /**
         * uidset : ["1234","5124","653"]
         * upstream : beta1
         */

        private String upstream;
        private List<String> uidset;
    }
}
