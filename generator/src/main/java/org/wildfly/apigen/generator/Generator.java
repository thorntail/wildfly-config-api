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

    private static ResourceDescription readDescription(ResourceRef ref) throws Exception{
        ReadDescription op = new ReadDescription(ref.getSourceAddress());
        ModelNode response = client.execute(op.resolve(new DefaultStatementContext()));
        return ResourceDescription.from(response);
    }

    private void generate(
            ResourceRef ref,
            ResourceDescription description,
            String targetDir) throws Exception {

        JavaClassSource javaClass = SourceFactory.createResourceAsClass(ref, description);

        writeClass(targetDir, javaClass);
    }

    private void writeClass(String targetDir, JavaClassSource javaClass) {

        try {
            targetDir = targetDir + File.separator + javaClass.getPackage().replace(".", File.separator);
            Files.createDirectories(Paths.get(targetDir));

            Path fileName = Paths.get(targetDir + File.separator + javaClass.getName() + ".java");
            log.info(fileName.toString());
            Files.write(fileName, javaClass.toString().getBytes());
        } catch (IOException e) {
            log.log(Level.ERROR, "Failed to persist class", e);
        }

    }
}
