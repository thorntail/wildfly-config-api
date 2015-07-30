package org.wildfly.apigen.invocation;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.dmr.Property;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;
import org.jboss.jandex.MethodInfo;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import org.wildfly.apigen.invocation.Subresource;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

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

    private void createIndex() {
        try {
            Indexer indexer = new Indexer();
            String className = getType().getCanonicalName().replace(".","/") + ".class";

            InputStream stream = getType().getClassLoader()
                    .getResourceAsStream(className);
            indexer.index(stream);
            index = indexer.complete();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize EntityAdapter", e);
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
     * The ModelNode can be of any type supported by ModelType except BigInteger.
     * Typically it's just the payload of a DMR response (ModelNode.get(RESULT))
     *
     * @param dmr  a ModelNode
     * @return an entity representation of type T
     */
    /*public T fromDMR(ModelNode dmr) {

        if (isBaseTypeAdapter()) return convertToBaseType(dmr);

        ModelNode actualPayload = null;
        EntityFactory<?> factory = metaData.getFactory(getType());

        if(null==factory)
            throw new IllegalArgumentException("No factory method for " + getType());

        T entity = (T) factory.create();

        KeyAssignment keyDelegation = null;

        if(ModelType.OBJECT.equals(dmr.getType()))
        {
            actualPayload = dmr;
        }
        else if(ModelType.PROPERTY.equals(dmr.getType()))
        {
            final Property property = dmr.asProperty();

            keyDelegation = new KeyAssignment() {
                @Override
                public Object valueForKey(String key) {

                    Object resolvedValue = null;

                    // use delegate
                    if(keyAssignment!=null)
                        resolvedValue = keyAssignment.valueForKey(key);

                    // if delegate fails, fallback to property name
                    if(null==resolvedValue)
                        resolvedValue = property.getName();

                    return resolvedValue;
                }
            };

            actualPayload = property.getValue();
        }
        else
        {
            throw new IllegalArgumentException("Unknown ModelType "+dmr.getType()+": "+dmr);
        }

        BeanMetaData beanMetaData = metaData.getBeanMetaData(getType());
        Mutator mutator = metaData.getMutator(getType());

        //final List<ModelNode> filteredDMRNames = actualPayload.hasDefined("_filtered-attributes") ?
        //                actualPayload.get("_filtered-attributes").asList() : Collections.EMPTY_LIST;

        //final Set<String> filteredJavaNames = new HashSet<String>(filteredDMRNames.size());
        //final Set<String> readonlyJavaNames = new HashSet<String>();

        for(PropertyBinding propBinding : beanMetaData.getProperties())
        {

            // RBAC: We need turn the filtered dmr names into java property names to compatible with the ballroom Form API
            *//*for(ModelNode item : filteredDMRNames)
            {
                if(item.asString().equals(propBinding.getDetypedName()))
                {
                    filteredJavaNames.add(propBinding.getJavaName());
                    break;
                }
            } *//*

            // RBAC: read-only attributes
            //if(!securityContext.getAttributeWritePriviledge(propBinding.getDetypedName()).isGranted())
            //    readonlyJavaNames.add(propBinding.getJavaName());



            String[] splitDetypedName = propBinding.getDetypedName().split("/");
            ModelNode propValue = actualPayload.get(splitDetypedName);
            Object value = null;

            try
            {


                *//**
     * EXPRESSIONS
     *//*

                if(propBinding.doesSupportExpression())
                {
                    if(propValue.isDefined()
                            && propValue.getType() == ModelType.EXPRESSION)
                    {
                        String exprValue = actualPayload.get(propBinding.getDetypedName()).asString();

                        ExpressionAdapter.setExpressionValue(entity, propBinding.getJavaName(), exprValue);

                        continue; // expression have precedence over real values

                    }
                }

                *//**
     * KEYS
     *//*

                if(propBinding.isKey())
                {
                    // key resolution strategy:
                    // a, external KeyAssignment with fallback to property name (for property types)
                    // b, external KeyAssignment
                    // c, resolution of a matching property
                    // d, failure

                    if(keyDelegation!=null)
                    {
                        value = keyDelegation.valueForKey(propBinding.getJavaName());
                    }
                    else if(keyAssignment!=null)
                    {
                        // typically keys are
                        value = keyAssignment.valueForKey(propBinding.getJavaName());
                    }
                    else if(dmr.hasDefined(propBinding.getDetypedName()))
                    {
                        // keys are required to be strings (part of the address..)
                        value = actualPayload.get(propBinding.getDetypedName()).asString();
                    }
                    else
                    {
                        Log.warn("Key property declared, but no key assignment available: " + propBinding);
                    }
                }

                *//**
     * VALUES
     *//*

                else if("java.lang.Boolean".equals(propBinding.getJavaTypeName()))
                {
                    if(propValue.isDefined())
                        value = propValue.asBoolean();
                    else
                        value = false;
                }
                else if("java.lang.Long".equals(propBinding.getJavaTypeName()))
                {
                    if(propValue.isDefined())
                        value = propValue.asLong();
                    else
                        // need to make sure to use the proper type otherwise ClassCastExceptions occur down the line (after boxing)
                        value = null;
                }
                else if("java.lang.Integer".equals(propBinding.getJavaTypeName()))
                {
                    if(propValue.isDefined())
                        value = propValue.asInt();
                    else
                        value = null;
                }
                else if("java.lang.Double".equals(propBinding.getJavaTypeName()))
                {
                    if(propValue.isDefined())
                        value = propValue.asDouble();
                    else
                        value = null;
                }
                else if("java.lang.Float".equals(propBinding.getJavaTypeName()))
                {
                    if(propValue.isDefined())
                        value = propValue.asDouble();
                    else
                        value = null;
                }
                else if("java.lang.String".equals(propBinding.getJavaTypeName()))
                {
                    // default
                    if(propValue.isDefined())
                        value = propValue.asString();
                    else
                        value = "";
                }
                else if ("java.util.List".equals(propBinding.getJavaTypeName()))
                {
                    ModelNode list = actualPayload.get(splitDetypedName);
                    if (list.isDefined() && propValue.isDefined() && !list.asList().isEmpty()) {
                        if (list.asList().get(0).getType().equals(ModelType.PROPERTY)) {
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
                }

                // invoke the mutator
                if(value!=null)
                    mutator.setValue(entity, propBinding.getJavaName(), value);

            }
            catch (RuntimeException e)
            {
                //  System.out.println("Error on property binding: '"+propBinding.toString()+"'");
                //  System.out.println(dmr);
                throw e;
            }


        }

        // pass the RBAC meta data along for further Form processing
        // see Form#edit() ...

        //RBACAdapter.setFilteredAttributes(entity, filteredJavaNames);
        //RBACAdapter.setReadOnlyAttributes(entity, readonlyJavaNames);

        return entity;
    }*/

    /**
     * Parse a ModelNode of type ModelType.List<p/>
     * Basically calls {@link #fromDMR(org.jboss.dmr.client.ModelNode)} for each item.
     *
     * @param dmr a ModelNode
     * @return a list of entities of type T
     */
   /* public List<T> fromDMRList(List<ModelNode> dmr) {

        List<T> entities = new LinkedList<T>();

        for(ModelNode item : dmr)
        {
            T entity = fromDMR(item);
            entities.add(entity);
        }

        return entities;
    }*/

  /*  public List<PropertyRecord> fromDMRPropertyList(List<Property> dmr) {
        List<PropertyRecord> entities = new LinkedList<PropertyRecord>();

        for (Property prop : dmr) {
            PropertyRecord property = propertyRecordFactory.create();
            property.setKey(prop.getName());
            property.setValue(prop.getValue().asString());
            entities.add(property);
        }

        return entities;
    }*/

    /**
     * Create a plain DMR representation of an entity.
     * Plain means w/o the address and operation property.
     *
     * @param entity
     * @return
     */
    @SuppressWarnings("unchecked")
    public ModelNode fromEntity(T entity) throws Exception {

        ModelNode operation = new ModelNode();

        ClassInfo clazz = index.getClassByName(DotName.createSimple(getType().getCanonicalName()));
        for (MethodInfo method : clazz.methods()) {

            if (method.hasAnnotation(BINDING_META)) {

                Method target = entity.getClass().getMethod(method.name());
                Class<?> returnType = target.getReturnType();
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
                    operation.get(detypedName).setExpression(exprValue);
                    continue; // expression have precedence over real values
                }
            }*/

                // VALUES
                if (propertyValue != null) {
                    try {
                        ModelType modelType = resolveModelType(returnType.getName());

                        if (modelType == ModelType.LIST) {

                            System.out.println("unsupported type "+ modelType);
                            // TODO
                            /*operation.get(detypedName).set(modelType,
                                    property.getEntityAdapterForList().fromEntityList((List) propertyValue));*/
                        } else if (modelType == ModelType.OBJECT) {

                            System.out.println("unsupported type "+ modelType);
                            // TODO
                            // Only Map<String, String> is supported!
                            /*Map<String, String> map = (Map<String, String>) propertyValue;
                            for (Map.Entry<String, String> entry : map.entrySet()) {
                                operation.get(detypedName).get(entry.getKey()).set(entry.getValue());
                            }*/
                        } else {
                            set(operation.get(detypedName), modelType, propertyValue);
                        }
                    } catch (RuntimeException e) {
                        throw new RuntimeException("Failed to get value " + returnType.getName(), e);
                    }
                }

            }

        }

        return operation;
    }

    public ModelNode set(ModelNode target, ModelType type, Object propValue)
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

    private ModelType resolveModelType(String javaTypeName) {

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
        else if("java.util.List".equals(javaTypeName))
            type = ModelType.LIST;
        else if("java.util.Map".equals(javaTypeName))
            type = ModelType.OBJECT;
        else {
            throw new RuntimeException("Failed to resolve ModelType for '"+ javaTypeName+"'");
        }

        return type;
    }

    public ModelNode fromBaseTypeList(List<?> baseTypeValues, Class<?> baseType) {
        ModelNode node = new ModelNode();
        if (baseTypeValues.isEmpty()) {
            node.setEmptyList();
            return node;
        }

        for (Object obj : baseTypeValues) {
            if (baseType == String.class) {
                node.add((String)obj);
            } else if (baseType == Long.class) {
                node.add((Long)obj);
            } else if (baseType == Integer.class) {
                node.add((Integer)obj);
            } else if (baseType == Boolean.class) {
                node.add((Boolean)obj);
            } else if (baseType == Double.class) {
                node.add((Double)obj);
            } else if (baseType == BigDecimal.class) {
                node.add((BigDecimal)obj);
            } else if (baseType == byte[].class) {
                node.add((byte[])obj);
            } else {
                throw new IllegalArgumentException("Can not convert. This value is not of a recognized base type. Value =" + obj.toString());
            }
        }
        return node;
    }

    /**
     * Creates a composite operation to create entities.
     * Basically calls {@link #fromEntity(Object)}
     *
     * @param entities
     * @return a composite ModelNode structure
     */
    /*public ModelNode fromEntityList(List<T> entities)
    {
        ModelNode operation = new ModelNode();
        operation.get(OP).set(COMPOSITE);
        operation.get(ADDRESS).setEmptyList();

        List<ModelNode> steps = new ArrayList<ModelNode>();

        for(T entity : entities)
        {
            steps.add(fromEntity(entity));
        }

        operation.get(STEPS).set(steps);
        return operation;
    }*/

    /*public ModelNode fromEntityPropertyList(List<PropertyRecord> entities)
    {
        ModelNode propList = new ModelNode();
        for (PropertyRecord prop : entities) {
            propList.add(prop.getKey(), prop.getValue());
        }
        return propList;
    }*/

    /**
     * Turns a changeset into a composite write attribute operation.
     *
     * @return composite operation
     */
   /* public ModelNode fromChangeset(Map<String, Object> changeSet, ModelNode address, ModelNode... extraSteps)
    {

        ModelNode protoType = new ModelNode();
        protoType.get(ADDRESS).set(address.get(ADDRESS));
        protoType.get(OP).set(WRITE_ATTRIBUTE_OPERATION);

        ModelNode operation = new ModelNode();
        operation.get(OP).set(COMPOSITE);
        operation.get(ADDRESS).setEmptyList();

        List<ModelNode> steps = new ArrayList<ModelNode>();

        List<PropertyBinding> propertyBindings = metaData.getBeanMetaData(type).getProperties();

        Map<String, ModelNode> flattenedSteps = new HashMap<String, ModelNode>();
        for(PropertyBinding binding : propertyBindings)
        {
            Object value = changeSet.get(binding.getJavaName());
            if (value == null) continue;

            ModelNode step = protoType.clone();

            // account for flattened sub-attribute paths
            String detypedName = binding.getDetypedName();
            String[] splitDetypedName = detypedName.split("/");

            step.get(NAME).set(splitDetypedName[0]);
            splitDetypedName[0] = VALUE;
            ModelNode nodeToSetValueUpon = step.get(splitDetypedName);

            if (binding.isFlattened()) { // unflatten
                String attributePath = detypedName.substring(0, detypedName.lastIndexOf("/"));
                ModelNode savedStep = flattenedSteps.get(attributePath);
                if (savedStep == null) {
                    setValue(binding, nodeToSetValueUpon, value);
                    flattenedSteps.put(attributePath, step);
                } else {
                    setValue(binding, savedStep.get(splitDetypedName), value);
                }

            } else {
                setValue(binding, nodeToSetValueUpon, value);
                steps.add(step);
            }
        }

        // add steps for flattened attributes
        for (ModelNode step : flattenedSteps.values()) steps.add(step);

        // add extra steps
        steps.addAll(Arrays.asList(extraSteps));

        operation.get(STEPS).set(steps);
        System.out.println(operation);
        return operation;
    }*/

    private void setValue(ModelNode nodeToSetValueUpon, Object value) {
        Class type = value.getClass();

       /* if(FormItem.VALUE_SEMANTICS.class == type) {

            // skip undefined form item values (FormItem.UNDEFINED.Value)
            // or persist as UNDEFINED
            if(value.equals(FormItem.VALUE_SEMANTICS.UNDEFINED))
            {
                nodeToSetValueUpon.set(ModelType.UNDEFINED);
            }

        }
        else*/ if(String.class == type)
        {

            String stringValue = (String) value;
            if(stringValue.startsWith("$"))     // TODO: further constraints
                nodeToSetValueUpon.setExpression(stringValue);
            else
                nodeToSetValueUpon.set(stringValue);
        }
        else if(Boolean.class == type)
        {
            nodeToSetValueUpon.set((Boolean)value);
        }
        else if(Integer.class == type)
        {
            nodeToSetValueUpon.set((Integer)value);
        }
        else if(Double.class == type)
        {
            nodeToSetValueUpon.set((Double)value);
        }
        else if (Long.class == type)
        {
            nodeToSetValueUpon.set((Long)value);
        }
        else if (Float.class == type)
        {
            nodeToSetValueUpon.set((Float)value);
        }
        else
        {
            throw new RuntimeException("Unsupported type: "+type);
        }
    }

   /* @SuppressWarnings("unchecked")
    private void setValue(PropertyBinding binding, ModelNode nodeToSetValueUpon, Object value) {
        final Class sourceType = value.getClass();

        if(FormItem.VALUE_SEMANTICS.class == sourceType) {

            // skip undefined form item values (FormItem.UNDEFINED.Value)
            // or persist as UNDEFINED
            if(value.equals(FormItem.VALUE_SEMANTICS.UNDEFINED)
                    && binding.isWriteUndefined())
            {
                nodeToSetValueUpon.set(ModelType.UNDEFINED);
            }

        }
        else if(String.class == sourceType)
        {

            String stringValue = (String) value;
            if(stringValue.startsWith("$"))     // TODO: further constraints
                nodeToSetValueUpon.setExpression(stringValue);
            else
                nodeToSetValueUpon.set(stringValue);
        }
        else if(Boolean.class == sourceType)
        {
            nodeToSetValueUpon.set((Boolean)value);
        }
        else if(Integer.class == sourceType)
        {
            nodeToSetValueUpon.set((Integer)value);
        }
        else if(Double.class == sourceType)
        {
            nodeToSetValueUpon.set((Double)value);
        }
        else if (Long.class == sourceType)
        {
            nodeToSetValueUpon.set((Long)value);
        }
        else if (Float.class == sourceType)
        {
            nodeToSetValueUpon.set((Float)value);
        }
        else if (binding.getListType() != null)
        {
            if (binding.getListType() == PropertyRecord.class) {
                nodeToSetValueUpon.set(fromEntityPropertyList((List)value));
            }  else if (isBaseType(binding.getListType())) {
                nodeToSetValueUpon.set(fromBaseTypeList((List)value, binding.getListType()));
            } else {
                nodeToSetValueUpon.set(fromEntityList((List)value));
            }
        }
        else if (Map.class.getName().equals(binding.getJavaTypeName())) {
            // Only Map<String, String> is supported!
            Map<String, String> map = (Map<String, String>) value;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nodeToSetValueUpon.get(entry.getKey()).set(entry.getValue());
            }
        }
        else
        {
            throw new RuntimeException("Unsupported type: "+sourceType);
        }
    }*/

    public static List<String> modelToList(ModelNode model, String elementName)
    {
        List<String> strings = new ArrayList<String>();

        if(model.hasDefined(elementName))
        {
            List<ModelNode> items = model.get(elementName).asList();
            for(ModelNode item : items)
                strings.add(item.asString());

        }

        return strings;
    }

    private static Set<String> toSet(List<ModelNode> nodeList)
    {
        final Set<String> s = new HashSet<String>(nodeList.size());
        for(ModelNode n : nodeList)
            s.add(n.asString());
        return s;
    }
}

