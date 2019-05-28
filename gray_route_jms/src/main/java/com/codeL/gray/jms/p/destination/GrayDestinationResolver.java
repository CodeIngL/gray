package com.codeL.gray.jms.p.destination;

import com.codeL.gray.jms.p.strategy.extract.Extractor;
import com.codeL.gray.core.GrayStatus;
import com.codeL.gray.core.context.GrayContext;
import org.springframework.jms.support.destination.DestinationResolver;

import javax.jms.*;

import static com.codeL.gray.core.context.GrayContextBinder.getGlobalGrayContext;

/**
 * <p>Description: </p>
 * <p>write with codeL</p>
 * <p>contact <code>codeLHJ@163.com</code></p>
 *
 * @author laihj
 * 2019/5/24 15:23
 */
public class GrayDestinationResolver implements DestinationResolver, CompositeDestinationResolver {

    public GrayDestinationResolver(DestinationResolver originResolver) {
        this.originResolver = originResolver;
    }

    private DestinationResolver originResolver;

    @Override
    public DestinationResolver getCompositeLoadBalance() {
        return null;
    }

    @Override
    public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain) throws JMSException {
        GrayContext context = getGlobalGrayContext();
        GrayStatus status = context.getGrayStatus();
        if (GrayStatus.Open == status) {
            String destination = new Extractor().extract(destinationName, pubSubDomain);
            return originResolver.resolveDestinationName(session, destination == null ? destinationName : destination, pubSubDomain);
        }
        return originResolver.resolveDestinationName(session, destinationName, pubSubDomain);
    }
}
