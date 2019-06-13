package com.codeL.gray.dubbo.route;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.cluster.Router;
import com.alibaba.dubbo.rpc.cluster.RouterFactory;

/**
 * <p>Description: </p>
 *
 * @author laihj
 * 2019/6/10
 */
public class StubRouterFactory implements RouterFactory {
    @Override
    public Router getRouter(URL url) {
        return new StubRoute(url);
    }
}
