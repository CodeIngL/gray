package com.codeL.gray.core.strategy.extract;

import com.codeL.gray.common.convert.TypeHolder;
import com.codeL.gray.core.strategy.select.Select;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/27 16:59
 */
public interface PExtract extends Extract {

    ConcurrentHashMap<TypeHolder, Select> mappingSelector = new ConcurrentHashMap<>();

}
