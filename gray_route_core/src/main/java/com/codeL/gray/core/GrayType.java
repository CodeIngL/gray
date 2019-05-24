package com.codeL.gray.core;

import lombok.Getter;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Getter
public enum GrayType {

    U(""),

    P("that"),

    C("this");

    String value;

    GrayType(String value) {
        this.value = value;
    }

    public static GrayType toGrayType(String value) {
        if (C.getValue().equals(value)) {
            return C;
        }
        if (P.getValue().equals(value)) {
            return P;
        }
        return U;
    }

}
