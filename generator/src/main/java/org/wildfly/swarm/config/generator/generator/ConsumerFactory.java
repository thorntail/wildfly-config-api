package org.wildfly.swarm.config.generator.generator;

import java.util.logging.Logger;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.MethodSource;

/**
 * Encapsulates the templates for generating source files from resource descriptions
 *
 * @author Heiko Braun
 * @since 30/07/15
 */
public class ConsumerFactory implements SourceFactory {

    private static final Logger log = Logger.getLogger(ConsumerFactory.class.getName());

    /**
     * Base template for a resource representation.
     * Covers the resource attributes
     *
     * @param index
     * @param plan
     * @return
     */
    public JavaType create(ClassIndex index, ClassPlan plan) {

        // base class
        JavaInterfaceSource type = Roaster.parse(
                JavaInterfaceSource.class,
                "public interface " + plan.getClassName() + "Consumer<T extends " + plan.getClassName() + "> {}"
        );

        type.setPackage(plan.getPackageName());

        type.addImport(plan.getPackageName() + "." + plan.getClassName());
        type.addAnnotation(FunctionalInterface.class);

        addAccept(type, plan);

        return type;
    }

    protected void addAccept(JavaInterfaceSource type, ClassPlan plan) {
        final MethodSource<JavaInterfaceSource> method = type.addMethod();
        method.getJavaDoc()
                .setText("Configure a pre-constructed instance of " + plan.getClassName() + " resource")
                .addTagValue("@parameter", "Instance of " + plan.getClassName() + " to configure")
                .addTagValue("@return", "nothing");
        method.addParameter( "T", "value" );
        method.setName("accept")
                .setReturnType("void");
    }

}
