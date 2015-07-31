package org.wildfly.apigen.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import java.util.List;

/**
 * @author Heiko Braun
 * @since 31/07/15
 */
public class SimpleTypeAdapter {

    public void toDmr(ModelNode target, String detypedName, ModelType dmrType, Object value)
    {
        setValueOn(target.get(detypedName), dmrType, value);
    }

    public static ModelNode setValueOn(ModelNode target, ModelType type, Object propValue)
    {
        if(type.equals(ModelType.STRING))
        {
            target.set((String) propValue);
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
        else if(type.equals(ModelType.LIST))
        {
            target.setEmptyList();
            List list = (List)propValue;

            for(Object item : list)
                target.add(String.valueOf(item));
        }
        else
        {
            throw new RuntimeException("Type conversion not implemented for "+type);
        }

        return target;
    }

    public static ModelNode addValueTo(ModelNode target, ModelType type, Object propValue)
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
            throw new RuntimeException("Type conversion not implemented for "+type);
        }

        return target;
    }
}
