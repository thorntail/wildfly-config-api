package org.wildfly.swarm.config.runtime.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Heiko Braun
 * @since 31/07/15
 */
public class ListTypeAdapter {

    @SuppressWarnings("unchecked")
    public void toDmr(ModelNode target, List list) {

        if(list.isEmpty()) {
            target.setEmptyList();
        }
        else {
            for (Object value : list) {
                ModelNode node = target.add();
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

    }

    // TODO handle composite values
    @SuppressWarnings("unchecked")
    public void fromDmr(Object entity, String javaName, ModelType dmrType, Class<?> propertyType, ModelNode dmrPayload) throws Exception {

        Method target = entity.getClass().getMethod(javaName, propertyType);
        List<ModelNode> items = dmrPayload.isDefined() ? dmrPayload.asList() : Collections.EMPTY_LIST;

        if(items.isEmpty()) {
            target.invoke(entity, Collections.EMPTY_LIST);
        }
        else
        {
            List list = new ArrayList(items.size());

            // java type is derived from list item dmr type
            ModelType listValueType = items.get(0).getType();
            for (ModelNode item : items) {
                addJavaValueTo(list, listValueType, item);
            }

            target.invoke(entity, list);
        }


    }

    private void addJavaValueTo(List target, ModelType type, ModelNode propValue)
    {
        if(type.equals(ModelType.STRING))
        {
            target.add(propValue.asString());
        }
        else if(type.equals(ModelType.INT))
        {
            target.add(propValue.asInt());
        }
        else if(type.equals(ModelType.DOUBLE))
        {
            target.add(propValue.asDouble());
        }
        else if(type.equals(ModelType.LONG))
        {
            target.add(propValue.asLong());
        }
        else if(type.equals(ModelType.BOOLEAN))
        {
            target.add(propValue.asBoolean());
        }
        else
        {
            throw new RuntimeException("Unsupported DMR type: "+type);
        }

    }

}
