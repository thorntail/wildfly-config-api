package org.wildfly.apigen.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;
import org.jboss.jandex.MethodInfo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Adopts DMR to Entity T and vice versa.
 */
public class EntityAdapter<T> {

    private final static DotName BINDING_META = DotName.createSimple(Binding.class.getCanonicalName());
    private final static DotName ADDRESS_META = DotName.createSimple(Address.class.getCanonicalName());
    private final static DotName SUBRESOURCE_META = DotName.createSimple(Subresource.class.getCanonicalName());

    private final Class<?> type;
    private Index index;

    public EntityAdapter(Class<?> type) {
        this.type = type;

        createIndex();

    }

    /**
     * Creates an annotation index for the given entity T
     */
    private void createIndex() {
        try {
            Indexer indexer = new Indexer();
            String className = getType().getCanonicalName().replace(".","/") + ".class";

            InputStream stream = getType().getClassLoader()
                    .getResourceAsStream(className);
            indexer.index(stream);
            index = indexer.complete();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Indexer", e);
        }

    }

    private Class<?> getType() {
        return type;
    }

    /**
     * Determine if this is an EntityAdapter for a one of the supported ModelNode
     * base classes (String, Long, BigDecimal, etc).
     *
     * @return <code>true</code> if this is a base class EntityAdapter, <code>false</code> otherwise.
     */
    public boolean isBaseTypeAdapter() {
        return isBaseType(getType());
    }

    /**
     * Is the given class one of the supported ModelNode base classes (String, Long, BigDecimal, etc.)
     *
     * @param clazz The class to be tested.
     * @return <code>true</code> if the given class is a base class, <code>false</code> otherwise.
     */
    public boolean isBaseType(Class<?> clazz) {
        return (clazz == String.class) ||
                (clazz == Long.class) ||
                (clazz == Integer.class) ||
                (clazz == Boolean.class) ||
                (clazz == Double.class) ||
                (clazz == BigDecimal.class) ||
                (clazz == byte[].class);
    }

    private T convertToBaseType(ModelNode dmr) {
        if (getType() == String.class) return (T)dmr.asString();
        if (getType() == Long.class) return (T)Long.valueOf(dmr.asLong());
        if (getType() == Integer.class) return (T)Integer.valueOf(dmr.asInt());
        if (getType() == Boolean.class) return (T)Boolean.valueOf(dmr.asBoolean());
        if (getType() == Double.class) return (T)Double.valueOf(dmr.asDouble());
        if (getType() == BigDecimal.class) return (T)BigDecimal.valueOf(dmr.asDouble());
        if (getType() == byte[].class) return (T)dmr.asBytes();

        throw new IllegalArgumentException("Can not convert. This node is not of a base type. Actual type is " + type.getName());
    }

    /**
     * Converts a DMR {@link ModelNode} to a java entity of type T.
     *
     * @param modelNode a ModelNode
     * @return an entity representation of type T
     */
    public T fromDMR(ModelNode modelNode) throws Exception {

        if (isBaseTypeAdapter()) return convertToBaseType(modelNode);

        ModelNode actualPayload = null;

        if(ModelType.OBJECT.equals(modelNode.getType()))
        {
            actualPayload = modelNode;
        }
       /* else if(ModelType.PROPERTY.equals(modelNode.getType()))
        {
            final Property property = modelNode.asProperty();
            actualPayload = property.getValue();
        }*/
        else
        {
            throw new IllegalArgumentException("Unsupported ModelType "+modelNode.getType()+": "+modelNode);
        }

        T entity = (T) getType().newInstance();
        ClassInfo clazz = index.getClassByName(DotName.createSimple(getType().getCanonicalName()));

        for (MethodInfo method : clazz.methods()) {

            if (method.hasAnnotation(BINDING_META)) {

                Method getter = entity.getClass().getMethod(method.name());
                Class<?> propertyType = getter.getReturnType();

                Binding binding = getter.getDeclaredAnnotation(Binding.class);
                String detypedName = binding.detypedName();

                String javaName = method.name().replace("get", "set");

                ModelNode dmrPayload = actualPayload.get(detypedName);


                // EXPRESSIONS
                /*if(propBinding.doesSupportExpression())
                {
                    if(propValue.isDefined()
                            && propValue.getType() == ModelType.EXPRESSION)
                    {
                        String exprValue = actualPayload.get(propBinding.getDetypedName()).asString();

                        ExpressionAdapter.setExpressionValue(entity, propBinding.getJavaName(), exprValue);

                        continue; // expression have precedence over real values

                    }
                }*/


                // VALUES
                ModelType dmrType = Types.resolveModelType(propertyType);

                if (dmrType == ModelType.LIST) {
                    new ListTypeAdapter().fromDmr(entity, javaName, dmrType, propertyType, dmrPayload);
                } else if (dmrType == ModelType.OBJECT) {
                    new MapTypeAdapter().fromDmr(entity, javaName, dmrType, propertyType, dmrPayload);
                } else {
                    new SimpleTypeAdapter().fromDmr(entity, javaName, dmrType, propertyType, dmrPayload);
                }


            }
        }
        return entity;
    }

    /**
     * Converts an entity of type T into a DMR {@link ModelNode}
     *
     * @param entity
     * @return ModelNode
     */
    public ModelNode fromEntity(T entity) throws Exception {

        ModelNode modelMode = new ModelNode();

        ClassInfo clazz = index.getClassByName(DotName.createSimple(getType().getCanonicalName()));
        for (MethodInfo method : clazz.methods()) {

            if (method.hasAnnotation(BINDING_META)) {

                Method target = entity.getClass().getMethod(method.name());
                Class<?> propertyType = target.getReturnType();
                Object propertyValue = target.invoke(entity);

                Binding binding = target.getDeclaredAnnotation(Binding.class);
                String detypedName = binding.detypedName();


            /*// EXPRESSIONS
            if(property.doesSupportExpression())
            {
                String exprValue = ExpressionAdapter.getExpressionValue(
                        entity, property.getJavaName()
                );

                if(exprValue!=null)
                {
                    modelMode.get(detypedName).setExpression(exprValue);
                    continue; // expression have precedence over real values
                }
            }*/

                // VALUES
                if (propertyValue != null) {
                    try {
                        ModelType dmrType = Types.resolveModelType(propertyType);

                        if (dmrType == ModelType.LIST) {
                            new ListTypeAdapter().toDmr(modelMode, detypedName, (List) propertyValue);

                        } else if (dmrType == ModelType.OBJECT) {
                            // only Map<String,String> supported
                            new MapTypeAdapter().toDmr(modelMode, detypedName, (Map) propertyValue);

                        } else {
                            new SimpleTypeAdapter().toDmr(modelMode, detypedName, dmrType, propertyValue);
                        }
                    } catch (RuntimeException e) {
                        throw new RuntimeException("Failed to adopt value " + propertyType.getName(), e);
                    }
                }

            }

        }

        return modelMode;
    }
}

