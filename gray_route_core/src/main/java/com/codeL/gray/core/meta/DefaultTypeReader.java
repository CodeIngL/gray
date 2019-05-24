package com.codeL.gray.core.meta;

import com.codeL.gray.core.GrayType;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

import static com.codeL.gray.core.GrayType.U;
import static com.codeL.gray.core.GrayType.toGrayType;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Slf4j
public class DefaultTypeReader implements TypeReader {
    @Override
    public GrayType readType(InputStream inputStream) {
        try {
            String value = JSONObject.parseObject(inputStream, String.class);
            return toGrayType(value);
        } catch (IOException e) {
            log.error("read type error", e);
        }
        return U;
    }
}
