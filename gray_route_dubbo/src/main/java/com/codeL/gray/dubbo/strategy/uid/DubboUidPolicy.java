package com.codeL.gray.dubbo.strategy.uid;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
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
public class DubboUidPolicy {

    private final String divtype = "uid";

    @Getter
    @Setter
    private Map<String, UidSets> divdata;

    @Data
    public static class UidSets {
        private Set<String> uids = new HashSet<>();

        public boolean contains(String id) {
            return uids.contains(id);
        }
    }
}
