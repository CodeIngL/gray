package com.codeL.gray.core.support.condition;

import com.codeL.gray.core.support.condition.strategy.Calculation;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:46
 */
public class AutoCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (metadata == null || !metadata.isAnnotated(Calculation.class.getName())) {
            return false;
        }
        try {
            String className = getCalculationClasses(metadata);
            if (className == null) {
                return false;
            }
            Class.forName(className);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private String getCalculationClasses(AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(Calculation.class.getName(), true);
        Object values = (attributes != null ? attributes.get("value") : null);
        return (String) values;
    }

}
