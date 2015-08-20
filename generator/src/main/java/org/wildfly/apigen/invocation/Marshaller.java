package org.wildfly.apigen.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.MethodInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Lance Ball
 */
public class Marshaller {

    public static LinkedList<ModelNode> marshal(Object root) throws Exception {
        return appendNode(root, new LinkedList<ModelNode>());
    }

    private static HashMap<Class<?>, EntityAdapter<?>> adapters = new HashMap<>();
    private static HashMap<Class<?>, Optional<Subresource>> subresources = new HashMap<>();

    private static LinkedList<ModelNode> appendNode(Object node, LinkedList<ModelNode> list) throws Exception {
        EntityAdapter adapter = adapterFor(node.getClass());
        list.add(adapter.fromEntity(node));
        return marshalSubresources(node, list);
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

    private static LinkedList<ModelNode> marshalSubresources(Object parent, LinkedList<ModelNode> list) {
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
                            appendNode(o, list);
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

