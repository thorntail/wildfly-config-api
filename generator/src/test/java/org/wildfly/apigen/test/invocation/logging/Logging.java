package org.wildfly.apigen.test.invocation.logging;

import org.wildfly.apigen.invocation.Implicit;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import java.util.List;
import org.wildfly.apigen.invocation.Subresource;
import org.wildfly.apigen.test.invocation.logging.subsystem.periodicRotatingFileHandler.PeriodicRotatingFileHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.logger.Logger;
import org.wildfly.apigen.test.invocation.logging.subsystem.asyncHandler.AsyncHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.fileHandler.FileHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.sizeRotatingFileHandler.SizeRotatingFileHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.syslogHandler.SyslogHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.LoggingProfile;
import org.wildfly.apigen.test.invocation.logging.subsystem.customFormatter.CustomFormatter;
import org.wildfly.apigen.test.invocation.logging.subsystem.periodicSizeRotatingFileHandler.PeriodicSizeRotatingFileHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.consoleHandler.ConsoleHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.logFile.LogFile;
import org.wildfly.apigen.test.invocation.logging.subsystem.patternFormatter.PatternFormatter;
import org.wildfly.apigen.test.invocation.logging.subsystem.customHandler.CustomHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.rootLogger.Root;
/**
 * The configuration of the logging subsystem.
 */
@Address("/subsystem=logging")
@Implicit
public class Logging {

	private String key;
	private Boolean addLoggingApiDependencies;
	private Boolean useDeploymentLoggingConfig;
	private LoggingResources subresources = new LoggingResources();
	private Root root;

	public Logging() {
		this.key = "logging";
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * Indicates whether or not logging API dependencies should be added to deployments during the deployment process. A value of true will add the dependencies to the deployment. A value of false will skip the deployment from being processed for logging API dependencies.
	 */
	@Binding(detypedName = "add-logging-api-dependencies")
	public Boolean addLoggingApiDependencies() {
		return this.addLoggingApiDependencies;
	}

	/**
	 * Indicates whether or not logging API dependencies should be added to deployments during the deployment process. A value of true will add the dependencies to the deployment. A value of false will skip the deployment from being processed for logging API dependencies.
	 */
	public Logging addLoggingApiDependencies(Boolean value) {
		this.addLoggingApiDependencies = value;
		return this;
	}

	/**
	 * Indicates whether or not deployments should use a logging configuration file found in the deployment to configure the log manager. If set to true and a logging configuration file was found in the deployments META-INF or WEB-INF/classes directory, then a log manager will be configured with those settings. If set false the servers logging configuration will be used regardless of any logging configuration files supplied in the deployment.
	 */
	@Binding(detypedName = "use-deployment-logging-config")
	public Boolean useDeploymentLoggingConfig() {
		return this.useDeploymentLoggingConfig;
	}

	/**
	 * Indicates whether or not deployments should use a logging configuration file found in the deployment to configure the log manager. If set to true and a logging configuration file was found in the deployments META-INF or WEB-INF/classes directory, then a log manager will be configured with those settings. If set false the servers logging configuration will be used regardless of any logging configuration files supplied in the deployment.
	 */
	public Logging useDeploymentLoggingConfig(Boolean value) {
		this.useDeploymentLoggingConfig = value;
		return this;
	}

	public LoggingResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all PeriodicRotatingFileHandler objects to this subresource
	 * @return this
	 * @param value List of PeriodicRotatingFileHandler objects.
	 */
	public Logging periodicRotatingFileHandlers(
			List<PeriodicRotatingFileHandler> value) {
		this.subresources.periodicRotatingFileHandlers.addAll(value);
		return this;
	}

	/**
	 * Add the PeriodicRotatingFileHandler object to the list of subresources
	 * @param value The PeriodicRotatingFileHandler to add
	 * @return this
	 */
	public Logging periodicRotatingFileHandler(PeriodicRotatingFileHandler value) {
		this.subresources.periodicRotatingFileHandlers.add(value);
		return this;
	}

	/**
	 * Add all Logger objects to this subresource
	 * @return this
	 * @param value List of Logger objects.
	 */
	public Logging loggers(List<Logger> value) {
		this.subresources.loggers.addAll(value);
		return this;
	}

	/**
	 * Add the Logger object to the list of subresources
	 * @param value The Logger to add
	 * @return this
	 */
	public Logging logger(Logger value) {
		this.subresources.loggers.add(value);
		return this;
	}

	/**
	 * Add all AsyncHandler objects to this subresource
	 * @return this
	 * @param value List of AsyncHandler objects.
	 */
	public Logging asyncHandlers(List<AsyncHandler> value) {
		this.subresources.asyncHandlers.addAll(value);
		return this;
	}

	/**
	 * Add the AsyncHandler object to the list of subresources
	 * @param value The AsyncHandler to add
	 * @return this
	 */
	public Logging asyncHandler(AsyncHandler value) {
		this.subresources.asyncHandlers.add(value);
		return this;
	}

	/**
	 * Add all FileHandler objects to this subresource
	 * @return this
	 * @param value List of FileHandler objects.
	 */
	public Logging fileHandlers(List<FileHandler> value) {
		this.subresources.fileHandlers.addAll(value);
		return this;
	}

	/**
	 * Add the FileHandler object to the list of subresources
	 * @param value The FileHandler to add
	 * @return this
	 */
	public Logging fileHandler(FileHandler value) {
		this.subresources.fileHandlers.add(value);
		return this;
	}

	/**
	 * Add all SizeRotatingFileHandler objects to this subresource
	 * @return this
	 * @param value List of SizeRotatingFileHandler objects.
	 */
	public Logging sizeRotatingFileHandlers(List<SizeRotatingFileHandler> value) {
		this.subresources.sizeRotatingFileHandlers.addAll(value);
		return this;
	}

	/**
	 * Add the SizeRotatingFileHandler object to the list of subresources
	 * @param value The SizeRotatingFileHandler to add
	 * @return this
	 */
	public Logging sizeRotatingFileHandler(SizeRotatingFileHandler value) {
		this.subresources.sizeRotatingFileHandlers.add(value);
		return this;
	}

	/**
	 * Add all SyslogHandler objects to this subresource
	 * @return this
	 * @param value List of SyslogHandler objects.
	 */
	public Logging syslogHandlers(List<SyslogHandler> value) {
		this.subresources.syslogHandlers.addAll(value);
		return this;
	}

	/**
	 * Add the SyslogHandler object to the list of subresources
	 * @param value The SyslogHandler to add
	 * @return this
	 */
	public Logging syslogHandler(SyslogHandler value) {
		this.subresources.syslogHandlers.add(value);
		return this;
	}

	/**
	 * Add all LoggingProfile objects to this subresource
	 * @return this
	 * @param value List of LoggingProfile objects.
	 */
	public Logging loggingProfiles(List<LoggingProfile> value) {
		this.subresources.loggingProfiles.addAll(value);
		return this;
	}

	/**
	 * Add the LoggingProfile object to the list of subresources
	 * @param value The LoggingProfile to add
	 * @return this
	 */
	public Logging loggingProfile(LoggingProfile value) {
		this.subresources.loggingProfiles.add(value);
		return this;
	}

	/**
	 * Add all CustomFormatter objects to this subresource
	 * @return this
	 * @param value List of CustomFormatter objects.
	 */
	public Logging customFormatters(List<CustomFormatter> value) {
		this.subresources.customFormatters.addAll(value);
		return this;
	}

	/**
	 * Add the CustomFormatter object to the list of subresources
	 * @param value The CustomFormatter to add
	 * @return this
	 */
	public Logging customFormatter(CustomFormatter value) {
		this.subresources.customFormatters.add(value);
		return this;
	}

	/**
	 * Add all PeriodicSizeRotatingFileHandler objects to this subresource
	 * @return this
	 * @param value List of PeriodicSizeRotatingFileHandler objects.
	 */
	public Logging periodicSizeRotatingFileHandlers(
			List<PeriodicSizeRotatingFileHandler> value) {
		this.subresources.periodicSizeRotatingFileHandlers.addAll(value);
		return this;
	}

	/**
	 * Add the PeriodicSizeRotatingFileHandler object to the list of subresources
	 * @param value The PeriodicSizeRotatingFileHandler to add
	 * @return this
	 */
	public Logging periodicSizeRotatingFileHandler(
			PeriodicSizeRotatingFileHandler value) {
		this.subresources.periodicSizeRotatingFileHandlers.add(value);
		return this;
	}

	/**
	 * Add all ConsoleHandler objects to this subresource
	 * @return this
	 * @param value List of ConsoleHandler objects.
	 */
	public Logging consoleHandlers(List<ConsoleHandler> value) {
		this.subresources.consoleHandlers.addAll(value);
		return this;
	}

	/**
	 * Add the ConsoleHandler object to the list of subresources
	 * @param value The ConsoleHandler to add
	 * @return this
	 */
	public Logging consoleHandler(ConsoleHandler value) {
		this.subresources.consoleHandlers.add(value);
		return this;
	}

	/**
	 * Add all LogFile objects to this subresource
	 * @return this
	 * @param value List of LogFile objects.
	 */
	public Logging logFiles(List<LogFile> value) {
		this.subresources.logFiles.addAll(value);
		return this;
	}

	/**
	 * Add the LogFile object to the list of subresources
	 * @param value The LogFile to add
	 * @return this
	 */
	public Logging logFile(LogFile value) {
		this.subresources.logFiles.add(value);
		return this;
	}

	/**
	 * Add all PatternFormatter objects to this subresource
	 * @return this
	 * @param value List of PatternFormatter objects.
	 */
	public Logging patternFormatters(List<PatternFormatter> value) {
		this.subresources.patternFormatters.addAll(value);
		return this;
	}

	/**
	 * Add the PatternFormatter object to the list of subresources
	 * @param value The PatternFormatter to add
	 * @return this
	 */
	public Logging patternFormatter(PatternFormatter value) {
		this.subresources.patternFormatters.add(value);
		return this;
	}

	/**
	 * Add all CustomHandler objects to this subresource
	 * @return this
	 * @param value List of CustomHandler objects.
	 */
	public Logging customHandlers(List<CustomHandler> value) {
		this.subresources.customHandlers.addAll(value);
		return this;
	}

	/**
	 * Add the CustomHandler object to the list of subresources
	 * @param value The CustomHandler to add
	 * @return this
	 */
	public Logging customHandler(CustomHandler value) {
		this.subresources.customHandlers.add(value);
		return this;
	}

	/**
	 * Child mutators for Logging
	 */
	public class LoggingResources {
		/**
		 * Defines a handler which writes to a file, rotating the log after a time period derived from the given suffix string, which should be in a format understood by java.text.SimpleDateFormat.
		 */
		private List<PeriodicRotatingFileHandler> periodicRotatingFileHandlers = new java.util.ArrayList<>();
		/**
		 * Defines a logger category.
		 */
		private List<Logger> loggers = new java.util.ArrayList<>();
		/**
		 * Defines a handler which writes to the sub-handlers in an asynchronous thread. Used for handlers which introduce a substantial amount of lag.
		 */
		private List<AsyncHandler> asyncHandlers = new java.util.ArrayList<>();
		/**
		 * Defines a handler which writes to a file.
		 */
		private List<FileHandler> fileHandlers = new java.util.ArrayList<>();
		/**
		 * Defines a handler which writes to a file, rotating the log after the size of the file grows beyond a certain point and keeping a fixed number of backups.
		 */
		private List<SizeRotatingFileHandler> sizeRotatingFileHandlers = new java.util.ArrayList<>();
		/**
		 * Defines a syslog handler.
		 */
		private List<SyslogHandler> syslogHandlers = new java.util.ArrayList<>();
		/**
		 * The configuration of the logging subsystem.
		 */
		private List<LoggingProfile> loggingProfiles = new java.util.ArrayList<>();
		/**
		 * A custom formatter to be used with handlers. Note that most log records are formatted in the printf format. Formatters may require invocation of the org.jboss.logmanager.ExtLogRecord#getFormattedMessage() for the message to be properly formatted.
		 */
		private List<CustomFormatter> customFormatters = new java.util.ArrayList<>();
		/**
		 * Defines a handler which writes to a file, rotating the log after a time period derived from the given suffix string or after the size of the file grows beyond a certain point and keeping a fixed number of backups. The suffix should be in a format understood by the java.text.SimpleDateFormat. Any backups rotated by the suffix will not be purged during a size rotation.
		 */
		private List<PeriodicSizeRotatingFileHandler> periodicSizeRotatingFileHandlers = new java.util.ArrayList<>();
		/**
		 * Defines a handler which writes to the console.
		 */
		private List<ConsoleHandler> consoleHandlers = new java.util.ArrayList<>();
		/**
		 * Log files that are available to be read.
		 */
		private List<LogFile> logFiles = new java.util.ArrayList<>();
		/**
		 * A pattern formatter to be used with handlers.
		 */
		private List<PatternFormatter> patternFormatters = new java.util.ArrayList<>();
		/**
		 * Defines a custom logging handler. The custom handler must extend java.util.logging.Handler.
		 */
		private List<CustomHandler> customHandlers = new java.util.ArrayList<>();

		/**
		 * Get the list of PeriodicRotatingFileHandler resources
		 * @return the list of resources
		 */
		@Subresource
		public List<PeriodicRotatingFileHandler> periodicRotatingFileHandlers() {
			return this.periodicRotatingFileHandlers;
		}

		/**
		 * Get the list of Logger resources
		 * @return the list of resources
		 */
		@Subresource
		public List<Logger> loggers() {
			return this.loggers;
		}

		/**
		 * Get the list of AsyncHandler resources
		 * @return the list of resources
		 */
		@Subresource
		public List<AsyncHandler> asyncHandlers() {
			return this.asyncHandlers;
		}

		/**
		 * Get the list of FileHandler resources
		 * @return the list of resources
		 */
		@Subresource
		public List<FileHandler> fileHandlers() {
			return this.fileHandlers;
		}

		/**
		 * Get the list of SizeRotatingFileHandler resources
		 * @return the list of resources
		 */
		@Subresource
		public List<SizeRotatingFileHandler> sizeRotatingFileHandlers() {
			return this.sizeRotatingFileHandlers;
		}

		/**
		 * Get the list of SyslogHandler resources
		 * @return the list of resources
		 */
		@Subresource
		public List<SyslogHandler> syslogHandlers() {
			return this.syslogHandlers;
		}

		/**
		 * Get the list of LoggingProfile resources
		 * @return the list of resources
		 */
		@Subresource
		public List<LoggingProfile> loggingProfiles() {
			return this.loggingProfiles;
		}

		/**
		 * Get the list of CustomFormatter resources
		 * @return the list of resources
		 */
		@Subresource
		public List<CustomFormatter> customFormatters() {
			return this.customFormatters;
		}

		/**
		 * Get the list of PeriodicSizeRotatingFileHandler resources
		 * @return the list of resources
		 */
		@Subresource
		public List<PeriodicSizeRotatingFileHandler> periodicSizeRotatingFileHandlers() {
			return this.periodicSizeRotatingFileHandlers;
		}

		/**
		 * Get the list of ConsoleHandler resources
		 * @return the list of resources
		 */
		@Subresource
		public List<ConsoleHandler> consoleHandlers() {
			return this.consoleHandlers;
		}

		/**
		 * Get the list of LogFile resources
		 * @return the list of resources
		 */
		@Subresource
		public List<LogFile> logFiles() {
			return this.logFiles;
		}

		/**
		 * Get the list of PatternFormatter resources
		 * @return the list of resources
		 */
		@Subresource
		public List<PatternFormatter> patternFormatters() {
			return this.patternFormatters;
		}

		/**
		 * Get the list of CustomHandler resources
		 * @return the list of resources
		 */
		@Subresource
		public List<CustomHandler> customHandlers() {
			return this.customHandlers;
		}
	}

	/**
	 * Defines the root logger for this log context.
	 */
	@Subresource
	public Root root() {
		return this.root;
	}

	/**
	 * Defines the root logger for this log context.
	 */
	public Logging root(Root value) {
		this.root = value;
		return this;
	}
}