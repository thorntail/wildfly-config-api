package org.wildfly.apigen.gen;

import org.jboss.dmr.ModelNode;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.wildfly.apigen.AbstractTestCase;
import org.wildfly.apigen.generator.MetaDataIterator;
import org.wildfly.apigen.generator.ResourceMetaData;
import org.wildfly.apigen.generator.ModelSegment;
import org.wildfly.apigen.generator.SourceFactory;
import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.DefaultStatementContext;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.operations.ReadDescription;

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
        ReadDescription op = new ReadDescription(address);

        ModelNode response = client.execute(op.resolve(new DefaultStatementContext()));
        ResourceDescription description = ResourceDescription.from(response);

        this.metaData = new ResourceMetaData(address, description);
    }

    @Test
    public void testSourceFactory() {

        Assert.assertNotNull("Invalid fixture", metaData);

        JavaClassSource javaClass = SourceFactory.createResourceAsClass(
                new ModelSegment(metaData.getAddress().append("data-source=*"), "foo.bar.test"),
                metaData.getDescription().getChildDescription("data-source")
        );

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

}
