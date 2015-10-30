package org.wildfly.swarm.config.runtime.invocation;

import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;
import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.Addresses;
import org.wildfly.swarm.config.runtime.Implicit;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;
import org.wildfly.swarm.config.runtime.Subresource;
import org.wildfly.swarm.config.runtime.ResourceType;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @author Lance Ball
 */
public class IndexFactory {
    public final static DotName IMPLICIT_META = DotName.createSimple(Implicit.class.getCanonicalName());
    public final static DotName BINDING_META = DotName.createSimple(ModelNodeBinding.class.getCanonicalName());
    public final static DotName ADDRESS_META = DotName.createSimple(Address.class.getCanonicalName());
    public final static DotName ADDRESSES_META = DotName.createSimple(Addresses.class.getCanonicalName());
    public final static DotName RESOURCE_TYPE = DotName.createSimple(ResourceType.class.getCanonicalName());
    public final static DotName SUBRESOURCE_META = DotName.createSimple(Subresource.class.getCanonicalName());

    /**
     * Creates an annotation index for the given entity type
     */
    public synchronized static Index createIndex(Class<?> type) {
        Index index = indices.get(type);
        if (index == null) {
            try {
                Indexer indexer = new Indexer();
                Class<?> currentType = type;
                while ( currentType != null ) {
                    String className = currentType.getName().replace(".", "/") + ".class";
                    InputStream stream = type.getClassLoader()
                            .getResourceAsStream(className);
                    indexer.index(stream);
                    currentType = currentType.getSuperclass();
                }
                index = indexer.complete();
                indices.put(type, index);
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize Indexer", e);
            }
        }
        return index;
    }

    private static final HashMap<Class<?>, Index> indices = new HashMap<>();
}
