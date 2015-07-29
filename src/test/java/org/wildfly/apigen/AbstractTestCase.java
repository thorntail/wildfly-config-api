package org.wildfly.apigen;

import org.jboss.as.controller.client.ModelControllerClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class AbstractTestCase  {

    protected static ModelControllerClient client;

    @BeforeClass
    public static void setup() throws Exception {

        String[] args = {"admin", "passWord123"};
        client = ModelControllerClient.Factory.create("localhost", 9990, new AuthCallback(args));
    }

    @AfterClass
    public static void teardown() throws Exception {
        client.close();
    }
}
