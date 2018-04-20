package org.wildfly.swarm.config.runtime.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.dmr.Property;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.*;

/**
 * @author Heiko Braun
 * @since 31/07/15
 */
public class MapTypeAdapter {

    public void toDmr(ModelNode target, Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            ModelNode node = target.get(entry.getKey());
            Object value = entry.getValue();

            if (value instanceof List) {
                new ListTypeAdapter().toDmr(node, (List) value);
            } else if (value instanceof Map) {
                new MapTypeAdapter().toDmr(node, (Map) value);
            } else {
                ModelType type = Types.resolveModelType(value.getClass());
                new SimpleTypeAdapter().toDmr(node, type, value);
            }
        }
    }

    // TODO handle composite values
    public void fromDmr(Object entity, String javaName, ModelType dmrType, Class<?> propertyType, ModelNode dmrPayload) throws Exception {

        Method target = entity.getClass().getMethod(javaName, propertyType);
        @SuppressWarnings("unchecked")
        List<Property> properties = dmrPayload.isDefined() ? dmrPayload.asPropertyList() : EMPTY_LIST;
        if(properties.isEmpty())
        {
            target.invoke(entity, EMPTY_MAP);
        }
        else
        {
            Map<String, Object> map = new HashMap<>(properties.size());

            for (Property prop : properties) {
                map.put(prop.getName(), toJavaValue(prop.getValue().getType(), prop.getValue()));
            }

            target.invoke(entity, map);
        }
    }

    private Object toJavaValue(ModelType type, ModelNode propValue) {
        if (type.equals(ModelType.STRING)) {
            return propValue.asString();
        } else if (type.equals(ModelType.INT)) {
            return propValue.asInt();
        } else if (type.equals(ModelType.DOUBLE)) {
            return propValue.asDouble();
        } else if (type.equals(ModelType.LONG)) {
            return propValue.asLong();
        } else if (type.equals(ModelType.BOOLEAN)) {
            return propValue.asBoolean();
        } else {
            throw new RuntimeException("Unsupported DMR type: " + type);
        }
    }
}
