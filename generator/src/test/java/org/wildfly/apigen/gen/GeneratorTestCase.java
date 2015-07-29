package org.wildfly.apigen.gen;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;
import org.jboss.forge.roaster.model.source.PropertySource;
import org.jboss.forge.roaster.model.util.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.apigen.AbstractTestCase;
import org.wildfly.apigen.invocation.Binding;
import org.wildfly.apigen.invocation.Types;
import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.model.DefaultStatementContext;
import org.wildfly.apigen.operations.ReadDescription;

import java.util.Optional;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DESCRIPTION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.TYPE;

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

        String className = Types.javaClassName(address.getResourceType());

        // base class
        JavaClassSource javaClass =  Roaster.parse(
                JavaClassSource.class,
                "public class "+className+" {}"
        );

        // javadoc
        JavaDocSource javaDoc = javaClass.getJavaDoc();
        javaDoc.setText(description.getText());

        // imports
        javaClass.addImport(Binding.class);

        description.getAttributes().forEach(
                att -> {
                    ModelType modelType = ModelType.valueOf(att.getValue().get(TYPE).asString());
                    Optional<String> resolvedType = Types.resolveJavaTypeName(modelType);

                    if(resolvedType.isPresent()) {

                        // attributes

                        PropertySource<JavaClassSource> prop = javaClass.addProperty(
                                resolvedType.get(),
                                Types.javaAttributeName(att.getName())
                        );
                        String attributeDescription = att.getValue().get(DESCRIPTION).asString();
                        prop.getMutator().getJavaDoc().setText(attributeDescription);
                        prop.getAccessor().getJavaDoc().setText(attributeDescription);

                        AnnotationSource<JavaClassSource> bindingMeta = prop.getAccessor().addAnnotation();
                        bindingMeta.setName("Binding");
                        bindingMeta.setStringValue("detypedName", att.getName());
                    }
                }
        );

        System.out.println(javaClass);
    }
}
