package org.wildfly.swarm.config.logging;

import org.wildfly.swarm.config.runtime.Subresource;

public class TestLogging {

    @Subresource
    public void periodicRotatingFileHandlers() { }

    @Subresource
    public void loggers() { }

    @Subresource
    public void asyncHandlers() { }

    @Subresource
    public void fileHandlers() { }

    @Subresource
    public void sizeRotatingFileHandlers() { }

    @Subresource
    public void syslogHandlers() { }

    @Subresource
    public void loggingProfiles() { }

    @Subresource
    public void customFormatters() { }

    @Subresource
    public void periodicSizeRotatingFileHandlers() { }

    @Subresource
    public void consoleHandlers() { }

    @Subresource
    public void logFiles() { }

    @Subresource
    public void patternFormatters() { }

    @Subresource
    public void customHandlers() { }

    @Subresource
    public void rootLogger() { }
}
