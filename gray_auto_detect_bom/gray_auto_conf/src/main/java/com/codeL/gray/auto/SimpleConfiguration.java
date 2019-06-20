package com.codeL.gray.auto;

import com.codeL.gray.jms.support.EnableGrayJms;
import com.codeL.gray.core.watch.HttpURLWatchDog;
import com.codeL.gray.core.watch.WatchDog;
import com.codeL.gray.dubbo.support.EnableGrayDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@EnableGrayJms
@EnableGrayDubbo
@Configuration
public class SimpleConfiguration {

    @Bean
    WatchDog watchDog(@Value("${gray.http.url}") String url) {
        HttpURLWatchDog watchDog = new HttpURLWatchDog();
        watchDog.setUrl(url);
        return watchDog;
    }
}
