package org.wildfly.swarm.config;

import org.jboss.dmr.ModelNode;
import org.junit.Test;
import org.wildfly.swarm.config.infinispan.CacheContainer;
import org.wildfly.swarm.config.infinispan.cache_container.DistributedCache;
import org.wildfly.swarm.config.infinispan.cache_container.FileStore;
import org.wildfly.swarm.config.infinispan.cache_container.JGroupsTransport;
import org.wildfly.swarm.config.infinispan.cache_container.LockingComponent;
import org.wildfly.swarm.config.infinispan.cache_container.TransactionComponent;
import org.wildfly.swarm.config.logging.RootLogger;
import org.wildfly.swarm.config.runtime.invocation.Marshaller;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Heiko Braun
 * @since 28/10/15
 */
public class MarshallingTestCase {

    @Test
    public void testCacheContainer() throws Exception {
        CacheContainer ejbCache = new CacheContainer("ejb")
                .defaultCache("dist")
                .aliases(Arrays.asList(new String[]{"sfsb"}))
                .jgroupsTransport(new JGroupsTransport().lockTimeout(60000L))
                .distributedCache(new DistributedCache("dist")
                        .mode("ASYNC")
                        .l1Lifespan(0l)
                        .owners(2)
                        .lockingComponent(new LockingComponent().isolation("REPEATABLE_READ"))
                        .transactionComponent(new TransactionComponent().mode("BATCH"))
                        .fileStore(new FileStore()));

        LinkedList<ModelNode> payload = Marshaller.marshal(ejbCache);

        for (ModelNode modelNode : payload) {
            System.out.println(modelNode);
        }

    }

    @Test
    public void testRootLogger() throws Exception {
        Logging<Logging> logging = new Logging<>();
        logging.rootLogger(new RootLogger().level("DEBUG"));
        logging.consoleHandler( "console", handler -> {
            handler.enabled(true);
            handler.level("DEBUG");
        });

        LinkedList<ModelNode> payload = Marshaller.marshal(logging);

        for (ModelNode modelNode : payload) {
            System.out.println(modelNode);
        }

    }
}
