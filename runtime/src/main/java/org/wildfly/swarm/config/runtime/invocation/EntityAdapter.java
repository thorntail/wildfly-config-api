package org.wildfly.swarm.config.runtime.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.jandex.*;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Adopts DMR to Entity T and vice versa.
 */
public class EntityAdapter<T> {

    private Class<?> type;
    private Index index;

    public EntityAdapter(Class<?> type) {
        this.type = type;
        this.index = IndexFactory.createIndex(type);
    }

    private Class<?> getType() {
        return type;
    }

    public Index getIndex() { return index; }

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

    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public T fromDMR(String keyValue, ModelNode modelNode) throws Exception {

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

        ClassInfo clazz = index.getClassByName(DotName.createSimple(getType().getCanonicalName()));

        T entity = null;
        boolean implicitKey = clazz.annotations().containsKey(IndexFactory.IMPLICIT_META);

        if(implicitKey)
        {
            // implicit (aka singleton) resource
            Constructor<?> ctor = getType().getConstructor();
            entity = (T) ctor.newInstance();
        }
        else
        {
            // explicit (regular) resource
            entity = (T) getType().getConstructor(String.class)
                    .newInstance(keyValue);
        }


        for (MethodInfo method : clazz.methods()) {
            if (method.hasAnnotation(IndexFactory.BINDING_META)) {

                Method getter = entity.getClass().getMethod(method.name());
                Class<?> propertyType = getter.getReturnType();

                ModelNodeBinding binding = getter.getDeclaredAnnotation(ModelNodeBinding.class);
                String detypedName = binding.detypedName();

                ModelNode dmrPayload = actualPayload.get(detypedName);


                // EXPRESSIONS
                /*if(propBinding.doesSupportExpression())
                {
                    if(propValue.isDefined()
                            && propValue.getType() == ModelType.EXPRESSION)
                    {
                        String exprValue = actualPayload.resolve(propBinding.getDetypedName()).asString();

                        ExpressionAdapter.setExpressionValue(entity, propBinding.getJavaName(), exprValue);

                        continue; // expression have precedence over real values

                    }
                }*/


                // VALUES
                ModelType dmrType = Types.resolveModelType(propertyType);
                if (dmrType == ModelType.LIST) {
                    new ListTypeAdapter().fromDmr(entity, method.name(), dmrType, propertyType, dmrPayload);
                } else if (dmrType == ModelType.OBJECT) {
                    new MapTypeAdapter().fromDmr(entity, method.name(), dmrType, propertyType, dmrPayload);
                } else {
                    new SimpleTypeAdapter().fromDmr(entity, method.name(), dmrType, propertyType, dmrPayload);
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
    @SuppressWarnings("unchecked")
    public ModelNode fromEntity(T entity) throws Exception {
        return fromEntity(entity, new ModelNode());
    }

    public ModelNode fromEntity(T entity, ModelNode modelNode) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ClassInfo clazz = null;

        Class<?> currentType = getType();

        while (clazz == null ) {
            clazz = index.getClassByName(DotName.createSimple(currentType.getCanonicalName()));

            if ( clazz == null ) {
                currentType = currentType.getSuperclass();
                if ( currentType == null ) {
                    throw new RuntimeException( "Unable to determine ClassInfo" );
                }
            }
        }

        while ( clazz != null ) {
            for (MethodInfo method : clazz.methods()) {

                if (method.hasAnnotation(IndexFactory.BINDING_META)) {

                    Method target = entity.getClass().getMethod(method.name());
                    Class<?> propertyType = target.getReturnType();
                    Object propertyValue = target.invoke(entity);

                    AnnotationInstance ann = method.annotation(DotName.createSimple(ModelNodeBinding.class.getName()));
                    org.jboss.jandex.AnnotationValue annValue = ann.value("detypedName");
                    String detypedName = annValue.asString();

                    // VALUES
                    if (propertyValue != null) {
                        try {
                            ModelType dmrType = Types.resolveModelType(propertyType);

                            if (dmrType == ModelType.LIST) {
                                new ListTypeAdapter().toDmr(modelNode, detypedName, (List) propertyValue);

                            } else if (dmrType == ModelType.OBJECT) {
                                // only Map<String,String> supported
                                new MapTypeAdapter().toDmr(modelNode, detypedName, (Map) propertyValue);

                            } else {
                                new SimpleTypeAdapter().toDmr(modelNode, detypedName, dmrType, propertyValue);
                            }
                        } catch (RuntimeException e) {
                            throw new RuntimeException("Failed to adopt value " + propertyType.getName(), e);
                        }
                    }

                }
            }
            if ( currentType.getSuperclass() != null ) {
                currentType = currentType.getSuperclass();
                if ( currentType.equals( Object.class ) ) {
                    break;
                }
            }
            clazz = index.getClassByName( DotName.createSimple( currentType.getCanonicalName() ));
        }
        return modelNode;
    }
}

