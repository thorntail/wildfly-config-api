package org.wildfly.apigen.model;

import org.jboss.dmr.ModelNode;
import org.junit.Assert;
import org.junit.Test;
import org.wildfly.apigen.AbstractTestCase;
import org.wildfly.apigen.operations.ReadDescription;


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
        Assert.assertEquals(57, description.getAttributes().size());
    }
}
