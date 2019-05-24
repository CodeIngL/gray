package com.codeL.gray.dubbo.strategy;

import com.alibaba.dubbo.rpc.Invoker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class CompositeIndexedInvoker {

    private final List<IndexedInvoker> unMatchedConfigInvoker = new ArrayList<>();

    private final List<IndexedInvoker> matchedConfigInvoker = new ArrayList<>();

    public void addUnchoiced(IndexedInvoker indexedInvoker) {
        if (indexedInvoker == null) {
            return;
        }
        unMatchedConfigInvoker.add(indexedInvoker);
    }

    public List<IndexedInvoker> getAllUnchoiced() {
        return Collections.unmodifiableList(unMatchedConfigInvoker);
    }

    public void addChoiced(IndexedInvoker indexedInvoker) {
        if (indexedInvoker == null) {
            return;
        }
        matchedConfigInvoker.add(indexedInvoker);

    }

    public List<IndexedInvoker> getAllChoiced() {
        return Collections.unmodifiableList(matchedConfigInvoker);
    }
}
