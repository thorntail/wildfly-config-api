package org.wildfly.swarm.config.generator.generator;

import java.util.logging.Logger;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.MethodSource;

/**
 * Encapsulates the templates for generating source files from resource descriptions
 *
 * @author Heiko Braun
 * @since 30/07/15
 */
public class ConfiguratorInterfaceFactory {

    private static final Logger log = Logger.getLogger(ConfiguratorInterfaceFactory.class.getName());

    /**
     * Base template for a resource representation.
     * Covers the resource attributes
     *
     * @param index
     * @param plan
     * @return
     */
    public static JavaInterfaceSource createConfiguratorAsInterface(ClassIndex index, ClassPlan plan) {

        // base class
        JavaInterfaceSource javaInterface = Roaster.parse(
                JavaInterfaceSource.class,
                "public interface " + plan.getClassName() + "Consumer<T extends " + plan.getClassName() + "> {}"
        );

        javaInterface.setPackage(plan.getPackageName());

        javaInterface.addImport(plan.getPackageName() + "." + plan.getClassName());
        javaInterface.addAnnotation( FunctionalInterface.class );

        final MethodSource<JavaInterfaceSource> accessor = javaInterface.addMethod();
        accessor.getJavaDoc()
                .setText("Configure a pre-constructed instance of " + plan.getClassName() + " resource")
                .addTagValue("@parameter", "Instance of " + plan.getClassName() + " to configure")
                .addTagValue("@return", "nothing");
        accessor.addParameter( "T", "value" );
        accessor.setPublic()
                .setName("accept")
                .setReturnType("void");

        plan.setConfiguratorInterfaceSource(javaInterface);
        return javaInterface;
    }

}
