package org.wildfly.config.invocation;

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

    public void toDmr(ModelNode modelMode, String detypedName, Map<String,String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            modelMode.get(detypedName).get(entry.getKey()).set(entry.getValue());
        }
    }

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
