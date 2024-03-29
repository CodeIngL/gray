package com.codeL.gray.core.strategy;

import com.codeL.gray.common.ServerTypeHolder;
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
@Setter
@Getter
public class PolicyGroup {
    private Map<ServerTypeHolder, List<Policy>> group = new HashMap<>();
}
