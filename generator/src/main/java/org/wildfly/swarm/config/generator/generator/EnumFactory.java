package org.wildfly.swarm.config.generator.generator;

import java.util.List;

import org.jboss.dmr.ModelNode;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.EnumConstantSource;
import org.jboss.forge.roaster.model.source.JavaEnumSource;
import org.jboss.forge.roaster.model.source.MethodSource;

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

            boolean first = true;

            for (char c : v.toCharArray()) {
                switch (c) {
                    case '-':
                    case '.': {
                        sb.append('_');
                        break;
                    }
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9': {
                        if ( first ) {
                            sb.append( "_" );
                        }
                        sb.append( c );
                        break;

                    }
                    default:
                        sb.append(Character.toUpperCase(c));
                }
                first = false;
            }
            final EnumConstantSource constantSource = enumType.addEnumConstant(fixSingleDigitsInEnumName(sb.toString()));
            constantSource.setConstructorArguments("\"" + value.asString() + "\"");
        });

        return enumType;
    }

    static String fixSingleDigitsInEnumName(String input) {
        return input.replaceAll("^_1_", "ONE_" )
                .replaceAll( "^_2_", "TWO_")
                .replaceAll( "^_3_", "THREE_" )
                .replaceAll( "^_4_", "FOUR_" )
                .replaceAll( "^_5_", "FIVE_" )
                .replaceAll( "^_6_", "SIX_" )
                .replaceAll( "^_7_", "SEVEN_" )
                .replaceAll( "^_8_", "EIGHT_" )
                .replaceAll( "^_9_", "NINE_" )
                .replaceAll( "^_10_", "TEN_" );
    }

}
