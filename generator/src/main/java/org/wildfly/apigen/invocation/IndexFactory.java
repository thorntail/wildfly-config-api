package org.wildfly.apigen.invocation;

import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Lance Ball
 */
public class IndexFactory {
    public final static DotName IMPLICIT_META = DotName.createSimple(Implicit.class.getCanonicalName());
    public final static DotName BINDING_META = DotName.createSimple(Binding.class.getCanonicalName());
    public final static DotName ADDRESS_META = DotName.createSimple(Address.class.getCanonicalName());
    public final static DotName SUBRESOURCE_META = DotName.createSimple(Subresource.class.getCanonicalName());

    /**
     * Creates an annotation index for the given entity type
     */
    public static Index createIndex(Class<?> type) {
        try {
            Indexer indexer = new Indexer();
            String className = type.getName().replace(".","/") + ".class";
            InputStream stream = type.getClassLoader()
                    .getResourceAsStream(className);
            indexer.index(stream);
            return indexer.complete();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Indexer", e);
        }
    }
}
