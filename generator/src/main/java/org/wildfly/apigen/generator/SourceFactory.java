package org.wildfly.apigen.generator;

import com.google.common.base.CaseFormat;
import org.jboss.dmr.ModelType;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;
import org.jboss.forge.roaster.model.source.PropertySource;
import org.jboss.logmanager.Level;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import org.wildfly.apigen.invocation.Subresource;
import org.wildfly.apigen.invocation.Types;
import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.ResourceDescription;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DESCRIPTION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.TYPE;

/**
 * Encapsulates the templates for generating source files from resource descriptions
 *
 * @author Heiko Braun
 * @since 30/07/15
 */
public class SourceFactory {

    private static final Logger log = Logger.getLogger(SourceFactory.class.getName());

    /**
     * Base template for a resource representation.
     * Covers the resource attributes
     *
     * @param metaData
     * @return
     */
    public static JavaClassSource createResourceAsClass(ResourceMetaData metaData) {

        String className = Types.javaClassName(metaData.getAddress().getResourceType());

        // base class
        JavaClassSource javaClass =  Roaster.parse(
                JavaClassSource.class,
                "public class " + className + " {}"
        );

        javaClass.setPackage(metaData.get(ResourceMetaData.PKG));

        // javadoc
        JavaDocSource javaDoc = javaClass.getJavaDoc();
        ResourceDescription desc = metaData.getDescription();
        javaDoc.setText(desc.getText());

        // imports
        javaClass.addImport(Address.class);
        javaClass.addImport(Binding.class);


        AnnotationSource<JavaClassSource> addressMeta = javaClass.addAnnotation();
        addressMeta.setName("Address");
        addressMeta.setStringValue(metaData.getAddress().getTemplate());

        desc.getAttributes().forEach(
                att -> {
                    ModelType modelType = ModelType.valueOf(att.getValue().get(TYPE).asString());
                    Optional<String> resolvedType = Types.resolveJavaTypeName(modelType);

                    if (resolvedType.isPresent()) {

                        // attributes

                        try {
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
                        catch (Exception e)
                        {
                            log.log(Level.ERROR, "Failed to process " + metaData.getAddress() + ", attribute " + att.getName(), e);
                        }
                    }
                }
        );

        return javaClass;
    }

    /**
     * Decorates a base resource representation with accessors to it's child resources
     *
     * @param scope
     * @param resourceMetaData
     * @param javaClass
     */
    public static void createChildAccessors(GeneratorScope scope, ResourceMetaData resourceMetaData, JavaClassSource javaClass) {

        ResourceDescription desc = resourceMetaData.getDescription();
        Set<String> childrenNames = desc.getChildrenNames();
        if(!childrenNames.isEmpty())
        {
            javaClass.addImport("java.util.List");
            javaClass.addImport(Subresource.class);

            StringBuffer ctor = new StringBuffer();

            for (String childName : childrenNames) {
                AddressTemplate childAddress = resourceMetaData.getAddress().append(childName + "=*");
                JavaClassSource childClass = scope.getGenerated(childAddress);
                javaClass.addImport(childClass);

                String propName = CaseFormat.UPPER_CAMEL.to(
                        CaseFormat.LOWER_CAMEL,
                        Keywords.escape(childClass.getName())
                ) + "s";
                String propType = "java.util.List<" + childClass.getName() + ">";

                PropertySource<JavaClassSource> prop = javaClass.addProperty(
                        propType,
                        propName    // TODO name mangling
                );
                AnnotationSource<JavaClassSource> subresourceMeta = prop.getAccessor().addAnnotation();
                subresourceMeta.setName("Subresource");

                ctor.append("this.").append(propName).append(" = new ").append("java.util.ArrayList<>();\n");
            }

            // initialize the collections
            javaClass.addMethod()
                    .setConstructor(true)
                    .setPublic()
                    .setBody(ctor.toString());

        }
    }
}
