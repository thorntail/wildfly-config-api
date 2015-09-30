package org.wildfly.apigen.generator;

import com.google.common.base.CaseFormat;
import org.jboss.dmr.ModelType;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.logmanager.Level;
import org.wildfly.config.runtime.Address;
import org.wildfly.config.runtime.ModelNodeBinding;
import org.wildfly.config.runtime.Implicit;
import org.wildfly.config.runtime.Subresource;
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
     *
     * @param scope
     * @param metaData
     * @return
     */
    public static JavaClassSource createResourceAsClass(GeneratorScope scope, ResourceMetaData metaData) {

        String className = Types.javaClassName(metaData);

        // base class
        JavaClassSource javaClass =  Roaster.parse(
                JavaClassSource.class,
                "public class " + className + "<T extends " + className + "> {}"
        );

        // resource name
        javaClass.addField()
                .setName("key")
                .setPrivate()
                .setType(String.class);

        // constructors
        boolean isSingleton = metaData.getDescription().isSingleton();
        if(isSingleton)
        {
            javaClass.addMethod()
                    .setConstructor(true)
                    .setPublic()
                    .setBody("this.key = \""+metaData.getDescription().getSingletonName()+"\";");
        }
        else
        {
            // regular resources need to provide a key
            javaClass.addMethod()
                    .setConstructor(true)
                    .setPublic()
                    .setBody("this.key = key;")
                    .addParameter(String.class, "key");

        }

        MethodSource<JavaClassSource> keyAccessor = javaClass.addMethod()
                .setName("getKey")
                .setPublic()
                .setReturnType(String.class)
                .setBody("return this.key;");


        javaClass.setPackage(derivePackageName(metaData));

        // javadoc
        JavaDocSource javaDoc = javaClass.getJavaDoc();
        ResourceDescription desc = metaData.getDescription();
        javaDoc.setText(desc.getText());

        // imports
        javaClass.addImport(Implicit.class);
        javaClass.addImport(Address.class);
        javaClass.addImport(ModelNodeBinding.class);


        AnnotationSource<JavaClassSource> addressMeta = javaClass.addAnnotation();
        addressMeta.setName("Address");
        addressMeta.setStringValue(metaData.getAddress().getTemplate());

        if(isSingleton) {
            AnnotationSource<JavaClassSource> implicitMeta = javaClass.addAnnotation();
            implicitMeta.setName("Implicit");
        }

        desc.getAttributes().forEach(
                att -> {
                    ModelType modelType = ModelType.valueOf(att.getValue().get(TYPE).asString());
                    Optional<String> resolvedType = Types.resolveJavaTypeName(modelType, att.getValue());

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
                                    .setReturnType("T")
                                    .setBody("this." + name + " = value;\nreturn (T) this;")
                                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");

                            AnnotationSource<JavaClassSource> bindingMeta = accessor.addAnnotation();
                            bindingMeta.setName("ModelNodeBinding");
                            bindingMeta.setStringValue("detypedName", att.getName());
                        } catch (Exception e) {
                            log.log(Level.ERROR, "Failed to process " + metaData.getAddress() + ", attribute " + att.getName(), e);
                        }
                    } //else System.err.println(att.getValue());
                }
        );

        return javaClass;
    }

    private static String derivePackageName(ResourceMetaData metaData) {
        int level = metaData.getAddress().tokenLength();
        StringBuffer sb = new StringBuffer();
        if(level>1) {

            int subLevel = level;
            while(subLevel>=1) {
                AddressTemplate sub = metaData.getAddress().subTemplate(subLevel - 1, subLevel);
                String type = sub.getResourceType().replace("-", "_");
                String subPackage = CaseFormat.LOWER_UNDERSCORE.to(
                        CaseFormat.LOWER_CAMEL,
                        type
                );

                sb.insert(0, "."+subPackage);
                subLevel--;
            }

        }

        sb.insert(0, metaData.get(ResourceMetaData.PKG));
        //System.out.println(metaData.getAddress() + " >> " + sb.toString());
        return sb.toString();

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
        final Set<String> childrenNames = resourceMetaDataDescription.getChildrenTypes();
        for (String childName : childrenNames) {

            final AddressTemplate childAddress = resourceMetaData.getAddress().append(childName + "=*");
            final JavaClassSource childClass = scope.getGenerated(childAddress);
            javaClass.addImport(childClass);

            final String childClassName = childClass.getName();
            final String propType = "java.util.List<" + childClassName + ">";
            String propName = CaseFormat.UPPER_CAMEL.to(
                    CaseFormat.LOWER_CAMEL,
                    Keywords.escape(childClassName)
            );
            String singularName = propName;
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
                    .setReturnType("T")
                    .setBody("this.subresources." + propName + ".addAll(value);\nreturn (T) this;")
                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");

            // Add a mutator method that takes a single resource. Mutators are added to the containing class
            final MethodSource<JavaClassSource> mutator = javaClass.addMethod();
            mutator.getJavaDoc()
                    .setText("Add the " + childClassName + " object to the list of subresources")
                    .addTagValue("@param", "value The " + childClassName + " to add")
                    .addTagValue("@return", "this");
            mutator.addParameter(childClassName, "value");
            mutator.setPublic()
                    .setName(singularName)
                    .setReturnType("T")
                    .setBody("this.subresources." + propName + ".add(value);\nreturn (T) this;")
                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");

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
        subresourceClass.setPublic();

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

        javaClass.addImport("java.util.List");
        javaClass.addImport(Subresource.class);
        return subresourceClass;
    }

    public static void createSingletonChildAccessors(GeneratorScope scope, ResourceMetaData resourceMetaData, JavaClassSource javaClass) {

        final ResourceDescription description = resourceMetaData.getDescription();
        final Set<String> singletonNames = description.getSingletonChildrenTypes();
        for (String singletonName : singletonNames) {

            String[] split = singletonName.split("=");
            String type = split[0];
            String name = split[1];
            final AddressTemplate childAddress = resourceMetaData.getAddress().append(type + "=" + name);
            final JavaClassSource childClass = scope.getGenerated(childAddress);
            javaClass.addImport(childClass);

            String propName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, name);

            javaClass.addField()
                    .setName(propName)
                    .setType(childClass)
                    .setPrivate();

            // Add an accessor method
            final MethodSource<JavaClassSource> accessor = javaClass.addMethod();
            String javaDoc = description.getChildDescription(type, name).getText();
            accessor.getJavaDoc()
                    .setText(javaDoc);
            accessor.setPublic()
                    .setName(propName)
                    .setReturnType(childClass)
                    .setBody("return this." + propName + ";");

            AnnotationSource<JavaClassSource> subresourceMeta = accessor.addAnnotation();
            subresourceMeta.setName("Subresource");

            // Add a mutator
            final MethodSource<JavaClassSource> mutator = javaClass.addMethod();
            mutator.getJavaDoc()
                    .setText(javaDoc);
            mutator.addParameter(childClass, "value");
            mutator.setPublic()
                    .setName(propName)
                    .setReturnType("T")
                    .setBody("this." + propName + "=value;\nreturn (T) this;")
                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");

        }
    }
}
