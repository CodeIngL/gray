package com.codeL.gray.activemq.c.select;

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
public class PolicyUtil {

    public static Map<String, String> covertFromPolicy(Policy policy) {
        if (policy == null) {
            return null;
        }
        List<DivdataBean> divdataBeans = JSONObject.parseArray(policy.getDivdata(), DivdataBean.class);
        Map<String, String> map = new HashMap<>();
        if (divdataBeans == null || divdataBeans.size() == 0) {
            return new HashMap<>();
        }
        for (DivdataBean divdataBean : divdataBeans) {
            map.put(divdataBean.getOriginName(), divdataBean.getGoalName());
        }
        return map;
    }


    @Getter
    @Setter
    public static class DivdataBean {
        private String originName;
        private String goalName;
    }
}
