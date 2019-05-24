package com.codeL.gray.core.meta;

import com.codeL.gray.core.GrayType;

import java.io.InputStream;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public interface TypeReader extends Reader {

    GrayType readType(InputStream inputStream);

}
