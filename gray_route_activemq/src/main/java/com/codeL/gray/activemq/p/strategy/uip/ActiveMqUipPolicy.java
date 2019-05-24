package com.codeL.gray.activemq.p.strategy.uip;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class ActiveMqUipPolicy {

    private final String divtype = "ip";

    @Getter
    @Setter
    private Map<String, UipSets> divdata;

    @Data
    public static class UipSets {
        private String goalName;
        private Set<String> uips = new HashSet<>();
        public boolean contains(String ip){
            return uips.contains(ip);
        }
    }
}
