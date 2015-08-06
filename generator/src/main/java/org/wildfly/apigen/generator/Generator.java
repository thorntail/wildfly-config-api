package org.wildfly.apigen.generator;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.logmanager.Level;
import org.wildfly.apigen.invocation.ClientFactory;
import org.wildfly.apigen.model.DefaultStatementContext;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.operations.ReadDescription;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

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
            client = ClientFactory.createClient(config);
        } catch (Exception e) {
            log.log(Level.ERROR, "Failed to create model controller client", e);
        }

    }

    public static void main(String[] args) throws Exception {
        log.info("Config: " + args[0]);
        log.info("Output: " + args[1]);

        Config config = Config.fromJson(args[0]);
        Generator generator = new Generator(args[1], config);
        generator.processGeneratorTargets();
        generator.shutdown();
    }

    private void shutdown() {
        try {
            client.close();
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public void processGeneratorTargets() {
        config.getGeneratorTargets().forEach(
                target -> {
                    try {
                        GeneratorScope scope = new GeneratorScope();
                        ResourceMetaData resourceMetaData = fetchMetaData(target);
                        resourceMetaData.set(ResourceMetaData.PKG, target.getTargetPackage());

                        Iterator<ResourceMetaData> iterator = new MetaDataIterator(resourceMetaData).createInstance();
                        iterator.forEachRemaining(metaData -> {
                            generate(scope, metaData, targetDir);
                        });


                    } catch (Exception e) {
                        log.log(Level.ERROR, "Failed to process targets", e);
                    }
                }
        );
    }

    private static ResourceMetaData fetchMetaData(GeneratorTarget generatorTarget) throws Exception {
        ReadDescription op = new ReadDescription(generatorTarget.getSourceAddress());
        ModelNode response = client.execute(op.resolve(new DefaultStatementContext()));
        return new ResourceMetaData(generatorTarget.getSourceAddress(), ResourceDescription.from(response));
    }

    private void generate(
            GeneratorScope scope, ResourceMetaData resourceMetaData,
            String targetDir){

        JavaClassSource javaClass = SourceFactory.createResourceAsClass(resourceMetaData);

        if (!resourceMetaData.getDescription().getChildrenNames().isEmpty()) {
            SourceFactory.createChildAccessors(scope, resourceMetaData, javaClass);
        }

        writeClass(targetDir, javaClass);
        scope.addGenerated(resourceMetaData.getAddress(), javaClass);
    }

    private void writeClass(String targetDir, JavaClassSource javaClass) {

        try {
            targetDir = targetDir + File.separator + javaClass.getPackage().replace(".", File.separator);
            Files.createDirectories(Paths.get(targetDir));

            Path fileName = Paths.get(targetDir + File.separator + javaClass.getName() + ".java");
            log.info(javaClass.getCanonicalName());
            Files.write(fileName, javaClass.toString().getBytes());
        } catch (IOException e) {
            log.log(Level.ERROR, "Failed to persist class", e);
        }

    }
}
