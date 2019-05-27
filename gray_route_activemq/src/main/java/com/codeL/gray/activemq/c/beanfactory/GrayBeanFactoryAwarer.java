package com.codeL.gray.activemq.c.beanfactory;

import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.context.DefaultGrayContext;
import com.codeL.gray.core.context.GrayContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import static com.codeL.gray.activemq.c.beanfactory.GrayBeanFactoryBinder.getGlobalGrayBeanFactory;
import static com.codeL.gray.core.context.GrayContextBinder.getGlobalGrayContext;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Slf4j
public class GrayBeanFactoryAwarer {

    private Long index = -1L;

    private boolean lazyStart = false;

    @Scheduled(cron = "0 */1 * * * ?")
    public void wrapper() {
        /**
         * 开始是-1.ope
         */
        GrayContext context = getGlobalGrayContext();
        if (!DefaultGrayContext.class.isAssignableFrom(context.getClass())) {
            return;
        }
        if (context.getGrayStatus() == GrayStatus.Open) {
            lazyStart = true;
        }
        if (!lazyStart) {
            log.warn("no we can't open any jms Listener. should opened then closed. last switch to normal");
            return;
        }
        DefaultGrayContext grayContext = (DefaultGrayContext) context;
        long previousIndex = index;
        long currentIndex = grayContext.getIndex();
        if (previousIndex == currentIndex || previousIndex * currentIndex < 0) {
            return;
        }
        log.warn("refresh jms container, index:{}", previousIndex);
        getGlobalGrayBeanFactory().refresh();
        index = currentIndex;
    }
}
