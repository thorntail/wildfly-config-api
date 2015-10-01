package org.wildfly.apigen.generator;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.logmanager.Level;
import org.wildfly.config.model.AddressTemplate;
import org.wildfly.apigen.model.DefaultStatementContext;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.operations.ReadDescription;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class Generator {

    private static final Logger log = Logger.getLogger(Generator.class.getName());
    private static ModelControllerClient client;

    private DefaultStatementContext statementContext;

    private final String targetDir;
    private final Config config;

    public Generator(String targetDir, Config config) {
        this.statementContext = new DefaultStatementContext();
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

        try {
            Config config = Config.fromJson(args[0]);
            Generator generator = new Generator(args[1], config);
            generator.processGeneratorTargets();
            generator.shutdown();
        } catch (Throwable e) {
            System.exit(-1);
            log.log(Level.ERROR, "Unexpected error", e);
        }
    }

    public void deleteDir(String dir) throws Exception
    {
        Path directory = Paths.get(dir);
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

        });
    }


    public void shutdown() {
        try {
            client.close();
        } catch (IOException e) {
            log.log(Level.ERROR, e.getMessage());
        }
    }

    public void processGeneratorTargets() {


        if(Files.exists(Paths.get(targetDir))) {
            System.out.println("Delete output dir: "+ targetDir);
            try {
                deleteDir(targetDir);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        config.getGeneratorTargets().forEach(
                target -> {
                    try {
                        GeneratorScope scope = new GeneratorScope();

                        // load resource entry point recursively
                        ResourceMetaData resourceMetaData = loadResourceMetaData(target);
                        resourceMetaData.set(ResourceMetaData.PKG, target.getTargetPackage());

                        // generate classes
                        Iterator<ResourceMetaData> iterator = new MetaDataTraversal(resourceMetaData).createInstance();
                        iterator.forEachRemaining(metaData -> {
                            generate(scope, metaData, targetDir);
                        });


                    } catch (Exception e) {
                        log.log(Level.ERROR, "Failed to process targets", e);
                    }
                }
        );
    }

    private ResourceMetaData loadResourceMetaData(GeneratorTarget generatorTarget) throws Exception {

        AddressTemplate address = generatorTarget.getSourceAddress();

        ModelNode composite = new ModelNode();
        composite.get(OP).set(COMPOSITE);
        composite.get(ADDRESS).setEmptyList();

        List<ModelNode> steps = new ArrayList<>();

        // read parent child type
        Integer tokens = address.tokenLength();
        AddressTemplate parentAddress = address.subTemplate(0, tokens - 1);
        ModelNode childTypes = new ModelNode();
        childTypes.get(OP).set(READ_CHILDREN_TYPES_OPERATION);
        childTypes.get(ADDRESS).set(parentAddress.resolve(new DefaultStatementContext()));
        childTypes.get("include-singletons").set(true);
        steps.add(childTypes);

        // read resource description
        ReadDescription rrd = new ReadDescription(address);
        steps.add(rrd.resolve(this.statementContext));

        composite.get(STEPS).set(steps);
        ModelNode response = client.execute(composite);

        // parent type
        boolean isSingleton = false;
        List<ModelNode> types = response.get(RESULT).get("step-1").get(RESULT).asList();
        for (ModelNode type : types) {
            if(type.asString().equals(address.getResourceType()+"="+address.getResourceName())) {
                isSingleton = true;
                break;
            }
        }

        // resource meta data
        ResourceDescription description = ResourceDescription.from(response.get(RESULT).get("step-2"));
        if(isSingleton)
        {
            description.setSingletonName(address.getResourceName());
        }

        return new ResourceMetaData(generatorTarget.getSourceAddress(), description);
    }

    private void generate(GeneratorScope scope, ResourceMetaData resourceMetaData, String targetDir){

        JavaClassSource javaClass = SourceFactory.createResourceAsClass(scope, resourceMetaData);

        if (!resourceMetaData.getDescription().getChildrenTypes().isEmpty()) {
            SourceFactory.createChildAccessors(scope, resourceMetaData, javaClass);
        }

        if (!resourceMetaData.getDescription().getSingletonChildrenTypes().isEmpty()) {
            SourceFactory.createSingletonChildAccessors(scope, resourceMetaData, javaClass);
        }

        writeClass(targetDir, javaClass);
        scope.addGenerated(resourceMetaData.getAddress(), javaClass);
    }

    private void writeClass(String targetDir, JavaClassSource javaClass) {

        try {
            targetDir = targetDir + File.separator + javaClass.getPackage().replace(".", File.separator);
            Files.createDirectories(Paths.get(targetDir));

            Path fileName = Paths.get(targetDir + File.separator + javaClass.getName() + ".java");
            if(Files.exists(fileName)) {
                throw new RuntimeException("File already exists, will be replaced: " + fileName);
            }

            Files.write(fileName, javaClass.toString().getBytes());

        } catch (IOException e) {
            log.log(Level.ERROR, "Failed to persist class", e);
        }

    }
}
