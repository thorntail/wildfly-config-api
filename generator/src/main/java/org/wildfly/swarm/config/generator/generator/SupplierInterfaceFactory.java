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
public class SupplierInterfaceFactory {

    private static final Logger log = Logger.getLogger(SupplierInterfaceFactory.class.getName());

    /**
     * Base template for a resource representation.
     * Covers the resource attributes
     *
     * @param index
     * @param plan
     * @return
     */
    public static JavaInterfaceSource createSupplierAsInterface(ClassIndex index, ClassPlan plan) {

        // base class
        JavaInterfaceSource javaInterface = Roaster.parse(
                JavaInterfaceSource.class,
                "public interface " + plan.getClassName() + "Supplier<T extends " + plan.getClassName() + "> {}"
        );

        javaInterface.setPackage(plan.getPackageName());

        javaInterface.addImport(plan.getPackageName() + "." + plan.getClassName());
        javaInterface.addAnnotation( FunctionalInterface.class );

        final MethodSource<JavaInterfaceSource> accessor = javaInterface.addMethod();
        accessor.getJavaDoc()
                .setText("Constructed instance of " + plan.getClassName() + " resource")
                .addTagValue("@return", "The instance");
        accessor.setPublic()
                .setName("get")
                .setReturnType(plan.getClassName());

        plan.setSupplierInterfaceSource(javaInterface);
        return javaInterface;
    }

}
