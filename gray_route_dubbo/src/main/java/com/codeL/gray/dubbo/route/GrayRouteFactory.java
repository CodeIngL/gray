package com.codeL.gray.dubbo.route;

import com.codeL.gray.common.convert.TypeConverterDelegate;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.cluster.Router;
import com.alibaba.dubbo.rpc.cluster.RouterFactory;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.COM</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class GrayRouteFactory implements RouterFactory {

    private final RouterFactory routerFactory;

    private final TypeConverterDelegate delegate;

    public GrayRouteFactory(RouterFactory routerFactory, TypeConverterDelegate delegate) {
        this.routerFactory = routerFactory;
        this.delegate = delegate;
    }

    @Override
    public Router getRouter(URL url) {
        return new GrayRoute(url, routerFactory.getRouter(url), delegate);
    }
}
