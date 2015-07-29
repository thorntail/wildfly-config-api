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
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.operations.DefaultStatementContext;
import org.wildfly.apigen.operations.ReadDescription;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DESCRIPTION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.TYPE;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class Generator {

    private static ModelControllerClient client;

    private static final Logger log = Logger.getLogger(Generator.class.getName());

    private final String targetDir;
    private final Config config;

    public Generator(String targetDir, Config config) {
        this.targetDir = targetDir;
        this.config = config;


        try {
            client = ModelControllerClient.Factory.create(
                    config.getHost(), config.getPort(),
                    new AuthCallback(new String[] {
                            config.getUser(), config.getPass()
                    })
            );
        } catch (UnknownHostException e) {
            log.severe("Failed to create model controller client: "+e.getMessage());
        }

    }

    public static void main(String[] args) throws Exception {
        log.info("Config: " + args[0]);
        log.info("Output: " + args[1]);

        Config config = Config.fromJson(args[0]);
        Generator generator = new Generator(args[1], config);
        generator.generate();
        generator.shutdown();
    }

    private void shutdown() {
        try {
            client.close();
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public void generate() {
        config.getReferences().forEach(
                ref -> {
                    try {
                        ResourceDescription resourceDescription = readDescription(ref);
                        generate(ref, resourceDescription, targetDir);
                    } catch (Exception e) {
                        log.severe(e.getMessage());
                    }
                }
        );
    }

    private static ResourceDescription readDescription(Config.ResourceRef ref) throws Exception{
        ReadDescription op = new ReadDescription(ref.getSourceAddress());
        ModelNode response = client.execute(op.resolve(new DefaultStatementContext()));
        return ResourceDescription.from(response);
    }

    private static void generate(
            Config.ResourceRef ref,
            ResourceDescription description,
            String targetDir) throws Exception {

        String className = Types.javaClassName(ref.getSourceAddress().getResourceType());

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
        log.info(fileName.toString());
        Files.write(fileName, javaClass.toString().getBytes());

    }
}
