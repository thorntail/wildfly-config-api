package org.wildfly.apigen.generator;

import com.google.common.base.CaseFormat;
import org.jboss.dmr.ModelType;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.*;
import org.jboss.logmanager.Level;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import org.wildfly.apigen.invocation.Subresource;
import org.wildfly.apigen.invocation.Types;
import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.ResourceDescription;

import java.util.List;
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
                    //System.err.println("Attribute value: " + att.getValue());
                    ModelType modelType = ModelType.valueOf(att.getValue().get(TYPE).asString());
                    Optional<String> resolvedType = Types.resolveJavaTypeName(modelType);

                    if (resolvedType.isPresent()) {

                        // attributes
                        try {
                            final String name = Types.javaAttributeName(att.getName());
                            String attributeDescription = att.getValue().get(DESCRIPTION).asString();

                            javaClass.addField()
                                    .setName(name)
                                    .setType(resolvedType.get())
                                    .setPrivate();

                            final MethodSource<JavaClassSource> accessor = javaClass.addMethod();
                            accessor.getJavaDoc().setText(attributeDescription);
                            accessor.setPublic()
                                    .setName(name)
                                    .setReturnType(resolvedType.get())
                                    .setBody("return this." + name + ";");


                            final MethodSource<JavaClassSource> mutator = javaClass.addMethod();
                            mutator.getJavaDoc().setText(attributeDescription);
                            mutator.addParameter(resolvedType.get(), "value");
                            mutator.setPublic()
                                    .setName(name)
                                    .setReturnType(className)
                                    .setBody("this." + name + " = value;\nreturn this;");

                            AnnotationSource<JavaClassSource> bindingMeta = accessor.addAnnotation();
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
     * @param scope
     * @param resourceMetaData
     * @param javaClass
     */
    public static void createChildAccessors(GeneratorScope scope, ResourceMetaData resourceMetaData, JavaClassSource javaClass) {

        final JavaClassSource subresourceClass = createSubresourceClass(resourceMetaData, javaClass);

        // For each subresource create a getter/mutator/list-mutator
        final ResourceDescription resourceMetaDataDescription = resourceMetaData.getDescription();
        final Set<String> childrenNames = resourceMetaDataDescription.getChildrenNames();
        for (String childName : childrenNames) {

            final AddressTemplate childAddress = resourceMetaData.getAddress().append(childName + "=*");
            final JavaClassSource childClass = scope.getGenerated(childAddress);
            //subresourceClass.addImport(childClass);

            final String childClassName = childClass.getName();
            final String propType = "java.util.List<" + childClassName + ">";
            String propName = CaseFormat.UPPER_CAMEL.to(
                    CaseFormat.LOWER_CAMEL,
                    Keywords.escape(childClassName)
            );
            if (!propName.endsWith("s")) { propName += "s"; }

            // Add a property and an initializer for this subresource to the class
            final String resourceText = resourceMetaDataDescription.getChildDescription(childName).getText();
            subresourceClass.addField()
                    .setName(propName)
                    .setType(propType)
                    .setPrivate()
                    .setLiteralInitializer("new java.util.ArrayList<>();")
                    .getJavaDoc().setText(resourceText);

            // Add an accessor method
            final MethodSource<JavaClassSource> accessor = subresourceClass.addMethod();
            accessor.getJavaDoc()
                    .setText("Get the list of " + childClassName + " resources")
                    .addTagValue("@return", "the list of resources");
            accessor.setPublic()
                    .setName(propName)
                    .setReturnType(propType)
                    .setBody("return this." + propName + ";");

            // Add a mutator method that takes a list of resources. Mutators are added to the containing class
            final MethodSource<JavaClassSource> listMutator = javaClass.addMethod();
            listMutator.getJavaDoc()
                    .setText("Add all " + childClassName + " objects to this subresource")
                    .addTagValue("@return", "this")
                    .addTagValue("@param", "value List of " + childClassName + " objects.");
            listMutator.addParameter(propType, "value");
            listMutator.setPublic()
                    .setName(propName)
                    .setReturnType(javaClass.getName())
                    .setBody("this.subresources." + propName + ".addAll(value);\nreturn this;");

            // Add a mutator method that takes a single resource. Mutators are added to the containing class
            final MethodSource<JavaClassSource> mutator = javaClass.addMethod();
            mutator.getJavaDoc()
                    .setText("Add the " + childClassName + " object to the list of subresources")
                    .addTagValue("@param", "value The " + childClassName + " to add")
                    .addTagValue("@return", "this");
            mutator.addParameter(childClassName, "value");
            mutator.setPublic()
                    .setName(propName)
                    .setReturnType(javaClass.getName())
                    .setBody("this.subresources." + propName + ".add(value);\nreturn this;");

            final AnnotationSource<JavaClassSource> subresourceMeta = accessor.addAnnotation();
            subresourceMeta.setName("Subresource");

        }

        // initialize the collections
        javaClass.addNestedType(subresourceClass);
    }

    private static JavaClassSource createSubresourceClass(ResourceMetaData resourceMetaData, JavaClassSource javaClass) {

        JavaClassSource subresourceClass =  Roaster.parse(
                JavaClassSource.class,
                "class " + javaClass.getName() + "Resources" + " {}"
        );
        subresourceClass.setPackage(resourceMetaData.get(ResourceMetaData.PKG));
        subresourceClass.getJavaDoc().setText("Child mutators for " + javaClass.getName());

        javaClass.addField()
                .setPrivate()
                .setType(subresourceClass.getName())
                .setName("subresources")
                .setLiteralInitializer("new " + subresourceClass.getName() + "();");

        final MethodSource<JavaClassSource> subresourcesMethod = javaClass.addMethod()
                .setName("subresources")
                .setPublic();
        subresourcesMethod.setReturnType(subresourceClass.getName());
        subresourcesMethod.setBody("return this.subresources;");
        subresourcesMethod.addAnnotation().setName("Subresource");


        javaClass.addImport("java.util.List");
        javaClass.addImport(Subresource.class);
        return subresourceClass;
    }
}
