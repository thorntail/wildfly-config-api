package org.wildfly.swarm.config.generator.generator;

import org.jboss.as.controller.client.ModelControllerClient;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class ClientFactory {

    public static ModelControllerClient createClient(Config config) throws Exception {
        return ModelControllerClient.Factory.create(config.getHost(), config.getPort(),
                new AuthCallback(config.getUser(), config.getPass()));
    }
}
