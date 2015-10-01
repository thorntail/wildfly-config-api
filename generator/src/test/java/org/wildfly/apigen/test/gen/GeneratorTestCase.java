package org.wildfly.apigen.test.gen;

import org.jboss.dmr.ModelNode;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.apigen.generator.Config;
import org.wildfly.apigen.generator.Generator;
import org.wildfly.apigen.generator.GeneratorScope;
import org.wildfly.apigen.generator.MetaDataTraversal;
import org.wildfly.apigen.generator.ResourceMetaData;
import org.wildfly.apigen.generator.SourceFactory;
import org.wildfly.config.model.AddressTemplate;
import org.wildfly.apigen.model.DefaultStatementContext;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.operations.ReadDescription;
import org.wildfly.apigen.test.AbstractTestCase;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class GeneratorTestCase extends AbstractTestCase {

    private ResourceMetaData metaData;

    @Before
    public void fixture() throws Exception {
        AddressTemplate address = AddressTemplate.of("/subsystem=datasources");
        this.metaData = getResourceMetaData(address);
    }

    @Test
    public void testSourceFactory() {

        Assert.assertNotNull("Invalid fixture", metaData);

        AddressTemplate childAddress = metaData.getAddress().append("data-source=*");
        ResourceMetaData subresource = new ResourceMetaData(
                childAddress,
                metaData.getDescription().getChildDescription("data-source")
        );
        subresource.set(ResourceMetaData.PKG, "foo.bar.test");

        GeneratorScope scope = new GeneratorScope();
        JavaClassSource javaClass = SourceFactory.createResourceAsClass(scope, subresource);

    }

    @Test
    public void testChildResourceTraversal() {

        Iterator<ResourceMetaData> iterator = new MetaDataTraversal(metaData).createInstance();

        // verify that the order reflects the nesting structure
        Integer previous = Integer.MAX_VALUE;
        while(iterator.hasNext())
        {
            ResourceMetaData child = iterator.next();

            Assert.assertTrue(
                    "Wrong order of elements",
                    child.getAddress().tokenLength() <= previous
            );

            previous = child.getAddress().tokenLength();
        }

    }

    @Test
    public void testAttributeEdgeCase() {
        ResourceMetaData metaData = getResourceMetaData(
                AddressTemplate.of("/subsystem=logging/custom-handler=*")
        );
        metaData.set(ResourceMetaData.PKG, "foo.bar");

        metaData.getDescription().getAttributes().forEach(
                att -> {} //System.out.println(att.getName())
        );

        GeneratorScope scope = new GeneratorScope();
        JavaClassSource javaClass = SourceFactory.createResourceAsClass(scope, metaData);

    }

    /**
     * Single occurrence of singleton meta data per child type
     * @throws Exception
     */
    @Test
    public void testSimpleSingletons() throws Exception {

        String configDirectory = System.getProperty("APIGEN_CFG_DIR");

        ResourceMetaData root = getResourceMetaData(
                AddressTemplate.of("/subsystem=logging")
        );
        root.set(ResourceMetaData.PKG, "foo.bar.logging");

        Config config = Config.fromJson(configDirectory+"/logging-config.json");
        Generator generator = new Generator("./target/generated-test-sources", config);
        generator.processGeneratorTargets();
        generator.shutdown();

    }

    /**
     * Multiple different singleton resources per child type
     * @throws Exception
     */
    @Test
    public void testComplexSingletons() throws Exception {

        String configDirectory = System.getProperty("APIGEN_CFG_DIR");

        ResourceMetaData root = getResourceMetaData(
                AddressTemplate.of("/subsystem=ejb3")
        );
        root.set(ResourceMetaData.PKG, "foo.bar.ejb3");

        Config config = Config.fromJson(configDirectory+"/ejb3-config.json");
        Generator generator = new Generator("./target/generated-test-sources", config);
        generator.processGeneratorTargets();
        generator.shutdown();

    }

    /**
     * Test parsing of singleton type meta data
     *
     * @throws Exception
     */
    @Test
    public void testSingletonMetaData() throws Exception {

        AddressTemplate address = AddressTemplate.of("/subsystem=ejb3");
        ReadDescription rrd = new ReadDescription(address);

        ModelNode response = client.execute(rrd.resolve(new DefaultStatementContext()));

        ResourceDescription description = ResourceDescription.from(response);

        Set<String> childrenTypes = description.getChildrenTypes();
        Set<String> singletonChildrenTypes = description.getSingletonChildrenTypes();

        childrenTypes.forEach(s -> System.out.println(s));
        singletonChildrenTypes.forEach(s -> System.out.println(s));

        Assert.assertFalse(childrenTypes.contains("service"));
        Assert.assertEquals(4, singletonChildrenTypes.size());
        Assert.assertTrue(singletonChildrenTypes.contains("service=timer-service"));
        Assert.assertTrue(singletonChildrenTypes.contains("service=remote"));
        Assert.assertTrue(singletonChildrenTypes.contains("service=async"));
        Assert.assertTrue(singletonChildrenTypes.contains("service=iiop"));

    }

}
