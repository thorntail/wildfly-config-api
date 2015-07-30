package org.wildfly.apigen.test.gen;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.apigen.test.AbstractTestCase;
import org.wildfly.apigen.generator.MetaDataIterator;
import org.wildfly.apigen.generator.ResourceMetaData;
import org.wildfly.apigen.generator.SourceFactory;
import org.wildfly.apigen.model.AddressTemplate;

import java.util.Iterator;

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

        JavaClassSource javaClass = SourceFactory.createResourceAsClass(subresource);

        System.out.println(javaClass);
    }

    @Test
    public void testChildResourceTraversal() {
        System.out.println(metaData.getDescription().getChildrenNames());

        Iterator<ResourceMetaData> iterator = new MetaDataIterator(metaData).createInstance();

        // verify that the order reflects the nesting structure
        Integer previous = Integer.MAX_VALUE;
        while(iterator.hasNext())
        {
            ResourceMetaData child = iterator.next();
            System.out.println(child.getAddress());
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
                att -> System.out.println(att.getName())
        );

        JavaClassSource javaClass = SourceFactory.createResourceAsClass(metaData);
        System.out.println(javaClass.toString());

    }

}
