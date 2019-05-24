package com.codeL.gray.core.watch;

import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.GrayType;
import com.codeL.gray.core.check.file.FileStatusCheck;
import com.codeL.gray.core.context.DefaultGrayContext;
import com.codeL.gray.core.mbean.ExportUtil;
import com.codeL.gray.core.meta.DefaultMetaReader;
import com.codeL.gray.core.meta.DefaultTypeReader;
import com.codeL.gray.core.strategy.PolicyGroup;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

import static com.codeL.gray.core.GrayType.U;
import static com.codeL.gray.core.check.file.FileStatusCheck.META_PATH;
import static com.codeL.gray.core.context.GrayContextBinder.DEFAULT_GRAY_CONTEXT;
import static com.codeL.gray.core.context.GrayContextBinder.bindGlobalGrayContext;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Slf4j
public class FileWatchDog extends AbstractWatchDog {

    private final AtomicLong index = new AtomicLong(1);

    private Long lastChanged = -1L;

    @Override
    protected void customInit() {
        //ignore
    }

    @Override
    protected Runnable doWatch() {
        return () -> {
            GrayStatus status;
            GrayType type;
            PolicyGroup policyGroup;
            status = statusCheck.checkStatus();
            switch (status) {
                case Close:
                    ExportUtil.exportClosed(mBeanServer);
                    bindGlobalGrayContext(DEFAULT_GRAY_CONTEXT);
                    return;
                default:
            }
            try (
                    InputStream checkPath = new FileInputStream(FileStatusCheck.STATUS_CHECK_PATH);
                    InputStream metaPath = new FileInputStream(META_PATH)) {
                type = new DefaultTypeReader().readType(checkPath);
                policyGroup = new DefaultMetaReader().readPolicyGroup(metaPath);
                ExportUtil.export(mBeanServer, status, type);
            } catch (Exception e) {
                log.warn("something wrong happen.", e);
                ExportUtil.exportClosed(mBeanServer);
                bindGlobalGrayContext(DEFAULT_GRAY_CONTEXT);
                return;
            }
            if (policyGroup == null) {
                log.error("error status. gray is open. but no meta");
                bindGlobalGrayContext(DEFAULT_GRAY_CONTEXT);
                return;
            }
            if (status != statusCheck.checkStatus()) {
                bindGlobalGrayContext(DEFAULT_GRAY_CONTEXT);
                return;
            }
            boolean updated = false;
            File file = new File(META_PATH);
            if (file.exists()) {
                if (lastChanged == -1) {
                    lastChanged = file.lastModified();
                    updated = true;
                }
                if (lastChanged != file.lastModified()) {
                    lastChanged = file.lastModified();
                    updated = true;
                }
            }
            bindGlobalGrayContext(new DefaultGrayContext(policyGroup, type == null ? U : type, status, updated ? index.get() : index.getAndIncrement()));
        };
    }

}
