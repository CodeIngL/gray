package com.codeL.gray.core.check;

import com.codeL.gray.core.GrayStatus;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class OkCheck implements StatusCheck {

    public GrayStatus checkStatus() {
        return GrayStatus.Open;
    }
}
