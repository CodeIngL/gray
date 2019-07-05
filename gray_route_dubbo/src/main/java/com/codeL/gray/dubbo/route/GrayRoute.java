package com.codeL.gray.dubbo.route;

import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.context.GrayContext;
import com.codeL.gray.dubbo.strategy.CompositeIndexedInvoker;
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

    public GrayRoute(URL url, Router router) {
        this.url = url;
        this.router = router;
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
            if (router == null) {
                return invokers;
            }
            return router.route(invokers, url, invocation);
        }
        CompositeIndexedInvoker<T> invoker = new Extractor().extract(invokers, url, invocation);
        if (invoker == null) {
            if (router == null) {
                return invokers;
            }
            return router.route(invokers, url, invocation);
        }
        List<Invoker<T>> changedInvokers = new ArrayList<>();

        //step 1. find some invoker matched policy with info
        List<IndexedInvoker> chosen = invoker.getAllChosen();
        if (chosen != null && chosen.size() > 0) {
            if (chosen.size() == 1) {
                changedInvokers.add(chosen.get(0).getInvoker());
                return changedInvokers;
            }
            for (int i = 0; i < chosen.size(); i++) {
                changedInvokers.add(chosen.get(i).getInvoker());
            }
            if (router == null) {
                if (changedInvokers.size() > 0) {
                    return changedInvokers;
                }
                return invokers;
            }
            return router.route(changedInvokers, url, invocation);
        }

        //step 2. find some invoker matched policy not with info
        List<IndexedInvoker> unChosen = invoker.getAllUnChosen();
        for (int i = 0; i < invokers.size(); i++) {
            boolean has = false;
            for (int j = 0; j < unChosen.size(); j++) {
                if (invokers.get(i).equals(unChosen.get(j))) {
                    has = true;
                    break;
                }
            }
            if (!has) {
                changedInvokers.add(invokers.get(i));
            }
        }
        if (router == null) {
            if (changedInvokers.size() > 0) {
                return changedInvokers;
            }
            return invokers;
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
