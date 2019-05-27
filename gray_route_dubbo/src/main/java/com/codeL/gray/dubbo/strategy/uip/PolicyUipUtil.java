package com.codeL.gray.dubbo.strategy.uip;

import com.codeL.gray.core.strategy.Policy;
import com.codeL.gray.dubbo.strategy.uip.DubboUipPolicy.UIPSets;
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


    public static DubboUipPolicy covertFromPolicy(Policy policy) {
        if (policy == null) {
            return null;
        }
        DubboUipPolicy dubboPolicy = new DubboUipPolicy();
        List<DivdataBean> divdataBeans = JSONObject.parseArray(policy.getDivdata(), DivdataBean.class);
        Map<String, UIPSets> divData = new HashMap<>();
        for (DivdataBean bean : divdataBeans) {
            if (!divData.containsKey(bean.getUpstream())) {
                UIPSets uipSets = new UIPSets();
                uipSets.getIps().addAll(bean.getUipset());
                divData.put(bean.getUpstream(), uipSets);
            } else {
                UIPSets uipSets = divData.get(bean.getUpstream());
                uipSets.getIps().addAll(bean.getUipset());
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
         * divdata : [{"uipset":["1234","5124","653"],"upstream":"beta1"},{"uipset":["3214","652","145"],"upstream":"beta2"}]
         */
        /**
         * uipset : ["1234","5124","653"]
         * upstream : beta1
         */

        private String upstream;
        private List<String> uipset;
    }
}
