package com.codeL.gray.activemq.support.c;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ImportGrayActiveMqCConfiguration.class)
public @interface EnableGreyActiveMqC {
}
