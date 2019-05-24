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
public enum GrayStatus {

    /**
     * �ر�״̬
     */
    Close(false, "closed"),
    /**
     * ����״̬
     */
    Open(true, "opened");

    Boolean open;

    String description;

    GrayStatus(Boolean open, String description) {
        this.open = open;
        this.description = description;
    }
}
