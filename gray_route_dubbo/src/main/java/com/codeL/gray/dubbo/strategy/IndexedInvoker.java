package com.codeL.gray.dubbo.strategy;

import com.alibaba.dubbo.rpc.Invoker;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Getter
@Setter
public class IndexedInvoker<T> {

    private final Invoker<T> invoker;

    private final int index;

    private boolean choiced = false;

    public IndexedInvoker(Invoker<T> invoker, int index, boolean choiced) {
        this.invoker = invoker;
        this.index = index;
        this.choiced = choiced;
    }

}
