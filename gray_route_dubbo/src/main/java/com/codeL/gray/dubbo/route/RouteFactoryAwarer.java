package com.codeL.gray.dubbo.route;

import com.codeL.gray.dubbo.ExtensionLoaderAware;
import com.alibaba.dubbo.rpc.cluster.RouterFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Slf4j
public class RouteFactoryAwarer extends ExtensionLoaderAware<RouterFactory> {

    public RouteFactoryAwarer() {
        super(RouterFactory.class);
    }

    @Override
    protected WrapperResult<RouterFactory> doWrapper(List<RouterFactory> factories) {
        boolean allWrapped = true;
        List<RouterFactory> wrappedFactories = new ArrayList<>();
        for (RouterFactory factory : factories) {
            RouterFactory wrappedFactory = factory;
            if (!factory.getClass().isAssignableFrom(GrayRouteFactory.class)) {
                wrappedFactory = new GrayRouteFactory(factory);
                allWrapped = false;
            }
            wrappedFactories.add(wrappedFactory);
        }
        return new WrapperResult<>(wrappedFactories, allWrapped);
    }
}
