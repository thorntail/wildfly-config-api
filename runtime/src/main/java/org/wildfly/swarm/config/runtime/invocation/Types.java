package org.wildfly.swarm.config.runtime.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class Types<T> {

    private static final Logger logger = Logger.getLogger(Types.class.getName());

    /*public static ModelType toDmr(String javaTypeName) {

        ModelType type = null;

        if("java.lang.String".equals(javaTypeName))
            type = ModelType.STRING;
        else if("java.lang.Integer".equals(javaTypeName))
            type = ModelType.INT;
        else if("java.lang.Long".equals(javaTypeName))
            type = ModelType.LONG;
        else if("java.lang.Boolean".equals(javaTypeName))
            type = ModelType.BOOLEAN;
        else if("java.lang.Double".equals(javaTypeName))
            type = ModelType.DOUBLE;
        else if("java.util.List".equals(javaTypeName)) {
            type = ModelType.LIST;
        } else {
            throw new RuntimeException("Failed to resolve ModelType for '"+ javaTypeName+"'");
        }

        return type;
    }*/

    /*public static Object fromDmr(ModelNode attributeValue) {

        Object result = null;
        final ModelType attributeType = attributeValue.getType();

        if(ModelType.STRING == attributeType)
        {
            result = attributeValue.asString();
        }
        else if(ModelType.UNDEFINED== attributeType)
        {
            result = null;
        }
        else if(ModelType.INT == attributeType)
        {
            result = attributeValue.asInt();
        }
        else if(ModelType.LONG == attributeType)
        {
            result = attributeValue.asLong();
        }
        else if(ModelType.BOOLEAN == attributeType)
        {
            result = attributeValue.asBoolean();
        }
        else if(ModelType.DOUBLE == attributeType)
        {
            result = attributeValue.asDouble();
        }
        else if(ModelType.LIST == attributeType)
        {
            final List<ModelNode> nodeList = attributeValue.asList();
            List list = new ArrayList(nodeList.size());
            for(ModelNode item : nodeList)
                list.add(item.asString());

            result = list;
        }
        else if(ModelType.OBJECT == attributeType)
        {
            result = attributeValue.asString();
        }
        else {
            throw new RuntimeException("Unsupported type "+attributeType);
        }

        return result;
    }
*/
    public static Optional<String> resolveJavaTypeName(ModelType modelType, ModelNode value) {

        Optional<String> result = Optional.empty();

        if(ModelType.STRING == modelType)
        {
            result = Optional.of("java.lang.String");
        }
        else if(ModelType.INT == modelType)
        {
            result = Optional.of("java.lang.Integer");
        }
        else if(ModelType.LONG == modelType)
        {
            result = Optional.of("java.lang.Long");
        }
        else if(ModelType.BOOLEAN == modelType)
        {
            result = Optional.of("java.lang.Boolean");
        }
        else if(ModelType.DOUBLE == modelType)
        {
            result = Optional.of("java.lang.Double");
        }
        else if(ModelType.BIG_DECIMAL == modelType )
        {
            result = Optional.of("java.math.BigDecimal");
        }
        else if (ModelType.OBJECT == modelType) {
            result = Optional.of("java.util.Map");
        }
        else if (ModelType.LIST == modelType) {
            String templatedType = "<?>";

            final ModelNode valueTypeNode = value.get("value-type");
            if (valueTypeNode.getType() == ModelType.OBJECT) {
                templatedType = "<java.util.Map>";
            } else {
                final ModelType type = valueTypeNode.asType();
                if (type == ModelType.STRING) {
                    templatedType = "<String>";
                }
            }

            result = Optional.of("java.util.List"+templatedType);
        }
        else
        {
            logger.warning("Unsupported type "+modelType);
        }

        return result;
    }

    public static ModelType resolveModelType(Class<?> javaType) {

        ModelType type = null;

        if(String.class.equals(javaType))
            type = ModelType.STRING;
        else if(Integer.class.equals(javaType))
            type = ModelType.INT;
        else if(Long.class.equals(javaType))
            type = ModelType.LONG;
        else if(Boolean.class.equals(javaType))
            type = ModelType.BOOLEAN;
        else if(Double.class.equals(javaType))
            type = ModelType.DOUBLE;
        else if(BigDecimal.class.equals(javaType))
            type = ModelType.BIG_DECIMAL;
        else if(List.class.equals(javaType))
            type = ModelType.LIST;
        else if(Map.class.equals(javaType))
            type = ModelType.OBJECT;
        else {
            throw new RuntimeException("Failed to resolve ModelType for '"+ javaType.getName()+"'");
        }

        return type;
    }

}
