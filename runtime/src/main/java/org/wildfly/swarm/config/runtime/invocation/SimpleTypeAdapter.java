package org.wildfly.swarm.config.runtime.invocation;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.dmr.ValueExpression;

/**
 * @author Heiko Braun
 * @since 31/07/15
 */
public class SimpleTypeAdapter {

    public void toDmr(ModelNode target, ModelType type, Object propValue)
    {
        if(type.equals(ModelType.STRING))
        {
            String value = propValue.toString();
            if (value.startsWith("${") && value.endsWith("}")) value = new ValueExpression(value).resolveString();
            target.set(value);
        }
        else if(type.equals(ModelType.INT))
        {
            target.set((Integer) propValue);
        }
        else if(type.equals(ModelType.DOUBLE))
        {
            target.set((Double) propValue);
        }
        else if(type.equals(ModelType.LONG))
        {
            target.set((Long) propValue);
        }
        else if(type.equals(ModelType.BOOLEAN))
        {
            target.set((Boolean) propValue);
        }
        else if (type.equals(ModelType.BIG_DECIMAL ) ) {
            target.set((BigDecimal) propValue);
        }
        else
        {
            throw new RuntimeException("Unsupported DMR type: "+type);
        }
    }

    public void fromDmr(Object entity, String javaName, ModelType dmrType, Class<?> propertyType, ModelNode dmrPayload) throws Exception {
        Method target = entity.getClass().getMethod(javaName, propertyType);
        setJavaValueOn(entity, target, propertyType, dmrPayload);
    }

    private void setJavaValueOn(Object entity, Method method, Class<?> propertyType, ModelNode dmrPayload) throws Exception {
        Object value = null;

        // VALUES
        if(Boolean.class.equals(propertyType)) {
            if(dmrPayload.isDefined())
                value = dmrPayload.asBoolean();
            else
                value = false;
        }
        else if(Long.class.equals(propertyType))
        {
            if(dmrPayload.isDefined())
                value = dmrPayload.asLong();
            else
                value = null;
        }
        else if(Integer.class.equals(propertyType))
        {
            if(dmrPayload.isDefined())
                value = dmrPayload.asInt();
            else
                value = null;
        }
        else if(Double.class.equals(propertyType))
        {
            if(dmrPayload.isDefined())
                value = dmrPayload.asDouble();
            else
                value = null;
        }
        else if(Float.class.equals(propertyType))
        {
            if(dmrPayload.isDefined())
                value = dmrPayload.asDouble();
            else
                value = null;
        }
        else if(String.class.equals(propertyType))
        {
            // default
            if(dmrPayload.isDefined())
                value = dmrPayload.asString();
            else
                value = "";
        }
        else if(Enum.class.isAssignableFrom(propertyType))
        {
            if(dmrPayload.isDefined()) {
                final String allowedValue = dmrPayload.asString();
                // The toString() value should be the same as the DMR string
                for (Object e : propertyType.getEnumConstants()) {
                    if (allowedValue.equals(e.toString())) {
                        value = e;
                        break;
                    }
                }
                if (value == null) {
                    throw new RuntimeException(String.format("Could not determine correct enum value for type %s with value %s", propertyType, dmrPayload.asString()));
                }
            } else
                value = null;
        }

      /*  else if ("java.util.List".equals(propBinding.getJavaTypeName()))
                       {
                           ModelNode list = actualPayload.resolve(detypedName);
                           if (list.isDefined() && propValue.isDefined() && !list.asList().isEmpty()) {
                               if (list.asList().resolve(0).getType().equals(ModelType.PROPERTY)) {
                                   value = propBinding.getEntityAdapterForList().fromDMRPropertyList(list.asPropertyList());
                               } else {
                                   value = propBinding.getEntityAdapterForList().fromDMRList(list.asList());
                               }
                           }
                           else
                           {
                               value = new LinkedList();
                           }
                       }
                       else if ("java.util.Map".equals(propBinding.getJavaTypeName())) {
                           // Only Map<String, String> is supported!
                           Map<String, String> map = new HashMap<>();
                           if (propValue.isDefined() && !propValue.asPropertyList().isEmpty()) {
                               for (Property property : propValue.asPropertyList()) {
                                   map.put(property.getName(), property.getValue().asString());
                               }
                           }
                           value = map;
                       }*/

        else {
            throw new RuntimeException("Unsupported java type: "+propertyType.getName());
        }
        method.invoke(entity, value);
    }


}
