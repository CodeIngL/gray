package com.codeL.gray.activemq.c.beanfactory;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class GrayBeanFactoryBinder {

    static final GrayBeanFactory DEFAULT_GRAY_STATUS = null;

    private static GrayBeanFactory globalGrayBeanFactory = null;

    static void bindGlobalGrayBeanFactory(GrayBeanFactory grayBeanFactory) {
        if (grayBeanFactory != null) {
            globalGrayBeanFactory = grayBeanFactory;
        } else {
            globalGrayBeanFactory = DEFAULT_GRAY_STATUS;
        }
    }

    static GrayBeanFactory getGlobalGrayBeanFactory() {
        return globalGrayBeanFactory;
    }
}
