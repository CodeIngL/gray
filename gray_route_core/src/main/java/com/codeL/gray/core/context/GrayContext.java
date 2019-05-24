package com.codeL.gray.core.context;

import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.GrayType;
import com.codeL.gray.core.strategy.PolicyGroup;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public interface GrayContext {

    PolicyGroup getPolicyGroup();

    GrayType getGrayType();

    GrayStatus getGrayStatus();

}
