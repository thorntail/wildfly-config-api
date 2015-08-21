package org.wildfly.apigen.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Heiko Braun
 * @since 31/07/15
 */
public class ListTypeAdapter {

    @SuppressWarnings("unchecked")
    public void toDmr(ModelNode modelMode, String detypedName, List value) {

        if(value.isEmpty()) {
            modelMode.get(detypedName).setEmptyList();
        }
        else {
            value.forEach(
                    v -> {
                        // model type  is derived from list item java type
                        ModelType listValueType = Types.resolveModelType(v.getClass());
                        addDmrValueTo(modelMode.get(detypedName), listValueType, v);
                    }
            );
        }

    }

    private void addDmrValueTo(ModelNode target, ModelType type, Object propValue)
    {
        if(type.equals(ModelType.STRING))
        {
            target.add((String) propValue);
        }
        else if(type.equals(ModelType.INT))
        {
            target.add((Integer) propValue);
        }
        else if(type.equals(ModelType.DOUBLE))
        {
            target.add((Double) propValue);
        }
        else if(type.equals(ModelType.LONG))
        {
            target.add((Long) propValue);
        }
        else if(type.equals(ModelType.BOOLEAN))
        {
            target.add((Boolean) propValue);
        }
        else
        {
            throw new RuntimeException("Unsupported DMR type: "+type);
        }
    }


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
