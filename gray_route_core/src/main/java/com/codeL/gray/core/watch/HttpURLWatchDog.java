package com.codeL.gray.core.watch;

import com.codeL.gray.common.http.Response;
import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.check.remote.HttpURLStatusCheck;
import com.codeL.gray.core.context.DefaultGrayContext;
import com.codeL.gray.core.meta.DefaultMetaReader;
import com.codeL.gray.core.strategy.PolicyGroup;
import com.codeL.gray.core.util.HttpUtils;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.codeL.gray.common.http.Response.CODE_SUCCESS;
import static com.codeL.gray.core.GrayType.U;
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
public class HttpURLWatchDog extends AbstractWatchDog {

    private final AtomicLong index = new AtomicLong(1);

    @Setter
    private String url;

    @Setter
    private Fetch fetch;

    @Override
    protected void customInit() {
        if (url == null || "".equals(url)) {
            url = System.getProperty("gray.http.remote");
        }
        fetch = new Fetch(url);
    }

    @Override
    protected Runnable doWatch() {
        return () -> {
            customInit();
            if (url == null || "".equals(url)) {
                return;
            }
            GrayStatus status;
            PolicyGroup policyGroup = null;
            Reporter reporter = new Reporter(url);
            try {
                status = new HttpURLStatusCheck(url).checkStatus();
                switch (status) {
                    case Close:
                        bindGlobalGrayContext(DEFAULT_GRAY_CONTEXT);
                        reporter.reportNodeStatus(false);
                        return;
                    default:
                }
                String policyGroupStr = fetch.fetch();
                if (policyGroupStr != null && !"".equals(policyGroupStr)) {
                    byte[] bytes = policyGroupStr.getBytes();
                    ByteInputStream inputStream = new ByteInputStream(bytes, bytes.length);
                    policyGroup = new DefaultMetaReader().readPolicyGroup(inputStream);
                }
            } catch (Exception e) {
                log.warn("something wrong happen.", e);
                bindGlobalGrayContext(DEFAULT_GRAY_CONTEXT);
                reporter.reportNodeStatus(false);
                return;
            }
            if (policyGroup == null) {
                log.error("error status. gray is open. but no meta");
                bindGlobalGrayContext(DEFAULT_GRAY_CONTEXT);
                reporter.reportNodeStatus(false);
                return;
            }
            if (status != new HttpURLStatusCheck(url).checkStatus()) {
                bindGlobalGrayContext(DEFAULT_GRAY_CONTEXT);
                reporter.reportNodeStatus(false);
                return;
            }
            reporter.reportNodeStatus(true);
            bindGlobalGrayContext(new DefaultGrayContext(policyGroup, U, status, fetch.changed ? index.getAndIncrement() : index.get()));
        };
    }

    /**
     * reporter the status
     */
    private static class Reporter {

        static final String API = "/gray/setStatus";

        private final String url;

        Reporter(String url) {
            this.url = url;
        }

        boolean reportNodeStatus(Boolean status) {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("status", String.valueOf(status));
            try {
                String result = HttpUtils.postForm(url + API, parameters, "utf-8");
                if (result == null || "".equals(result)) {
                    return false;
                }
            } catch (IOException e) {
                log.error("reporter status false", e);
            }
            return true;
        }
    }

    /**
     * fetch policy
     */
    private static class Fetch {

        private static final String API = "/gray/getPolicy";

        private final String url;

        private CompareString lastCompare = null;

        private boolean changed = false;

        public Fetch(String url) {
            this.url = url;
        }

        String fetch() {
            try {
                String result = HttpUtils.get(url + API, "utf-8");
                if (result == null) {
                    return null;
                }
                if (lastCompare == null) {
                    lastCompare = new CompareString(new String(DigestUtils.md5(result)), result);
                } else {
                    CompareString oldC = lastCompare;
                    CompareString newC = new CompareString(new String(DigestUtils.md5(result)), result);
                    if (oldC.getMD5().equals(newC.getMD5())) {
                        changed = false;
                    } else {
                        changed = true;
                    }
                }
                Response response = JSONObject.parseObject(result, Response.class);
                if (response == null) {
                    return null;
                }
                Response.HeadBean head = response.getHead();
                if (head == null) {
                    return null;
                }
                if (!CODE_SUCCESS.equals(head.getCode())) {
                    log.error("fail http:{}", result);
                    return null;
                }
                return String.valueOf(response.getBody());
            } catch (IOException e) {
                log.error("reporter status false", e);
            }
            return null;
        }

        @Getter
        @Setter
        public class CompareString {
            String MD5;
            String origin;

            public CompareString(String MD5, String orgin) {
                this.MD5 = MD5;
                this.origin = orgin;
            }
        }
    }

    public static void main(String[] args) {

        String policyGroupStr = "{\n" +
                "\t\"group\": {\n" +
                "\t\t\"first\": {\n" +
                "\t\t\t\"divtype\": \"uid\",\n" +
                "\t\t\t\"divdata\": [{\n" +
                "\t\t\t\t\"uidset\": [\"111111\", \"2222222\"],\n" +
                "\t\t\t\t\"upstream\": \"10.202.130.13830000\"\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"uidset\": [\"333333\", \"4444444\"],\n" +
                "\t\t\t\t\"upstream\": \"10.202.130.13830001\"\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"servertype\": \"dubbo\"\n" +
                "\t\t},\n" +
                "        \"second\": {\n" +
                "\t\t\t\"divtype\": \"uid\",\n" +
                "\t\t\t\"divdata\": [{\n" +
                "                \"originName\":\"hhhhhh\",\n" +
                "\t\t\t\t\"uidset\": [\"111111\", \"2222222\"],\n" +
                "\t\t\t\t\"goalName\": \"hehehehe\"\n" +
                "\t\t\t}, {\n" +
                "                \"originName\":\"dddddd\",\n" +
                "\t\t\t\t\"uidset\": [\"333333\", \"4444444\"],\n" +
                "\t\t\t\t\"goalName\": \"dadadada\"\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"servertype\": \"activemq:P\"\n" +
                "\t\t},\n" +
                "         \"third\": {\n" +
                "\t\t\t\"divtype\": \"uid\",\n" +
                "\t\t\t\"divdata\": [{\n" +
                "                \"originName\":\"hhhhhh\",\n" +
                "\t\t\t\t\"goalName\": \"hehehehe\"\n" +
                "\t\t\t}, {\n" +
                "                \"originName\":\"dddddd\",\n" +
                "\t\t\t\t\"goalName\": \"dadadada\"\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"servertype\": \"activemq:C\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        ByteInputStream inputStream = new ByteInputStream(policyGroupStr.getBytes(), policyGroupStr.getBytes().length);
        PolicyGroup policyGroup = new DefaultMetaReader().readPolicyGroup(inputStream);
        System.out.println(123);
    }
}
