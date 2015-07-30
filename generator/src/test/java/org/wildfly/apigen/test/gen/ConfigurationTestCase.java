package org.wildfly.apigen.test.gen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.apigen.generator.Config;
import org.wildfly.apigen.generator.GeneratorTarget;

import java.util.List;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class ConfigurationTestCase {

    private Config config;

    @Before
    public void fixture() throws Exception {
        String testResourceDir = System.getProperty("testResourceDir");
        Assert.assertNotNull(testResourceDir, "No configuration given");
        config = Config.fromJson(testResourceDir + "/example-config.json");
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
