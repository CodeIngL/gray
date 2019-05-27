package com.codeL.gray.dubbo;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.utils.Holder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Slf4j
public abstract class ExtensionLoaderAware<T> {

    final Class<T> cls;

    public ExtensionLoaderAware(Class<T> cls) {
        this.cls = cls;
    }

    /**
     * 我们通过wrapper进行构造，anyway，扩展类总是存在于我们的应用访问中
     */
    @PostConstruct
    public void wrapper() {
        Set<String> names = ExtensionLoader.getExtensionLoader(cls).getSupportedExtensions();
        if (names == null || names.size() == 0) {
            return;
        }
        for (String name : names) {
            ExtensionLoader.getExtensionLoader(cls).getExtension(name);
        }
        ExtensionLoader<T> extensionLoader = ExtensionLoader.getExtensionLoader(cls);
        Object[] arrayNames = names.toArray();
        List<T> ts = new ArrayList<>();
        for (Object name : arrayNames) {
            ts.add(extensionLoader.getLoadedExtension(String.valueOf(name)));
        }
        WrapperResult<T> result = doWrapper(ts);
        if (result.allWrapper) {
            return;
        }
        try {
            Field field = extensionLoader.getClass().getDeclaredField("cachedInstances");
            field.setAccessible(true);
            final ConcurrentMap<String, Holder<Object>> hodlerMap = (ConcurrentMap<String, Holder<Object>>) field.get(extensionLoader);
            for (int i = 0; i < arrayNames.length; i++) {
                String name = String.valueOf(arrayNames[i]);
                Holder<Object> holder = new Holder<>();
                holder.set(result.ts.get(i));
                hodlerMap.put(name, holder);
            }
        } catch (Exception e) {
            log.error("can't wrapped cls:{}.", cls.getSimpleName(), e);
        }
    }

    /**
     * for subclass to implements,wrapper
     * @param ts
     * @return
     */
    protected abstract WrapperResult<T> doWrapper(List<T> ts);

    @Data
    @AllArgsConstructor
    protected static class WrapperResult<T> {
        List<T> ts;
        boolean allWrapper = true;
    }
}
