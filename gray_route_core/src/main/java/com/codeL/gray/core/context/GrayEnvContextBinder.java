package com.codeL.gray.core.context;

import java.util.ServiceLoader;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class GrayEnvContextBinder {

    private static GrayEnvContext globalGrayEnvContext = null;

    public static void bindGlobalGrayEnvContext(GrayEnvContext context) {
        if (context != null) {
            globalGrayEnvContext = context;
        } else {
            globalGrayEnvContext = new MDCGrayEnvContext();
        }
    }

    public static GrayEnvContext getGlobalGrayEnvContext() {
        if (globalGrayEnvContext == null) {
            ServiceLoader<GrayEnvContext> serviceLoader = ServiceLoader.load(GrayEnvContext.class);
            for (GrayEnvContext envContext : serviceLoader) {
                return globalGrayEnvContext = envContext;
            }
            globalGrayEnvContext = new MDCGrayEnvContext();
            return globalGrayEnvContext;
        }
        return globalGrayEnvContext;
    }
}
