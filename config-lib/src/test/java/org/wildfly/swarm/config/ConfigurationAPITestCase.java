package org.wildfly.swarm.config;

import org.junit.Assert;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;
import org.jboss.jandex.MethodInfo;
import org.junit.Test;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import org.wildfly.swarm.config.datasources.DataSource;

import java.io.InputStream;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class ConfigurationAPITestCase {

    /**
     * This test just makes sure the generated classes can be instantiated.
     */
    @Test
    public void testClassInstances() {
        DataSource dataSource = new DataSource();
        dataSource.setUserName("john_doe");
        dataSource.setPassword("password");
    }

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

        // verify @Address annotations are present
        clazz.annotations().keySet().contains(DotName.createSimple(Address.class.getCanonicalName()));

        // verify @Binding annotations are present
        for (MethodInfo method : clazz.methods()) {
          if(method.name().startsWith("get"))
          {
              Assert.assertTrue(method.hasAnnotation(DotName.createSimple(Binding.class.getCanonicalName())));
          }
        }

    }

}
