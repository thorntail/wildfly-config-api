package org.wildfly.apigen.gen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.apigen.generator.Config;
import org.wildfly.apigen.generator.ResourceRef;

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
        List<ResourceRef> references = config.getReferences();

        Assert.assertFalse("Config should not be empty", references.isEmpty());

        ResourceRef resourceRef = references.get(0);
        Assert.assertEquals("/subsystem=datasources/data-source=*", resourceRef.getSourceAddress());
        Assert.assertEquals("org.wildfly.swarm.config.datasources", resourceRef.getTargetPackage());
    }
}
