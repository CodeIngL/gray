package com.codeL.gray.core.check.remote;

import com.codeL.gray.common.http.Response;
import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.check.StatusCheck;
import com.codeL.gray.core.util.HttpUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.codeL.gray.common.http.Response.CODE_SUCCESS;
import static com.codeL.gray.core.GrayStatus.Close;
import static com.codeL.gray.core.GrayStatus.Open;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Slf4j
public class HttpURLStatusCheck implements StatusCheck {

    private static final String API = "/gray/getMode";

    @Setter
    private String urlCheck;

    public HttpURLStatusCheck(String urlCheck) {
        this.urlCheck = urlCheck;
    }

    public HttpURLStatusCheck() {
    }

    @Override
    public GrayStatus checkStatus() {
        try {
            String result = HttpUtils.get(urlCheck + API, "utf-8");
            if (result == null || "".equals(result)) {
                return Close;
            }
            Response response = JSONObject.parseObject(result, Response.class);
            if (response == null) {
                return Close;
            }
            Response.HeadBean head = response.getHead();
            if (head == null) {
                return Close;
            }
            if (!CODE_SUCCESS.equals(head.getCode())) {
                log.error("fail http:{}", result);
                return Close;
            }
            if ("open".equals(response.getBody())) {
                return Open;
            }
            return Close;
        } catch (Exception e) {
            log.error("reporter status false", e);
        }
        return Close;
    }
}
