package org.wildfly.swarm.config.runtime.invocation;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.Addresses;
import org.wildfly.swarm.config.runtime.model.AddressTemplate;

/**
 * @author Heiko Braun
 * @since 29/10/15
 */
public class Addressing {
    public static AddressTemplate of(Class<?> entity) {
        Index index = IndexFactory.createIndex(entity);
        ClassInfo clazz = index.getClassByName(DotName.createSimple(entity.getCanonicalName()));

        AddressTemplate addr = null;

        for (AnnotationInstance annotation : clazz.classAnnotations()) {
            if (annotation.name().equals(IndexFactory.ADDRESS_META)) {
                Address meta = entity.getAnnotation(Address.class);
                addr = AddressTemplate.of(meta.value());
            }
            else if (annotation.name().equals(IndexFactory.ADDRESSES_META)) {
                Addresses meta = entity.getAnnotation(Addresses.class);
                StringBuffer sb = new StringBuffer("\n");
                for (String s : meta.value()) {
                    sb.append(s).append("\n");
                }
                throw new RuntimeException("Ambiguous resource reference on class "+entity +": "+ sb.toString());
            }

        }

        if(null==addr)
            throw new RuntimeException("Missing resource reference on class "+entity);

        return addr;
    }
}
