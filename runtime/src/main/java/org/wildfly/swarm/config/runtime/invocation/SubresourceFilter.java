package org.wildfly.swarm.config.runtime.invocation;

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
        String name = this.parentClass.getName();
        if ( name.startsWith( "org.wildfly.swarm.config.Logging" ) || name.startsWith( "org.wildfly.swarm.config.logging" ) ) {
            this.comparator = new LoggingComparator();
        } else {
            this.comparator = new DefaultComparator();
        }

    }

    public List<Method> invoke() throws NoSuchMethodException {
        return __invoke(parentClass, index, comparator);
    }

    private static List<Method> __invoke(Class<?> clazz, Index index, Comparator<Method> comparator) throws NoSuchMethodException {
        ArrayList methods = new ArrayList();
        ClassInfo clazzInfo = index.getClassByName(DotName.createSimple(clazz.getName()));
        for (MethodInfo method : clazzInfo.methods()) {
            if (method.hasAnnotation(IndexFactory.SUBRESOURCE_META)) {
                methods.add(clazz.getMethod(method.name()));
            }
        }
        if (clazzInfo.superName() != null && clazz.getSuperclass() != java.lang.Object.class) {
            index = IndexFactory.createIndex(clazz.getSuperclass());
            return __invoke(clazz.getSuperclass(), index, comparator);
        }
        Collections.sort(methods, comparator);
        return methods;
    }

    static class LoggingComparator implements Comparator<Method> {
        @Override
        public int compare(Method o1, Method o2) {
            if (o1.getName().contains("Formatter") ) return -1;
            if (o2.getName().contains("Formatter") ) return 1;
            if ( o1.getName().equals( "loggers" ) || o1.getName().equals( "rootLogger" )) return 1;
            return -1;
        }
    }

    private static class DefaultComparator implements Comparator<Method> {

        @Override
        public int compare(Method o1, Method o2) {
            return Collator.getInstance().compare(o1.getName(), o2.getName());
        }
    }
}
