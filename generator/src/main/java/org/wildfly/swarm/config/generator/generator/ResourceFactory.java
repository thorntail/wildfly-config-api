package org.wildfly.swarm.config.generator.generator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.base.CaseFormat;
import org.jboss.dmr.ModelType;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;
import org.jboss.forge.roaster.model.source.JavaEnumSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.logmanager.Level;
import org.wildfly.swarm.config.generator.model.ResourceDescription;
import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.Addresses;
import org.wildfly.swarm.config.runtime.AttributeDocumentation;
import org.wildfly.swarm.config.runtime.Implicit;
import org.wildfly.swarm.config.runtime.Keyed;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;
import org.wildfly.swarm.config.runtime.ResourceDocumentation;
import org.wildfly.swarm.config.runtime.ResourceType;
import org.wildfly.swarm.config.runtime.SingletonResource;
import org.wildfly.swarm.config.runtime.Subresource;
import org.wildfly.swarm.config.runtime.SubresourceInfo;
import org.wildfly.swarm.config.runtime.invocation.Types;
import org.wildfly.swarm.config.runtime.model.AddressTemplate;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ALLOWED;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DEPRECATED;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DESCRIPTION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.TYPE;

/**
 * Encapsulates the templates for generating source files from resource descriptions
 *
 * @author Heiko Braun
 * @since 30/07/15
 */
public class ResourceFactory implements SourceFactory {

    private static final Logger log = Logger.getLogger(ResourceFactory.class.getName());

    private final Set<String> names = new HashSet<>();

    /**
     * Base template for a resource representation.
     * Covers the resource attributes
     *
     * @param index
     * @param plan
     * @return
     */
    public JavaClassSource create(ClassIndex index, ClassPlan plan) {
        this.names.clear();

        // base class
        JavaClassSource type = Roaster.parse(
                JavaClassSource.class,
                "public class " + plan.getClassName() + "<T extends " + plan.getClassName() + "<T>> implements " + Keyed.class.getName() + "{}"
        );


        type.setPackage(plan.getPackageName());

        type.addImport(AttributeDocumentation.class);
        type.addImport(ResourceDocumentation.class);
        type.addImport(SingletonResource.class);

        JavaDocSource javaDoc = type.getJavaDoc();
        ResourceDescription desc = plan.getDescription();
        javaDoc.setText(desc.getText());

        addAddressAnnotations(type, plan);
        addConstructor(type, plan);
        addResourceTypeAnnotation(type, plan);
        addPropertyChangeSupport(type, plan);

        addChildResources(index, type, plan);
        addSingletonResources(index, type, plan);

        if (plan.getSubresourceClass() != null) {
            type.addNestedType(plan.getSubresourceClass());
        }

        for (EnumPlan enumPlan : plan.getEnumPlans()) {
            EnumFactory factory = new EnumFactory();
            JavaEnumSource enumType = factory.create(index, enumPlan);
            enumType.setStatic(true);
            type.addNestedType(enumType);
        }

        addAttribtues(index, type, plan);

        return type;
    }

    protected void addConstructor(JavaClassSource type, ClassPlan plan) {

        //type.extendSuperType(HashMap.class);

        // resource name
        type.addField()
                .setName("key")
                .setPrivate()
                .setType(String.class);

        // constructors
        boolean isSingleton = plan.isSingleton();
        if (isSingleton) {
            type.addMethod()
                    .setConstructor(true)
                    .setPublic()
                    .setBody("super();\nthis.key = \"" + plan.getSingletonName() + "\";\n"
                            + "this.pcs = new PropertyChangeSupport(this);");
        } else {
            // regular resources need to provide a key
            type.addMethod()
                    .setConstructor(true)
                    .setPublic()
                    .setBody("super();\nthis.key = key;")
                    .addParameter(String.class, "key");

        }

        type.addMethod()
                .setName("getKey")
                .setPublic()
                .setReturnType(String.class)
                .setBody("return this.key;");
    }

    protected void addAddressAnnotations(JavaClassSource type, ClassPlan plan) {

        AddressTemplate address = plan.getAddr();

        // resource references
        if (1 == plan.getAddresses().size()) {
            type.addImport(Address.class);
            AnnotationSource<JavaClassSource> addressMeta = type.addAnnotation(Address.class);
            addressMeta.setStringValue(plan.getAddresses().get(0).toString());
        } else {
            type.addImport(Addresses.class);
            String[] addresses = new String[plan.getAddresses().size()];
            int i = 0;
            for (AddressTemplate addressTemplate : plan.getAddresses()) {
                addresses[i] = addressTemplate.toString();
                i++;
            }
            AnnotationSource<JavaClassSource> addressesMeta = type.addAnnotation(Addresses.class);
            addressesMeta.setStringArrayValue(addresses);
        }
    }




    protected void addResourceTypeAnnotation(JavaClassSource type, ClassPlan plan) {
        type.addImport(ResourceType.class);

        AnnotationSource<JavaClassSource> typeAnno = type.addAnnotation();
        typeAnno.setName("ResourceType");
        typeAnno.setStringValue(plan.getResourceType());

        if (plan.isSingleton()) {
            type.addImport(Implicit.class);
            AnnotationSource<JavaClassSource> implicitMeta = type.addAnnotation();
            implicitMeta.setName(Implicit.class.getSimpleName());
        }
    }

    protected void addPropertyChangeSupport(JavaClassSource type, ClassPlan plan) {

        // property change listeners
        type.addField()
                .setName("pcs")
                .setType(PropertyChangeSupport.class)
                .setPrivate();

        final MethodSource<JavaClassSource> listenerAdd = type.addMethod();
        listenerAdd.getJavaDoc().setText("Adds a property change listener");
        listenerAdd.setPublic()
                .setName("addPropertyChangeListener")
                .addParameter(PropertyChangeListener.class, "listener");
        listenerAdd.setBody("if(null==this.pcs) this.pcs = new PropertyChangeSupport(this);\n" +
                "this.pcs.addPropertyChangeListener(listener);");

        final MethodSource<JavaClassSource> listenerRemove = type.addMethod();
        listenerRemove.getJavaDoc().setText("Removes a property change listener");
        listenerRemove.setPublic()
                .setName("removePropertyChangeListener")
                .addParameter(PropertyChangeListener.class, "listener");
        listenerRemove.setBody("if(this.pcs!=null) this.pcs.removePropertyChangeListener(listener);");
    }

    protected void addAttribtues(ClassIndex index, JavaClassSource type, ClassPlan plan) {
        ResourceDescription desc = plan.getDescription();
        Inflector inflector = new Inflector();

        type.addImport(ModelNodeBinding.class);

        desc.getAttributes().forEach(
                att -> {
                    if (this.names.contains(att.getName())) {
                        System.err.println("WARNING: skipping attribute: " + att.getName() + ": conflicts with sub-resource");
                        return;
                    }

                    ModelType modelType = ModelType.valueOf(att.getValue().get(TYPE).asString());
                    Optional<String> resolvedType = Types.resolveJavaTypeName(modelType, att.getValue());

                    if (resolvedType.isPresent()) {// && !att.getValue().get(DEPRECATED).isDefined()) {
                        // attributes
                        try {
                            final String name = javaAttributeName(att.getName());
                            final String attributeType;

                            // Determine if we should create an enum for strings that specify values
                            if (modelType == ModelType.STRING && att.getValue().hasDefined(ALLOWED)) {
                                // Create the enum name and enum source
                                boolean standaloneEnum = false;
                                EnumPlan enumPlan = plan.lookup(att);
                                if (enumPlan == null) {
                                    standaloneEnum = true;
                                    enumPlan = index.lookup(plan, att);
                                }
                                if ( enumPlan == null ) {
                                    attributeType = resolvedType.get();
                                } else {
                                    final String enumName = Character.toUpperCase(name.charAt(0)) + name.substring(1, name.length());
                                    attributeType = enumPlan.getClassName();
                                    type.addImport(Arrays.class);
                                    if (standaloneEnum) {
                                        type.addImport(enumPlan.getFullyQualifiedClassName());
                                    }
                                }
                            } else {
                                attributeType = resolvedType.get();
                            }

                            String attributeDescription = att.getValue().get(DESCRIPTION).asString();

                            FieldSource attributeField = type.addField()
                                    .setName(name)
                                    .setType(attributeType)
                                    .setPrivate();

                            AnnotationSource attributeAnnotation = attributeField.addAnnotation();
                            attributeAnnotation.setName(AttributeDocumentation.class.getSimpleName());
                            attributeAnnotation.setStringValue(attributeDescription);

                            final MethodSource<JavaClassSource> accessor = type.addMethod();
                            accessor.getJavaDoc().setText(attributeDescription);
                            accessor.setPublic()
                                    .setName(name)
                                    .setReturnType(attributeType)
                                    .setBody("return this." + name + ";");


                            final MethodSource<JavaClassSource> mutator = type.addMethod();
                            mutator.getJavaDoc().setText(attributeDescription);
                            mutator.addParameter(attributeType, "value");
                            mutator.setPublic()
                                    .setName(name)
                                    .setReturnType("T")
                                    .setBody("Object oldValue = this." + name + ";\n" +
                                            "this." + name + " = value;\n" +
                                            "if(this.pcs!=null) this.pcs.firePropertyChange(\"" + name + "\", oldValue, value);\n" +
                                            "return (T) this;")
                                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");

                            AnnotationSource<JavaClassSource> bindingMeta = accessor.addAnnotation();
                            bindingMeta.setName(ModelNodeBinding.class.getSimpleName());
                            bindingMeta.setStringValue("detypedName", att.getName());

                            // If the model type is LIST, then also add an appending mutator
                            if (modelType == ModelType.LIST) {
                                String singularName = inflector.singularize(name);
                                // initialize the field to an array list
                                //attributeField.setLiteralInitializer("new java.util.ArrayList<>()");
                                type.addImport(Arrays.class);
                                type.addImport(Collectors.class);
                                final MethodSource<JavaClassSource> appender = type.addMethod();
                                appender.getJavaDoc().setText(attributeDescription);
                                appender.addParameter(Types.resolveValueType(att.getValue()), "value");
                                appender.setPublic()
                                        .setName(singularName) // non-trivial to singularize the method name here
                                        .setReturnType("T")
                                        .setBody(" if ( this." + name + " == null ) { this." + name + " = new java.util.ArrayList<>(); }\nthis." + name + ".add(value);\nreturn (T) this;")
                                        .addAnnotation("SuppressWarnings").setStringValue("unchecked");

                                // also produce a var-args version

                                final MethodSource<JavaClassSource> varargs = type.addMethod();
                                varargs.getJavaDoc().setText(attributeDescription);
                                varargs.addParameter(Types.resolveValueType(att.getValue()), "...args");
                                varargs.setPublic()
                                        .setName(name)
                                        .setReturnType("T")
                                        .setBody(name + "(Arrays.stream(args).collect(Collectors.toList())); return (T) this;")
                                        .addAnnotation("SuppressWarnings").setStringValue("unchecked");

                            } else if (modelType == ModelType.OBJECT) {
                                // initialize the field to a HashMap
                                //attributeField.setLiteralInitializer("new java.util.HashMap<String, Object>()");
                                String singularName = inflector.singularize(name);
                                final MethodSource<JavaClassSource> appender = type.addMethod();
                                appender.getJavaDoc().setText(attributeDescription);
                                appender.addParameter(String.class, "key");
                                appender.addParameter(Object.class, "value");
                                appender.setPublic()
                                        .setName(singularName)
                                        .setReturnType("T")
                                        .setBody(" if ( this." + name + " == null ) { this." + name + " = new java.util.HashMap<>(); }\nthis." + name + ".put(key, value);\nreturn (T) this;")
                                        .addAnnotation("SuppressWarnings").setStringValue("unchecked");
                            }
                        } catch (Exception e) {
                            log.log(Level.ERROR, "Failed to process " + plan.getFullyQualifiedClassName() + ", attribute " + att.getName(), e);
                        }
                    } //else System.err.println(att.getValue());
                }
        );
    }


    protected void addChildResources(ClassIndex index, JavaClassSource type, ClassPlan plan) {
        if (!plan.getDescription().getChildrenTypes().isEmpty()) {
            createChildAccessors(index, plan, type);
        }
    }

    protected void addSingletonResources(ClassIndex index, JavaClassSource type, ClassPlan plan) {
        if (!plan.getDescription().getSingletonChildrenTypes().isEmpty()) {
            createSingletonChildAccessors(index, plan, type);
        }
    }

    /**
     * Decorates a base resource representation with accessors to it's child resources
     *
     * @param index
     * @param plan
     * @param javaClass
     */
    public void createChildAccessors(ClassIndex index, ClassPlan plan, JavaClassSource javaClass) {

        Inflector inflector = new Inflector();

        ResourceMetaData resourceMetaData = plan.getMetaData();

        final JavaClassSource subresourceClass = getOrCreateSubresourceClass(plan, javaClass);

        // For each subresource create a getter/mutator/list-mutator
        final ResourceDescription resourceMetaDataDescription = resourceMetaData.getDescription();
        final Set<String> childrenNames = resourceMetaDataDescription.getChildrenTypes();
        for (String childName : childrenNames) {
            this.names.add(childName);

            final AddressTemplate childAddress = resourceMetaData.getAddress().append(childName + "=*");
            final ClassPlan childClass = index.lookup(childAddress);
            //javaClass.addImport(childClass);

            javaClass.addImport(childClass.getFullyQualifiedClassName() + "Consumer");
            javaClass.addImport(childClass.getFullyQualifiedClassName() + "Supplier");

            final String childClassName = childClass.getClassName();
            javaClass.addImport(childClass.getFullyQualifiedClassName());
            final String propType = "java.util.List<" + childClassName + ">";
            String propName = CaseFormat.UPPER_CAMEL.to(
                    CaseFormat.LOWER_CAMEL,
                    Keywords.escape(childClass.getOriginalClassName())
            );

            String singularName = propName;
            String pluralName = inflector.pluralize(singularName);

            if (!propName.endsWith("s")) {
                propName = pluralName;
            }


            javaClass.addImport(SubresourceInfo.class);

            // Add a property and an initializer for this subresource to the class
            final String resourceText = resourceMetaDataDescription.getChildDescription(childName).getText();
            FieldSource<JavaClassSource> field = subresourceClass.addField();

            field.setName(propName)
                    .setType(propType)
                    .setPrivate()
                    .setLiteralInitializer("new java.util.ArrayList<>();")
                    .getJavaDoc().setText(resourceText);

            AnnotationSource attributeAnnotation = field.addAnnotation();
            attributeAnnotation.setName(ResourceDocumentation.class.getSimpleName());
            attributeAnnotation.setStringValue(resourceText);

            field.addAnnotation(SubresourceInfo.class).setStringValue(singularName);

            // Add an accessor method
            final MethodSource<JavaClassSource> accessor = subresourceClass.addMethod();
            accessor.getJavaDoc()
                    .setText("Get the list of " + childClassName + " resources")
                    .addTagValue("@return", "the list of resources");
            accessor.setPublic()
                    .setName(propName)
                    .setReturnType(propType)
                    .setBody("return this." + propName + ";");

            final MethodSource<JavaClassSource> getByKey = subresourceClass.addMethod();
            getByKey.addParameter(String.class, "key");
            getByKey.setPublic()
                    .setName(singularName)
                    .setReturnType(childClassName)
                    .setBody("return this." + propName + ".stream().filter( e->e.getKey().equals(key) ).findFirst().orElse(null);");

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
                    .setBody("this.subresources." + propName + " = value;\nreturn (T) this;")
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

            // Add a mutator method that factories a single resource and applies a supplied configurator. Mutators are added to the containing class
            final MethodSource<JavaClassSource> configurator = javaClass.addMethod();
            configurator.getJavaDoc()
                    .setText("Create and configure a " + childClassName + " object to the list of subresources")
                    .addTagValue("@param", "key The key for the " + childClassName + " resource")
                    .addTagValue("@param", "config The " + childClassName + "Consumer to use")
                    .addTagValue("@return", "this");
            configurator.addParameter(String.class, "childKey");
            configurator.addParameter(childClassName + "Consumer", "consumer");
            //configurator.addParameter(childClassName + "Consumer", "consumer");
            configurator.setPublic()
                    .setName(singularName)
                    .setReturnType("T")
                    .setBody(childClassName + "<? extends " + childClassName + "> child = new " + childClassName + "<>(childKey);\n if ( consumer != null ) { consumer.accept(child); }\n" + singularName + "(child);\nreturn (T) this;")
                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");

            // Add a mutator method that factories a single resource and applies a supplied configurator. Mutators are added to the containing class
            final MethodSource<JavaClassSource> nonConfigurator = javaClass.addMethod();
            nonConfigurator.getJavaDoc()
                    .setText("Create and configure a " + childClassName + " object to the list of subresources")
                    .addTagValue("@param", "key The key for the " + childClassName + " resource")
                    .addTagValue("@return", "this");
            nonConfigurator.addParameter(String.class, "childKey");
            nonConfigurator.setPublic()
                    .setName(singularName)
                    .setReturnType("T")
                    .setBody(singularName + "(childKey, null);\nreturn (T) this;\n")
                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");


            // Add a supplier to create

            final MethodSource<JavaClassSource> supplier = javaClass.addMethod();
            supplier.getJavaDoc()
                    .setText("Install a supplied " + childClassName + " object to the list of subresources");
            //supplier.addParameter(childClassName + "Supplier", "supplier");
            supplier.addParameter(childClassName + "Supplier", "supplier");
            supplier.setPublic()
                    .setName(singularName)
                    .setReturnType("T")
                    .setBody(singularName + "(supplier.get()); return (T) this;")
                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");

            final AnnotationSource<JavaClassSource> subresourceMeta = accessor.addAnnotation();
            subresourceMeta.setName("Subresource");


        }

        // initialize the collections
    }


    public void createSingletonChildAccessors(ClassIndex index, ClassPlan plan, JavaClassSource javaClass) {

        ResourceMetaData resourceMetaData = plan.getMetaData();

        final JavaClassSource subresourceClass = getOrCreateSubresourceClass(plan, javaClass);

        final ResourceDescription description = resourceMetaData.getDescription();
        final Set<String> singletonNames = description.getSingletonChildrenTypes();
        javaClass.addImport(Subresource.class);
        for (String singletonName : singletonNames) {

            String[] split = singletonName.split("=");
            String type = split[0];
            String name = split[1];
            final AddressTemplate childAddress = resourceMetaData.getAddress().append(type + "=" + name);
            final ClassPlan childClass = index.lookup(childAddress);
            //javaClass.addImport(childClass);

            String propName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, childClass.getOriginalClassName());

            propName = NameFixer.fixPropertyName(propName);

            FieldSource<JavaClassSource> field = subresourceClass.addField()
                    .setName(propName)
                    .setType(childClass.getFullyQualifiedClassName())
                    .setPrivate();

            AnnotationSource<JavaClassSource> singletonAnno = field.addAnnotation();
            singletonAnno.setName(SingletonResource.class.getSimpleName());


            // Add an accessor method
            final MethodSource<JavaClassSource> accessor = subresourceClass.addMethod();
            String javaDoc = description.getChildDescription(type, name).getText();
            accessor.getJavaDoc()
                    .setText(javaDoc);
            accessor.setPublic()
                    .setName(propName)
                    .setReturnType(childClass.getFullyQualifiedClassName())
                    .setBody("return this." + propName + ";");

            AnnotationSource<JavaClassSource> docAnno = field.addAnnotation();
            docAnno.setName(ResourceDocumentation.class.getSimpleName());
            docAnno.setStringValue(javaDoc);

            AnnotationSource<JavaClassSource> subresourceMeta = accessor.addAnnotation();
            subresourceMeta.setName("Subresource");


            // Add a mutator
            final MethodSource<JavaClassSource> mutator = javaClass.addMethod();
            mutator.getJavaDoc()
                    .setText(javaDoc);
            mutator.addParameter(childClass.getFullyQualifiedClassName(), "value");
            mutator.setPublic()
                    .setName(propName)
                    .setReturnType("T")
                    .setBody("this.subresources." + propName + "=value;\nreturn (T) this;")
                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");

            javaClass.addImport(childClass.getFullyQualifiedClassName() + "Consumer");
            javaClass.addImport(childClass.getFullyQualifiedClassName() + "Supplier");

            // Add a consumer to configure
            final MethodSource<JavaClassSource> consumer = javaClass.addMethod();

            consumer.getJavaDoc()
                    .setText(javaDoc);
            consumer.addParameter(childClass.getClassName() + "Consumer", "consumer");
            //consumer.addParameter(childClass.getClassName() + "Consumer", "consumer");
            consumer.setPublic()
                    .setName(propName)
                    .setReturnType("T")
                    .setBody(
                            childClass.getClassName() + "<? extends " + childClass.getClassName() + "> child = new " + childClass.getClassName() + "<>();\n"
                                    + "if ( consumer != null ) { consumer.accept(child); }\n"
                                    + "this.subresources." + propName + " = child;\n"
                                    + "return (T) this;"
                    )
                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");

            // Add a consumer to configure
            final MethodSource<JavaClassSource> noConfig = javaClass.addMethod();

            noConfig.getJavaDoc()
                    .setText(javaDoc);
            noConfig.setPublic()
                    .setName(propName)
                    .setReturnType("T")
                    .setBody(
                            childClass.getClassName() + "<? extends " + childClass.getClassName() + "> child = new " + childClass.getClassName() + "<>();\n"
                                    + "this.subresources." + propName + " = child;\n"
                                    + "return (T) this;"
                    )
                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");

            // Add a supplier to create

            final MethodSource<JavaClassSource> supplier = javaClass.addMethod();
            supplier.getJavaDoc()
                    .setText(javaDoc);
            supplier.addParameter(childClass.getClassName() + "Supplier", "supplier");
            //supplier.addParameter(  childClass.getClassName() + "Supplier", "supplier" );
            supplier.setPublic()
                    .setName(propName)
                    .setReturnType("T")
                    .setBody("this.subresources." + propName + " = supplier.get();\nreturn (T) this;")
                    .addAnnotation("SuppressWarnings").setStringValue("unchecked");
        }
    }

    private JavaClassSource getOrCreateSubresourceClass(ClassPlan plan, JavaClassSource javaClass) {

        JavaClassSource subresourceClass = plan.getSubresourceClass();

        if (subresourceClass != null) {
            return subresourceClass;
        }

        subresourceClass = Roaster.parse(
                JavaClassSource.class,
                "class " + javaClass.getName() + "Resources" + " {}"
        );
        subresourceClass.setPackage(plan.getPackageName());
        subresourceClass.getJavaDoc().setText("Child mutators for " + javaClass.getName());
        subresourceClass.setPublic();
        subresourceClass.setStatic(true);

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
        plan.setSubresourceClass(subresourceClass);
        return subresourceClass;
    }

    public final static String javaAttributeName(String dmr) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, Keywords.escape(dmr.replace("-", "_")));
    }

}

