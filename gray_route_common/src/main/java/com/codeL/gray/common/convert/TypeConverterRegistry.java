package com.codeL.gray.common.convert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class TypeConverterRegistry {

    private final ConcurrentHashMap<TypeHolder, TypeConverter> register = new ConcurrentHashMap<>();

    private static TypeConverterRegistry delegate = null;

    private TypeConverterRegistry() {
    }

    public static TypeConverterRegistry getGlobalInstance() {
        if (delegate == null) {
            synchronized (TypeConverterRegistry.class) {
                if (delegate != null) {
                    delegate = new TypeConverterRegistry();
                }
            }
        }
        return delegate;
    }

    public void addTypeConverter(TypeHolder typeHolder, TypeConverter converter) {
        register.put(typeHolder, converter);
    }

    public TypeConverter findTypeConverter(String name) {
        return register.get(new TypeHolder(name));
    }

    public TypeConverter findTypeConverter(TypeHolder typeHolder) {
        return register.get(typeHolder);
    }
}
