package org.wildfly.swarm.config.generator.generator;

import java.util.List;

import org.jboss.dmr.ModelNode;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.source.EnumConstantSource;
import org.jboss.forge.roaster.model.source.JavaEnumSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ALLOWED;

/**
 * @author Bob McWhirter
 */
public class EnumFactory {

    public JavaEnumSource create(ClassIndex index, EnumPlan plan) {
        final JavaEnumSource enumType = Roaster.create(JavaEnumSource.class)
                .setName(plan.getClassName())
                .setPublic();

        if ( plan.getPackageName() != null ) {
            enumType.setPackage(plan.getPackageName());
        }

        // Create a field to indicate the value the model expects
        enumType.addProperty(String.class, "allowedValue")
                .getAccessor()
                .getJavaDoc()
                .setText("Returns the allowed value for the management model.")
                .addTagValue("@return", "the allowed model value");

        final MethodSource<JavaEnumSource> constructor = enumType.addMethod()
                .setConstructor(true);
        constructor.addParameter(String.class, "allowedValue");
        constructor.setBody("this.allowedValue = allowedValue;");

        // Override the toString() to return the allowedValue so it can be used to determine the correct enum to use
        enumType.addMethod()
                .setName("toString")
                .setReturnType(String.class)
                .setPublic()
                .setBody("return allowedValue;")
                .addAnnotation(Override.class);


        List<ModelNode> allowedValues = plan.getAllowedValues();

        // For each allowed value add an enum constant
        allowedValues.forEach(value -> {
            final String v = value.asString();
            // Replace - and . with _ and uppercase each character
            final StringBuilder sb = new StringBuilder();
            for (char c : v.toCharArray()) {
                switch (c) {
                    case '-':
                    case '.': {
                        sb.append('_');
                        break;
                    }
                    default:
                        sb.append(Character.toUpperCase(c));
                }
            }
            final EnumConstantSource constantSource = enumType.addEnumConstant(sb.toString());
            constantSource.setConstructorArguments("\"" + value.asString() + "\"");
        });

        return enumType;
    }

}
