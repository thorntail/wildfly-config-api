package org.wildfly.apigen.gen;

import org.jboss.dmr.ModelNode;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.util.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.apigen.AbstractTestCase;
import org.wildfly.apigen.generator.ResourceRef;
import org.wildfly.apigen.generator.SourceFactory;
import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.model.DefaultStatementContext;
import org.wildfly.apigen.operations.ReadDescription;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class GeneratorTestCase extends AbstractTestCase {

    private AddressTemplate address;
    private ResourceDescription description;

    @Before
    public void fixture() throws Exception {
        address = AddressTemplate.of("/subsystem=datasources/data-source=*");
        ReadDescription op = new ReadDescription(address);

        ModelNode response = client.execute(op.resolve(new DefaultStatementContext()));
        description = ResourceDescription.from(response);
    }

    @Test
    public void testSourceFactory() {

        Assert.notNull(description, "Invalid fixture");

        JavaClassSource javaClass = SourceFactory.createResourceAsClass(
                new ResourceRef(address, "foo.bar.test"),
                description
        );

        System.out.println(javaClass);
    }

    @Test
    public void testChildResourceTraversal() {

    }

}
