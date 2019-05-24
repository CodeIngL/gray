package com.codeL.gray.core.context;

import org.slf4j.MDC;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class MDCGrayEnvContext implements GrayEnvContext {

    @Override
    public String get(String key) {
        return MDC.get(key);
    }
}
