package com.codeL.gray.jms.support;

import com.codeL.gray.jms.support.c.ImportGrayJmsCConfiguration;
import com.codeL.gray.jms.support.p.ImportGrayJmsPConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ImportGrayJmsCConfiguration.class, ImportGrayJmsPConfiguration.class})
public @interface EnableGrayJms {
}
