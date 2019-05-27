package com.codeL.gray.dubbo.route;

import com.codeL.gray.common.convert.TypeConverterDelegate;
import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.context.GrayContext;
import com.codeL.gray.dubbo.strategy.IndexedInvoker;
import com.codeL.gray.dubbo.strategy.extract.Extractor;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Router;

import java.util.ArrayList;
import java.util.List;

import static com.codeL.gray.core.context.GrayContextBinder.getGlobalGrayContext;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class GrayRoute implements Router {

    private final URL url;
    private final Router router;
    private final TypeConverterDelegate delegate;

    public GrayRoute(URL url, Router router, TypeConverterDelegate delegate) {
        this.url = url;
        this.router = router;
        this.delegate = delegate;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    public Router getRouter() {
        return router;
    }

    @Override
    public <T> List<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        GrayContext context = getGlobalGrayContext();
        GrayStatus status = context.getGrayStatus();
        if (!(GrayStatus.Open == status)) {
            return router.route(invokers, url, invocation);
        }
        IndexedInvoker<T> invoker = new Extractor(delegate).extract(invokers, url, invocation);
        if (invoker == null) {
            return router.route(invokers, url, invocation);
        }
        if (invoker.isChoiced()) {
            List<Invoker<T>> list = new ArrayList<>();
            list.add(invoker.getInvoker());
            return list;
        }
        List<Invoker<T>> changedInvokers = new ArrayList<>();
        for (int i = 0; i < invokers.size(); i++) {
            if (i == invoker.getIndex())
                continue;
            changedInvokers.add(invokers.get(i));
        }
        return router.route(changedInvokers, url, invocation);
    }

    @Override
    public int compareTo(Router o) {
        if (o.getClass() == GrayRoute.class) {
            return router.compareTo(((GrayRoute) o).getRouter());
        }
        return router.compareTo(o);
    }
}
