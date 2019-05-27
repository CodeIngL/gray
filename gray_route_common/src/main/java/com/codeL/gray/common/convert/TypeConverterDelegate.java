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
public class TypeConverterDelegate {

    private ConcurrentHashMap<TypeHolder, TypeConverter> register = new ConcurrentHashMap<>();

    public TypeConverterDelegate(ConcurrentHashMap<TypeHolder, TypeConverter> register) {
        this.register = register;
    }

    public TypeConverterDelegate() {
    }

    public void setRegister(ConcurrentHashMap<TypeHolder, TypeConverter> register) {
        this.register = register;
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
