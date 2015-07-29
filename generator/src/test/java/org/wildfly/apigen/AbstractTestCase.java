package org.wildfly.apigen;

import org.jboss.as.controller.client.ModelControllerClient;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.wildfly.apigen.generator.Config;
import org.wildfly.apigen.invocation.AuthCallback;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class AbstractTestCase  {

    protected static ModelControllerClient client;
    private static Config config;

    @BeforeClass
    public static void setup() throws Exception {

        String testResourceDir = System.getProperty("testResourceDir");
        Assert.assertNotNull(testResourceDir, "No configuration given");
        config = Config.fromJson(testResourceDir + "/example-config.json");

        client = ModelControllerClient.Factory.create(
                config.getHost(), config.getPort(),
                new AuthCallback(new String[] {
                        config.getUser(), config.getPass()
                })
        );
    }

    @AfterClass
    public static void teardown() throws Exception {
        client.close();
    }
}
