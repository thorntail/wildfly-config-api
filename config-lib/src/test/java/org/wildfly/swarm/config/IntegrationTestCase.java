package org.wildfly.swarm.config;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wildfly.apigen.generator.Config;
import org.wildfly.apigen.invocation.ClientFactory;
import org.wildfly.apigen.invocation.EntityAdapter;
import org.wildfly.swarm.config.datasources.DataSource;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * @author Heiko Braun
 * @since 31/07/15
 */
public class IntegrationTestCase {

    protected static ModelControllerClient client;
    private static Config config;

    @BeforeClass
    public static void setup() throws Exception {
        String testResourceDir = System.getProperty("testResourceDir");
        Assert.assertNotNull(testResourceDir, "No configuration given");
        config = Config.fromJson(testResourceDir + "/generator-config.json");
        client = ClientFactory.createClient(config);
    }

    @AfterClass
    public static void teardown() throws Exception {
        client.close();
    }

    @Test
    public void testDatasourceUnmarshalling() throws Exception {
        ModelNode op = new ModelNode();
        op.get(OP).set(READ_RESOURCE_OPERATION);
        op.get(ADDRESS).set("/subsystem=datasources/data-source=ExampleDS");
        op.get(RECURSIVE).set(true);

        ModelNode response = client.execute(op);

        Assert.assertEquals("success", response.get("outcome").asString());
        ModelNode payload = response.get(RESULT);

        EntityAdapter<DataSource> entityAdapter = new EntityAdapter<>(DataSource.class);
        DataSource dataSource = entityAdapter.fromDMR(payload);
        Assert.assertNotNull(dataSource);
        Assert.assertEquals("java:jboss/datasources/ExampleDS", dataSource.getJndiName());

    }

    @Test
    public void testDatasourceMarshalling() throws Exception {
        DataSource dataSource = new DataSource();
        dataSource.setJndiName("java:/foo/bar/DS");
        dataSource.setUserName("john.doe");
        dataSource.setPassword("password");
        dataSource.setConnectionUrl("jdbc:h2:mem:swarm-test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.setDriverName("h2");


        EntityAdapter<DataSource> entityAdapter = new EntityAdapter<>(DataSource.class);
        ModelNode addOp = entityAdapter.fromEntity(dataSource);

        Assert.assertNotNull(addOp);
        Assert.assertTrue(addOp.isDefined());

        String datasourceName = "swarm-integration-test";

        addOp.get(OP).set(ADD);
        addOp.get(ADDRESS).add("subsystem", "datasources");
        addOp.get(ADDRESS).add("data-source", datasourceName);

        ModelNode response = client.execute(addOp);
        Assert.assertEquals("success", response.get("outcome").asString());

        ModelNode removeOp = new ModelNode();
        removeOp.get(OP).set(REMOVE);
        removeOp.get(ADDRESS).add("subsystem", "datasources");
        removeOp.get(ADDRESS).add("data-source", datasourceName);

        ModelNode removalRsp = client.execute(removeOp);
        Assert.assertEquals("success", removalRsp.get("outcome").asString());
    }
}
