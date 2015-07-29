package org.wildfly.apigen.generator;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;
import org.jboss.forge.roaster.model.source.PropertySource;
import org.wildfly.apigen.invocation.AuthCallback;
import org.wildfly.apigen.invocation.Binding;
import org.wildfly.apigen.invocation.Types;
import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.operations.DefaultStatementContext;
import org.wildfly.apigen.operations.ReadDescription;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DESCRIPTION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.TYPE;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class Main {

    private static ModelControllerClient client;

    public static void main(String[] args) throws Exception {
        System.out.println("Config: "+args[0]);
        System.out.println("Output: "+args[1]);

        String[] userArgs = {"admin", "passWord123"};
        client = ModelControllerClient.Factory.create("localhost", 9990, new AuthCallback(userArgs));

        Config config = Config.fromJson(args[0]);

        config.getReferences().forEach(
                ref -> {
                    try {
                        generate(ref, args[1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        client.close();
    }

    private static void generate(ResourceRef ref, String targetDir) throws Exception {
        AddressTemplate address = AddressTemplate.of(ref.getSourceAddress());
        ReadDescription op = new ReadDescription(address);

        ModelNode response = client.execute(op.resolve(new DefaultStatementContext()));
        ResourceDescription description = ResourceDescription.from(response);

        String className = Types.javaClassName(address.getResourceType());

        // base class
        JavaClassSource javaClass =  Roaster.parse(
                JavaClassSource.class,
                "public class " + className + " {}"
        );
        javaClass.setPackage(ref.getTargetPackage());

        // javadoc
        JavaDocSource javaDoc = javaClass.getJavaDoc();
        javaDoc.setText(description.getText());

        // imports
        javaClass.addImport(Binding.class);

        description.getAttributes().forEach(
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

        // write results
        targetDir = targetDir + File.separator + javaClass.getPackage().replace(".", File.separator);
        Files.createDirectories(Paths.get(targetDir));

        Path fileName = Paths.get(targetDir + File.separator + className+".java");
        System.out.println(">> Writing "+ fileName);
        Files.write(fileName, javaClass.toString().getBytes());

    }
}
