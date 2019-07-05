package com.codeL.gray.common.convert;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public interface TypeConverter<S, T> {
    
    T convert(S source);

    default boolean isEmpty(T t) {
        if (t == null) {
            return false;
        }
        return true;
    }
}