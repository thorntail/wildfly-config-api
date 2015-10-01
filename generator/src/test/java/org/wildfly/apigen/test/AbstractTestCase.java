package org.wildfly.apigen.test;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.wildfly.apigen.generator.Config;
import org.wildfly.apigen.generator.ResourceMetaData;
import org.wildfly.apigen.generator.ClientFactory;
import org.wildfly.config.model.AddressTemplate;
import org.wildfly.apigen.model.DefaultStatementContext;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.operations.ReadDescription;

import java.io.IOException;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class AbstractTestCase  {

    protected static ModelControllerClient client;
    protected static Config config;

    @BeforeClass
    public static void setup() throws Exception {

        String configDirectory = System.getProperty("APIGEN_CFG_DIR");
        Assert.assertNotNull(configDirectory, "No configuration given");
        config = Config.fromJson(configDirectory + "/example-config.json");
        client = ClientFactory.createClient(config);
    }

    @AfterClass
    public static void teardown() throws Exception {
        client.close();
    }

    protected ResourceMetaData getResourceMetaData(AddressTemplate address) {
        try {
            ReadDescription op = new ReadDescription(address);
            ModelNode response = client.execute(op.resolve(new DefaultStatementContext()));
            return new ResourceMetaData(address, ResourceDescription.from(response));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
