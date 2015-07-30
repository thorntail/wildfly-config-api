package org.wildfly.apigen.generator;

import org.jboss.dmr.ModelType;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;
import org.jboss.forge.roaster.model.source.PropertySource;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import org.wildfly.apigen.invocation.Types;
import org.wildfly.apigen.model.ResourceDescription;

import java.util.Optional;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DESCRIPTION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.TYPE;

/**
 * Encapsulates the templates for generating source files from resource descriptions
 *
 * @author Heiko Braun
 * @since 30/07/15
 */
public class SourceFactory {

    public static JavaClassSource createResourceAsClass(ModelSegment ref, ResourceDescription desc) {

        String className = Types.javaClassName(ref.getSourceAddress().getResourceType());

        // base class
        JavaClassSource javaClass =  Roaster.parse(
                JavaClassSource.class,
                "public class " + className + " {}"
        );
        javaClass.setPackage(ref.getTargetPackage());

        // javadoc
        JavaDocSource javaDoc = javaClass.getJavaDoc();
        javaDoc.setText(desc.getText());

        // imports
        javaClass.addImport(Address.class);
        javaClass.addImport(Binding.class);


        AnnotationSource<JavaClassSource> addressMeta = javaClass.addAnnotation();
        addressMeta.setName("Address");
        addressMeta.setStringValue(ref.getSourceAddress().getTemplate());

        desc.getAttributes().forEach(
                att -> {
                    ModelType modelType = ModelType.valueOf(att.getValue().get(TYPE).asString());
                    Optional<String> resolvedType = Types.resolveJavaTypeName(modelType);

                    if (resolvedType.isPresent()) {

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

        return javaClass;
    }
}
