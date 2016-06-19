package org.wildfly.swarm.config.runtime.invocation;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marco Hofstetter
 */
public class LoggingComparatorTest {

    @Test
    public void testFormattersFirst() throws Exception {
        SubresourceFilter.LoggingComparator loggingComparator = new SubresourceFilter.LoggingComparator();

        Assert.assertEquals(-1, loggingComparator.compare(TestClass.class.getMethod("customFormatters"), TestClass.class.getMethod("customHandlers")));
        Assert.assertEquals(1, loggingComparator.compare(TestClass.class.getMethod("customHandlers"), TestClass.class.getMethod("customFormatters")));
    }

    @Test
    public void testLoggersLast() throws Exception {
        SubresourceFilter.LoggingComparator loggingComparator = new SubresourceFilter.LoggingComparator();

        Assert.assertEquals(1, loggingComparator.compare(TestClass.class.getMethod("loggers"), TestClass.class.getMethod("customHandlers")));
        Assert.assertEquals(1, loggingComparator.compare(TestClass.class.getMethod("rootLogger"), TestClass.class.getMethod("customHandlers")));
        Assert.assertEquals(-1, loggingComparator.compare(TestClass.class.getMethod("customHandlers"), TestClass.class.getMethod("loggers")));
        Assert.assertEquals(-1, loggingComparator.compare(TestClass.class.getMethod("customHandlers"), TestClass.class.getMethod("rootLogger")));
    }

    private static class TestClass {

        public void customHandlers() { }

        public void customFormatters() { }

        public void loggers() { }

        public void rootLogger() { }
    }
}