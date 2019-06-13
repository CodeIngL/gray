package com.codeL.gray.dubbo.route;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Router;

import java.util.List;

/**
 * <p>Description: </p>
 *
 * @author laihj
 * 2019/6/10
 */
public class StubRoute implements Router {


    private URL url;

    public StubRoute(URL url) {
        this.url = url;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        return invokers;
    }

    @Override
    public int compareTo(Router o) {
        return 0;
    }
}
