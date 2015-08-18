package org.wildfly.apigen.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.MethodInfo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lance Ball
 */
public class Marshaller {

    public static LinkedList<ModelNode> marshal(Object root) throws Exception {
        return appendNode(root, new LinkedList<ModelNode>());
    }

    private static HashMap<Class<?>, EntityAdapter<?>> adapters = new HashMap<>();

    private static LinkedList<ModelNode> appendNode(Object node, LinkedList<ModelNode> list) throws Exception {
        EntityAdapter adapter = getEntityAdapter(node.getClass());
        list.add(adapter.fromEntity(node));
        return marshalSubresources(node, list);
    }

    private static synchronized EntityAdapter getEntityAdapter(Class<?> type) {
        if (!adapters.containsKey(type)) {
            adapters.put(type, new EntityAdapter<>(type));
        }
        return adapters.get(type);
    }

    private static LinkedList<ModelNode> marshalSubresources(Object parent, LinkedList<ModelNode> list) {
        try {
            Method target = parent.getClass().getMethod("subresources");
            Class<?> subresourceType = target.getReturnType();
            Object subresources = target.invoke(parent);

            // Index the annotations on the subresources type
            Index index = IndexFactory.createIndex(subresourceType);
            ClassInfo clazz = index.getClassByName(DotName.createSimple(subresourceType.getName()));
            for (MethodInfo method : clazz.methods()) {
                if (method.hasAnnotation(IndexFactory.SUBRESOURCE_META)) {
                    Method subresourceMethod = subresourceType.getMethod(method.name());
                    Class<?> propertyType = subresourceMethod.getReturnType();
                    Object propertyValue = subresourceMethod.invoke(subresources);
                    List<?> resourceList = (List<?>) propertyValue;

                    for (Object o : resourceList) {
                        appendNode(o, list);
                        marshalSubresources(o, list);
                    }
                }
            }

        } catch (Exception e) {
            // TODO: Is this the best way to determine whether a
            // class contains subresources? Probably not.
            System.err.println(e);
            e.printStackTrace();
        }
        return list;
    }
}

