package com.codeL.gray.core.watch;

import com.codeL.gray.common.NamedThreadFactory;
import com.codeL.gray.core.check.StatusCheck;
import com.codeL.gray.core.check.file.FileStatusCheck;
import com.codeL.gray.core.mbean.ExportUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.util.ServiceLoader;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.codeL.gray.core.context.GrayContextBinder.DEFAULT_GRAY_CONTEXT;
import static com.codeL.gray.core.context.GrayContextBinder.bindGlobalGrayContext;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Slf4j
public abstract class AbstractWatchDog implements WatchDog {

    private static final long DEFAULT_GRAY_DELAY_MS = 5000;

    private static final long DEFAULT_GRAY_PERIOD_MS = 20000;

    private static final long UNDEFINED_LONG = -1L;

    private static final long ILLEGAL_LONG = 0L;

    private static ScheduledExecutorService scheduledService = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("gray-watch"));

    @Setter
    private Long initDelayMs = UNDEFINED_LONG;

    @Setter
    private Long periodMs = UNDEFINED_LONG;

    @Setter
    StatusCheck statusCheck = null;

    @Setter
    MBeanServer mBeanServer = null;

    private void initSystem() {
        final String GRAY_DELAY_MS = "gray.delay.ms";
        final String GRAY_PERIOD_MS = "gray.period.ms";
        String initDelay = System.getProperty(GRAY_DELAY_MS);
        if (StringUtils.isNotEmpty(initDelay)) {
            try {
                initDelayMs = Long.valueOf(initDelay);
            } catch (Exception e) {
                // ignore
            }
        }
        if (initDelayMs != UNDEFINED_LONG && initDelayMs <= ILLEGAL_LONG) {
            throw new IllegalStateException("illegal watchdog's initDelay " + initDelay);
        }
        String period = System.getProperty(GRAY_PERIOD_MS);
        if (StringUtils.isNotEmpty(period)) {
            try {
                periodMs = Long.valueOf(period);
            } catch (Exception e) {
                // ignore
            }
        }
        if (periodMs != UNDEFINED_LONG && periodMs <= 0) {
            throw new IllegalStateException("illegal watchdog's periodMs " + periodMs);
        }
    }

    private void initDefault() {
        if (initDelayMs == UNDEFINED_LONG) {
            initDelayMs = DEFAULT_GRAY_DELAY_MS;
        }
        if (periodMs == UNDEFINED_LONG) {
            periodMs = DEFAULT_GRAY_PERIOD_MS;
        }
        if (statusCheck == null) {
            initDefaultStatusCheck();
        }
        if (mBeanServer == null) {
            initDefaultMBeanServer();
        }
    }

    private void initDefaultStatusCheck() {
        ServiceLoader<StatusCheck> loader = ServiceLoader.load(StatusCheck.class);
        for (StatusCheck statusCheck : loader) {
            this.statusCheck = statusCheck;
            log.info("use {} to check switch", statusCheck.getClass());
        }
        statusCheck = new FileStatusCheck();
    }

    private void initDefaultMBeanServer() {
        mBeanServer = ManagementFactory.getPlatformMBeanServer();
    }

    private void validate() {
        if (initDelayMs <= ILLEGAL_LONG) {
            throw new IllegalStateException("illegal watchdog's initDelay " + initDelayMs);
        }
        if (periodMs <= ILLEGAL_LONG) {
            throw new IllegalStateException("illegal watchdog's periodMs " + periodMs);
        }
        if (statusCheck == null) {
            throw new IllegalStateException("Uninitialized watchdog's statusCheck");
        }
        if (mBeanServer == null) {
            throw new IllegalStateException("Uninitialized watchdog's mBeanServer");
        }
    }

    @PostConstruct
    public void watch() {
        bindGlobalGrayContext(DEFAULT_GRAY_CONTEXT);
        initSystem();
        initDefault();
        validate();
        try {
            ExportUtil.exportClosed(mBeanServer);
        } catch (Exception e) {
            log.error("error config");
            throw new IllegalStateException("can't create mbean");
        }
        customInit();
        scheduledService.scheduleAtFixedRate(doWatch(), initDelayMs, periodMs, TimeUnit.MILLISECONDS);
    }

    protected abstract void customInit();

    protected abstract Runnable doWatch();

}
