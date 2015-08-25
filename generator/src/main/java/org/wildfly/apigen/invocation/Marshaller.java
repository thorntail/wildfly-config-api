package org.wildfly.apigen.invocation;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.dmr.ModelNode;
import org.jboss.jandex.*;
import org.wildfly.apigen.model.AddressTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;

/**
 * @author Lance Ball
 */
public class Marshaller {

    private static HashMap<Class<?>, EntityAdapter<?>> adapters = new HashMap<>();
    private static HashMap<Class<?>, Optional<Subresource>> subresources = new HashMap<>();

    public static LinkedList<ModelNode> marshal(Object root) throws Exception {
        return appendNode(root, PathAddress.EMPTY_ADDRESS, new LinkedList<>());
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<ModelNode> appendNode(Object entity, PathAddress address, LinkedList<ModelNode> list) throws Exception {
        final PathAddress resourceAddress = resourceAddress(entity, address);
        list.add(addressNodeFor(resourceAddress));
        EntityAdapter adapter = adapterFor(entity.getClass());
        list.add(adapter.fromEntity(entity));
        return marshalSubresources(entity, resourceAddress, list);
    }

    private static PathAddress resourceAddress(Object resource, PathAddress pathAddress) {
        final Class<?> entityClass = resource.getClass();

        Index index = IndexFactory.createIndex(entityClass);
        ClassInfo clazz = index.getClassByName(DotName.createSimple(entityClass.getName()));

        for (AnnotationInstance annotation :  clazz.classAnnotations()) {
            if (annotation.name().equals(IndexFactory.ADDRESS_META)) {
                AddressTemplate address = AddressTemplate.of(annotation.value().asString());
                pathAddress = pathAddress.append(address.getResourceType(), address.getResourceName());
                return pathAddress;
            }
        }
        throw new RuntimeException("");
    }

    private static ModelNode addressNodeFor(PathAddress address) {
        ModelNode node = new ModelNode();
        node.get(OP_ADDR).set(address.toModelNode());
        node.get(OP).set(ADD);
        return node;
    }

    private static synchronized EntityAdapter adapterFor(Class<?> type) {
        if (!adapters.containsKey(type)) {
            adapters.put(type, new EntityAdapter<>(type));
        }
        return adapters.get(type);
    }

    public static synchronized Optional<Subresource> subresourcesFor(Object entity) {
        Class<?> type = entity.getClass();
        if (!subresources.containsKey(type)) {
            try {
                Method target = type.getMethod("subresources");
                subresources.put(type, Optional.of(new Subresource(target.getReturnType(), target, entity)));
            } catch (Exception e) {
                // If no subresources() method, then no subresources exist
                subresources.put(type, Optional.empty());
            }
        }
        return subresources.get(type);
    }

    private static LinkedList<ModelNode> marshalSubresources(Object parent, PathAddress address, LinkedList<ModelNode> list) {
        try {
            Optional<Subresource> optional = subresourcesFor(parent);

            if (optional.isPresent()) {
                Class<?> subresourceType = optional.get().type;
                Object subresources = optional.get().invoke();

                // Index the annotations on the subresources type
                Index index = IndexFactory.createIndex(subresourceType);
                ClassInfo clazz = index.getClassByName(DotName.createSimple(subresourceType.getName()));
                for (MethodInfo method : clazz.methods()) {
                    if (method.hasAnnotation(IndexFactory.SUBRESOURCE_META)) {
                        Method subresourceMethod = subresourceType.getMethod(method.name());
                        List<?> resourceList = (List<?>) subresourceMethod.invoke(subresources);

                        for (Object o : resourceList) {
                            appendNode(o, address, list);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting subresources for " + parent.getClass().getSimpleName());
        }
        return list;
    }

    private static class Subresource {
        public final Class<?> type;
        public final Method method;
        private final Object parent;

        public Subresource(Class<?> type, Method method, Object parent) {
            this.type = type;
            this.method = method;
            this.parent = parent;
        }

        public Object invoke() throws InvocationTargetException, IllegalAccessException {
            return method.invoke(parent);
        }

    }
}

