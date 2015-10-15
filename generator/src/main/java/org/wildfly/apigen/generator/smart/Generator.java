package org.wildfly.apigen.generator.smart;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.logmanager.Level;
import org.wildfly.apigen.generator.ClientFactory;
import org.wildfly.apigen.generator.Config;
import org.wildfly.apigen.generator.GeneratorScope;
import org.wildfly.apigen.generator.GeneratorTarget;
import org.wildfly.apigen.generator.MetaDataTraversal;
import org.wildfly.apigen.generator.ResourceMetaData;
import org.wildfly.apigen.model.DefaultStatementContext;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.operations.ReadDescription;
import org.wildfly.config.model.AddressTemplate;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADDRESS;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.COMPOSITE;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.READ_CHILDREN_TYPES_OPERATION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.RESULT;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.STEPS;

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

    public void deleteDir(String dir) throws Exception {
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


        if (Files.exists(Paths.get(targetDir))) {
            System.out.println("Delete output dir: " + targetDir);
            try {
                deleteDir(targetDir);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<SubsystemPlan> subsystems = new ArrayList<>();

        config.getGeneratorTargets().forEach(
                target -> {
                    try {
                        GeneratorScope scope = new GeneratorScope();

                        // load resource entry point recursively
                        ResourceMetaData resourceMetaData = loadResourceMetaData(target);
                        resourceMetaData.set(ResourceMetaData.PKG, target.getTargetPackage());

                        // generate classes

                        SubsystemPlan plan = new SubsystemPlan(resourceMetaData);
                        subsystems.add(plan);

                        List<ClassPlan> classPlans = plan.getClassPlans();
                        for (ClassPlan classPlan : classPlans) {
                            SourceFactory.createResourceAsClass(plan, classPlan);
                        }

                        for (ClassPlan classPlan : classPlans) {
                            if (classPlan.getJavaClassSource() == null) {
                                System.err.println("did not generate: " + classPlan.getFullyQualifiedClassName());
                            } else {
                                writeClass(classPlan.getJavaClassSource());
                                System.err.println("wrote: " + classPlan.getFullyQualifiedClassName());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.log(Level.ERROR, "Failed to process targets", e);
                    }
                }
        );

        System.err.println("----------------- BEGIN module.xml paths -----------------------");

        subsystems.stream()
                .flatMap((e) -> e.getClassPlans().stream())
                .map((e) -> e.getPackageName())
                .collect(Collectors.toSet())
                .stream().sorted()
                .forEach((e) -> {
                    System.err.println("        <path name=\"" + e.replace('.', '/' ) + "\"/>");
                });

        System.err.println("----------------- END module.xml paths -------------------------");


    }

    private void writeClass(JavaClassSource javaClass) {

        try {
            String dir = this.targetDir + File.separator + javaClass.getPackage().replace(".", File.separator);
            Files.createDirectories(Paths.get(dir));

            Path fileName = Paths.get(dir + File.separator + javaClass.getName() + ".java");
            if (Files.exists(fileName)) {
                System.err.println("File already exists, will be replaced: " + fileName);
            }

            Files.write(fileName, javaClass.toString().getBytes());

        } catch (IOException e) {
            log.log(Level.ERROR, "Failed to persist class", e);
        }

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
            if (type.asString().equals(address.getResourceType() + "=" + address.getResourceName())) {
                isSingleton = true;
                break;
            }
        }

        // resource meta data
        ResourceDescription description = ResourceDescription.from(response.get(RESULT).get("step-2"));
        if (isSingleton) {
            description.setSingletonName(address.getResourceName());
        }

        return new ResourceMetaData(generatorTarget.getSourceAddress(), description);
    }

}
