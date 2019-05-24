package com.codeL.gray.core.context;

import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.GrayType;
import com.codeL.gray.core.strategy.PolicyGroup;
import lombok.Getter;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class DefaultGrayContext implements GrayContext {

    public static final PolicyGroup EMPTY_PG = new PolicyGroup();

    private final PolicyGroup policyGroup;
    private final GrayType grayType;
    private final GrayStatus grayStatus;
    @Getter
    private final long index;

    public DefaultGrayContext(PolicyGroup policyGroup, GrayType grayType, GrayStatus grayStatus, Long index) {
        this.policyGroup = policyGroup;
        this.grayType = grayType;
        this.grayStatus = grayStatus;
        this.index = index;
    }

    @Override
    public PolicyGroup getPolicyGroup() {
        return policyGroup;
    }

    @Override
    public GrayType getGrayType() {
        return grayType;
    }

    @Override
    public GrayStatus getGrayStatus() {
        return grayStatus;
    }
}
