package org.wildfly.apigen.invocation;

import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.MethodInfo;

import java.lang.reflect.Method;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Lance Ball
 */
class SubresourceFilter {
    private final Comparator<Method> comparator;
    private final Class<?> parentClass;
    private final Index index;

    public SubresourceFilter(Class<?> parentClass) {
        this.parentClass = parentClass;
        this.index = IndexFactory.createIndex(parentClass);
        switch (this.parentClass.getName()) {
            case "org.wildfly.swarm.config.logging.Logging":
            case "org.wildfly.swarm.config.logging.Logging$LoggingResources":
            case "org.wildfly.apigen.test.invocation.logging.Logging":
            case "org.wildfly.apigen.test.invocation.logging.Logging$LoggingResources":
            case "org.wildfly.swarm.config.logging.subsystem.loggingProfile.LoggingProfile":
            case "org.wildfly.swarm.config.logging.subsystem.loggingProfile.LoggingProfile$LoggingResources":
            case "org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.LoggingProfile": {
                this.comparator = new LoggingComparator();
                break;
            }
            default: this.comparator = new DefaultComparator();
        }
    }

    public List<Method> invoke() throws NoSuchMethodException {
        ArrayList methods = new ArrayList();
        ClassInfo clazz = index.getClassByName(DotName.createSimple(parentClass.getName()));
        for (MethodInfo method : clazz.methods()) {
            if (method.hasAnnotation(IndexFactory.SUBRESOURCE_META)) {
                methods.add(parentClass.getMethod(method.name()));
            }
        }
        Collections.sort(methods, comparator);
        return methods;
    }

    private class LoggingComparator implements Comparator<Method> {
        @Override
        public int compare(Method o1, Method o2) {
            return o1.getName().contains("Handler") ? 1 : -1;
        }
    }

    private class DefaultComparator implements Comparator<Method> {

        @Override
        public int compare(Method o1, Method o2) {
            return Collator.getInstance().compare(o1.getName(), o2.getName());
        }
    }
}
