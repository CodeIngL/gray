package com.codeL.gray.core.context;

import static com.codeL.gray.core.GrayStatus.Close;
import static com.codeL.gray.core.GrayType.U;
import static com.codeL.gray.core.context.DefaultGrayContext.EMPTY_PG;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class GrayContextBinder {

    public static final GrayContext DEFAULT_GRAY_CONTEXT = new DefaultGrayContext(EMPTY_PG, U, Close, -1L);

    private static GrayContext globalGrayContext = null;

    public static void bindGlobalGrayContext(GrayContext context) {
        if (context != null) {
            globalGrayContext = context;
        } else {
            globalGrayContext = null;
        }
    }

    public static GrayContext getGlobalGrayContext() {
        return globalGrayContext;
    }
}
