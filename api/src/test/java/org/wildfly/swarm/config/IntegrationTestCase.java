package org.wildfly.swarm.config;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.wildfly.apigen.generator.ClientFactory;
import org.wildfly.apigen.generator.Config;
import org.wildfly.config.invocation.EntityAdapter;
import org.wildfly.swarm.config.datasources.DataSource;
import org.wildfly.swarm.config.datasources.data_source.ConnectionProperties;
import org.wildfly.swarm.config.logging.RootLogger;

import java.util.ArrayList;

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
        String configDirectory = System.getProperty("APIGEN_CFG_DIR");
        Assert.assertNotNull(configDirectory, "No configuration given");
        config = Config.fromJson(configDirectory + "/generator-config.json");
        client = ClientFactory.createClient(config);
    }

    @AfterClass
    public static void teardown() throws Exception {
        client.close();
    }

    /**
     * A regular resource, explicitly named
     *
     * @throws Exception
     */
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
        DataSource dataSource = entityAdapter.fromDMR("ExampleDS", payload);
        Assert.assertNotNull(dataSource);
        Assert.assertEquals("java:jboss/datasources/ExampleDS", dataSource.jndiName());
    }

    /**
     * A singleton resource, implicitly named
     *
     * @throws Exception
     */
    @Test
    public void testRootLoogerUnmarshalling() throws Exception {
        ModelNode op = new ModelNode();
        op.get(OP).set(READ_RESOURCE_OPERATION);
        op.get(ADDRESS).set("/subsystem=logging/root-logger=ROOT");
        op.get(RECURSIVE).set(true);

        ModelNode response = client.execute(op);

        Assert.assertEquals("success", response.get("outcome").asString());
        ModelNode payload = response.get(RESULT);

        EntityAdapter<RootLogger> entityAdapter = new EntityAdapter<>(RootLogger.class);
        RootLogger rootLogger = entityAdapter.fromDMR("ROOT", payload);
        Assert.assertNotNull(rootLogger);
        Assert.assertEquals("ROOT", rootLogger.getKey());
        Assert.assertEquals("INFO", rootLogger.level());

    }

    /**
     * A singleton resource, implicitly named
     *
     * @throws Exception
     */
    @Test
    public void testRootLoogerMarshalling() throws Exception {


        RootLogger root = new RootLogger();
        root.level("DEBUG");

        EntityAdapter<RootLogger> entityAdapter = new EntityAdapter<>(RootLogger.class);

        ModelNode addOp = entityAdapter.fromEntity(root);
        addOp.get(OP).set(ADD);
        addOp.get(ADDRESS).add("subsystem", "logging");
        addOp.get(ADDRESS).add("root-logger", "root");

        Assert.assertEquals("DEBUG", addOp.get("level").asString());
    }

    @Test
    public void testDatasourceMarshalling() throws Exception {
        DataSource dataSource = new DataSource("TestDS");
        dataSource.jndiName("java:/foo/bar/DS");
        dataSource.userName("john.doe");
        dataSource.password("password");
        dataSource.connectionUrl("jdbc:h2:mem:swarm-test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.driverName("h2");


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

    @Test
    public void testDatasourceConnectionPropertiesMarshalling() throws Exception {
        DataSource dataSource = new DataSource("TestDS");
        dataSource.jndiName("java:/foo/bar/DS");
        dataSource.userName("john.doe");
        dataSource.password("password");
        dataSource.connectionUrl("jdbc:h2:mem:swarm-test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.driverName("h2");

        ConnectionProperties connectionProperties = new ConnectionProperties("TestDS");
        connectionProperties.value("jdbc:h2:mem:swarm-test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        ArrayList<ConnectionProperties> list = new ArrayList<>();
        list.add(connectionProperties);
        dataSource.connectionProperties(list);


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
