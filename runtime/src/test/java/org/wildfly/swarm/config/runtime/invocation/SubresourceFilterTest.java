package org.wildfly.swarm.config.runtime.invocation;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.wildfly.swarm.config.logging.TestLogging;

/**
 * @author Marco Hofstetter
 */
public class SubresourceFilterTest {

    @Test
    public void testLoggingSubresourceOrdering() throws Exception {

        SubresourceFilter subresourceFilter = new SubresourceFilter(TestLogging.class);

        List<Method> orderedMethods = subresourceFilter.invoke();

        Assert.assertEquals(TestLogging.class.getDeclaredMethods().length, orderedMethods.size());

        Assert.assertTrue(orderedMethods.get(0).getName().contains("Formatter"));
        Assert.assertTrue(orderedMethods.get(1).getName().contains("Formatter"));
        Assert.assertTrue(orderedMethods.get(TestLogging.class.getDeclaredMethods().length -2).getName().toLowerCase().contains("logger"));
        Assert.assertTrue(orderedMethods.get(TestLogging.class.getDeclaredMethods().length -1).getName().toLowerCase().contains("logger"));
    }

}