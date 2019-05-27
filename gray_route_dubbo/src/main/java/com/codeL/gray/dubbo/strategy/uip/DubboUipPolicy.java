package com.codeL.gray.dubbo.strategy.uip;

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
public class DubboUipPolicy {

    private final String divtype = "ip";

    @Getter
    @Setter
    private Map<String, UIPSets> divdata;

    @Data
    public static class UIPSets {
        private Set<String> ips = new HashSet<>();
        public boolean contains(String id){
            return ips.contains(id);
        }
    }
}
