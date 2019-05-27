package com.codeL.gray.activemq.p.strategy.uid;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class ActiveMqUidPolicy {

    private final String divtype = "uid";

    @Getter
    @Setter
    private Map<String,UidSets> divdata;

    @Data
    public static class UidSets {
        private String goalName;
        private Set<String> uids = new HashSet<>();
        public boolean contains(String id){
            return uids.contains(id);
        }
    }
}
