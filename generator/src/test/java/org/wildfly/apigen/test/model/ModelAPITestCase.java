package org.wildfly.apigen.test.model;

import org.jboss.dmr.ModelNode;
import org.junit.Assert;
import org.junit.Test;
import org.wildfly.apigen.test.AbstractTestCase;
import org.wildfly.swarm.config.runtime.model.AddressTemplate;
import org.wildfly.swarm.config.generator.model.DefaultStatementContext;
import org.wildfly.swarm.config.generator.model.ResourceDescription;
import org.wildfly.swarm.config.generator.operations.ReadDescription;


/**
 * Verifies the Model API helper classes and it usage
 *
 * @author Heiko Braun
 * @since 29/07/15
 */
public class ModelAPITestCase extends AbstractTestCase {

    @Test
    public void testResourceDescriptionParsing() throws Exception {
        AddressTemplate address = AddressTemplate.of("/subsystem=datasources/data-source=*");
        ReadDescription op = new ReadDescription(address);

        ModelNode response = client.execute(op.resolve(new DefaultStatementContext()));
        ResourceDescription description = ResourceDescription.from(response);

        Assert.assertNotNull(description.getText());
        Assert.assertEquals(58, description.getAttributes().size());
    }
}
