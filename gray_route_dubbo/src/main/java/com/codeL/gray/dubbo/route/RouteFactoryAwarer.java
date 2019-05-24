package com.codeL.gray.dubbo.route;

import com.codeL.gray.common.convert.TypeConverterDelegate;
import com.codeL.gray.dubbo.ExtensionLoaderAware;
import com.alibaba.dubbo.rpc.cluster.RouterFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
@Slf4j
public class RouteFactoryAwarer extends ExtensionLoaderAware<RouterFactory> {

    public RouteFactoryAwarer() {
        super(RouterFactory.class);
    }

    @Setter
    private TypeConverterDelegate delegate = null;

    @Override
    protected WrapperResult<RouterFactory> doWrapper(List<RouterFactory> routerFactories) {
        boolean allWrapped = true;
        List<RouterFactory> wrappedRouteFactories = new ArrayList<>();
        for (RouterFactory routerFactory : routerFactories) {
            RouterFactory wrappedRouteFactory = routerFactory;
            if (!routerFactory.getClass().isAssignableFrom(GrayRouteFactory.class)) {
                wrappedRouteFactory = new GrayRouteFactory(routerFactory, delegate == null ? new TypeConverterDelegate() : delegate);
                allWrapped = false;
            }
            wrappedRouteFactories.add(wrappedRouteFactory);
        }
        return new WrapperResult<>(wrappedRouteFactories, allWrapped);
    }
}
