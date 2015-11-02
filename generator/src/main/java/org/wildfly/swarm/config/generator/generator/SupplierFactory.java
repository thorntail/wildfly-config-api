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
public class SupplierFactory implements SourceFactory {

    private static final Logger log = Logger.getLogger(SupplierFactory.class.getName());

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
                "public interface " + plan.getClassName() + "Supplier<T extends " + plan.getClassName() + "> {}"
        );

        type.setPackage(plan.getPackageName());

        type.addAnnotation(FunctionalInterface.class);

        addGet( type, plan );

        return type;
    }

    protected void addGet(JavaInterfaceSource type, ClassPlan plan) {
        final MethodSource<JavaInterfaceSource> method = type.addMethod();
        method.getJavaDoc()
                .setText("Constructed instance of " + plan.getClassName() + " resource")
                .addTagValue("@return", "The instance");
        method.setPublic()
                .setName("get")
                .setReturnType(plan.getClassName());
    }

}
