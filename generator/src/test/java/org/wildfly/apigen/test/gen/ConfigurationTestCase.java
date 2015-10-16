package org.wildfly.apigen.test.gen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.swarm.config.generator.generator.Config;
import org.wildfly.swarm.config.generator.generator.GeneratorTarget;

import java.util.List;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class ConfigurationTestCase {

    private Config config;

    @Before
    public void fixture() throws Exception {
        String configDirectory = System.getProperty("APIGEN_CFG_DIR");
        Assert.assertNotNull(configDirectory, "No configuration given");
        config = Config.fromJson(configDirectory + "/example-config.json");
    }

    @Test
    public void testXmlConfigParser() {
        List<GeneratorTarget> references = config.getGeneratorTargets();

        Assert.assertFalse("Config should not be empty", references.isEmpty());
        Assert.assertEquals(1, references.size());

        GeneratorTarget resourceRef = references.get(0);
        Assert.assertEquals("/subsystem=datasources/data-source=*", resourceRef.getSourceAddress().getTemplate());
        Assert.assertEquals("org.wildfly.swarm.config.datasources", resourceRef.getTargetPackage());
    }
}
