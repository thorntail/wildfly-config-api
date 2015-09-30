package org.wildfly.swarm.config;

import org.junit.Assert;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;
import org.jboss.jandex.MethodInfo;
import org.junit.Test;
import org.wildfly.config.runtime.Address;
import org.wildfly.config.runtime.ModelNodeBinding;
import org.wildfly.config.runtime.Subresource;
import org.wildfly.swarm.config.datasources.subsystem.dataSource.DataSource;
import org.wildfly.swarm.config.datasources.subsystem.dataSource.connectionProperties.ConnectionProperties;

import java.io.InputStream;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class BindingMetaDataTestCase {

    /**
     * This test just makes sure the generated classes can be instantiated.
     */
    @Test
    public void testClassInstances() {

        // attributes
        DataSource dataSource = new DataSource("Test");
        dataSource.userName("john_doe")
            .password("password");

        // subresources
        ConnectionProperties prop = new ConnectionProperties("Prop-One");
        prop.value("foo-bar");
        dataSource.connectionProperties(prop);
    }

    private final static String[] BLACKLIST = {
            "<init>", "getKey", "subresources"
    };

    /**
     * Verification of the binding meta on the generated classes
     */
    @Test
    public void testBindingMetaData() throws Exception {
        Indexer indexer = new Indexer();
        String className = DataSource.class.getCanonicalName().replace(".","/") + ".class";

        InputStream stream = DataSource.class.getClassLoader()
                                       .getResourceAsStream(className);
        indexer.index(stream);
        Index index = indexer.complete();

        ClassInfo clazz = index.getClassByName(DotName.createSimple(DataSource.class.getCanonicalName()));

        DotName bindingMeta = DotName.createSimple(ModelNodeBinding.class.getCanonicalName());
        DotName addressMeta = DotName.createSimple(Address.class.getCanonicalName());
        DotName subresourceMeta = DotName.createSimple(Subresource.class.getCanonicalName());

        // verify @Address annotations are present
        clazz.annotations().keySet().contains(addressMeta);

        // verify @Binding annotations are present
        for (MethodInfo method : clazz.methods()) {
          if(method.parameters().isEmpty()
                  && !method.hasAnnotation(subresourceMeta)
                  && !isExcluded(method.name()))
          {
              Assert.assertTrue(method.name()+" is missing @Binding", method.hasAnnotation(bindingMeta));
          }
        }

    }

    private static boolean isExcluded(String s) {
        for (String ex : BLACKLIST) {
            if(ex.equals(s))
                return true;
        }
        return false;
    }

}
