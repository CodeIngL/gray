package com.codeL.gray.core.check.file;

import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.check.StatusCheck;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:22
 */
@Slf4j
public class FileStatusCheck implements StatusCheck {

    public static final String STATUS_CHECK_PATH = "/usr/serveryou/grays/status.check";

    public static final String META_PATH = "/usr/serveryou/grays/meta";

    public GrayStatus checkStatus() {
        File file = new File(STATUS_CHECK_PATH);
        if (!file.exists()) {
            log.info("gray is closed");
            return GrayStatus.Close;
        }
        log.info("gray is opened");
        return GrayStatus.Open;
    }

}
