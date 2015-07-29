package org.wildfly.apigen.gen;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.util.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.apigen.AbstractTestCase;
import org.wildfly.apigen.invocation.Types;
import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.operations.DefaultStatementContext;
import org.wildfly.apigen.operations.ReadDescription;

import java.util.Optional;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

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
    public void testSimpleTypeAttributes() {

        Assert.notNull(description, "Invalid fixture");

        JavaClassSource javaClass =  Roaster.parse(
                JavaClassSource.class,
                "public class "+address.getResourceType().replace("-", "_")+" {}"
        );

        description.getAttributes().forEach(
                att -> {
                    ModelType modelType = ModelType.valueOf(att.getValue().get(TYPE).asString());
                    Optional<String> resolvedType = Types.resolveJavaTypeName(modelType);

                    if(resolvedType.isPresent()) {
                        javaClass.addProperty(
                                resolvedType.get(),
                                att.getName().replace("-", "_")
                        );
                    }
                }
        );

        System.out.println(javaClass);
    }
}
