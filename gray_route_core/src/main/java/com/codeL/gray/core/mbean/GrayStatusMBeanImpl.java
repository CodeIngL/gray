package com.codeL.gray.core.mbean;

import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.GrayType;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
class GrayStatusMBeanImpl implements GrayStatusMBean {

    private final GrayStatus grayStatus;

    private final GrayType grayType;

    GrayStatusMBeanImpl(GrayStatus grayStatus, GrayType grayType) {
        this.grayStatus = grayStatus;
        this.grayType = grayType;
    }

    @Override
    public String getGrayStatus() {
        return grayStatus.getDescription();
    }

    @Override
    public String getGrayType() {
        return grayType.name();
    }
}
