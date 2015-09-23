package org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile;

import org.wildfly.apigen.invocation.Address;

import java.util.List;
import org.wildfly.apigen.invocation.Subresource;
import org.wildfly.apigen.invocation.ModelNodeSubresources;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.consoleHandler.ConsoleHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.customFormatter.CustomFormatter;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.customHandler.CustomHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.fileHandler.FileHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.logFile.LogFile;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.logger.Logger;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.patternFormatter.PatternFormatter;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.periodicRotatingFileHandler.PeriodicRotatingFileHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.periodicSizeRotatingFileHandler.PeriodicSizeRotatingFileHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.rootLogger.Root;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.sizeRotatingFileHandler.SizeRotatingFileHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.syslogHandler.SyslogHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.asyncHandler.AsyncHandler;

/**
 * The configuration of the logging subsystem.
 */
@Address("/subsystem=logging/logging-profile=*")
public class LoggingProfile<T extends LoggingProfile> {

	private String key;
	private LoggingProfileResources subresources = new LoggingProfileResources();
	private Root root;

	public LoggingProfile(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public LoggingProfileResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all PeriodicRotatingFileHandler objects to this subresource
	 * @return this
	 * @param value List of PeriodicRotatingFileHandler objects.
	 */
	@SuppressWarnings("unchecked")
	public T periodicRotatingFileHandlers(
			List<PeriodicRotatingFileHandler> value) {
		this.subresources.periodicRotatingFileHandlers.addAll(value);
		return (T) this;
	}

	/**
	 * Add the PeriodicRotatingFileHandler object to the list of subresources
	 * @param value The PeriodicRotatingFileHandler to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T periodicRotatingFileHandler(PeriodicRotatingFileHandler value) {
		this.subresources.periodicRotatingFileHandlers.add(value);
		return (T) this;
	}

	/**
	 * Add all CustomFormatter objects to this subresource
	 * @return this
	 * @param value List of CustomFormatter objects.
	 */
	@SuppressWarnings("unchecked")
	public T customFormatters(List<CustomFormatter> value) {
		this.subresources.customFormatters.addAll(value);
		return (T) this;
	}

	/**
	 * Add the CustomFormatter object to the list of subresources
	 * @param value The CustomFormatter to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T customFormatter(CustomFormatter value) {
		this.subresources.customFormatters.add(value);
		return (T) this;
	}

	/**
	 * Add all PeriodicSizeRotatingFileHandler objects to this subresource
	 * @return this
	 * @param value List of PeriodicSizeRotatingFileHandler objects.
	 */
	@SuppressWarnings("unchecked")
	public T periodicSizeRotatingFileHandlers(
			List<PeriodicSizeRotatingFileHandler> value) {
		this.subresources.periodicSizeRotatingFileHandlers.addAll(value);
		return (T) this;
	}

	/**
	 * Add the PeriodicSizeRotatingFileHandler object to the list of subresources
	 * @param value The PeriodicSizeRotatingFileHandler to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T periodicSizeRotatingFileHandler(
			PeriodicSizeRotatingFileHandler value) {
		this.subresources.periodicSizeRotatingFileHandlers.add(value);
		return (T) this;
	}

	/**
	 * Add all ConsoleHandler objects to this subresource
	 * @return this
	 * @param value List of ConsoleHandler objects.
	 */
	@SuppressWarnings("unchecked")
	public T consoleHandlers(List<ConsoleHandler> value) {
		this.subresources.consoleHandlers.addAll(value);
		return (T) this;
	}

	/**
	 * Add the ConsoleHandler object to the list of subresources
	 * @param value The ConsoleHandler to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T consoleHandler(ConsoleHandler value) {
		this.subresources.consoleHandlers.add(value);
		return (T) this;
	}

	/**
	 * Add all Logger objects to this subresource
	 * @return this
	 * @param value List of Logger objects.
	 */
	@SuppressWarnings("unchecked")
	public T loggers(List<Logger> value) {
		this.subresources.loggers.addAll(value);
		return (T) this;
	}

	/**
	 * Add the Logger object to the list of subresources
	 * @param value The Logger to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T logger(Logger value) {
		this.subresources.loggers.add(value);
		return (T) this;
	}

	/**
	 * Add all AsyncHandler objects to this subresource
	 * @return this
	 * @param value List of AsyncHandler objects.
	 */
	@SuppressWarnings("unchecked")
	public T asyncHandlers(List<AsyncHandler> value) {
		this.subresources.asyncHandlers.addAll(value);
		return (T) this;
	}

	/**
	 * Add the AsyncHandler object to the list of subresources
	 * @param value The AsyncHandler to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T asyncHandler(AsyncHandler value) {
		this.subresources.asyncHandlers.add(value);
		return (T) this;
	}

	/**
	 * Add all FileHandler objects to this subresource
	 * @return this
	 * @param value List of FileHandler objects.
	 */
	@SuppressWarnings("unchecked")
	public T fileHandlers(List<FileHandler> value) {
		this.subresources.fileHandlers.addAll(value);
		return (T) this;
	}

	/**
	 * Add the FileHandler object to the list of subresources
	 * @param value The FileHandler to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T fileHandler(FileHandler value) {
		this.subresources.fileHandlers.add(value);
		return (T) this;
	}

	/**
	 * Add all LogFile objects to this subresource
	 * @return this
	 * @param value List of LogFile objects.
	 */
	@SuppressWarnings("unchecked")
	public T logFiles(List<LogFile> value) {
		this.subresources.logFiles.addAll(value);
		return (T) this;
	}

	/**
	 * Add the LogFile object to the list of subresources
	 * @param value The LogFile to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T logFile(LogFile value) {
		this.subresources.logFiles.add(value);
		return (T) this;
	}

	/**
	 * Add all PatternFormatter objects to this subresource
	 * @return this
	 * @param value List of PatternFormatter objects.
	 */
	@SuppressWarnings("unchecked")
	public T patternFormatters(List<PatternFormatter> value) {
		this.subresources.patternFormatters.addAll(value);
		return (T) this;
	}

	/**
	 * Add the PatternFormatter object to the list of subresources
	 * @param value The PatternFormatter to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T patternFormatter(PatternFormatter value) {
		this.subresources.patternFormatters.add(value);
		return (T) this;
	}

	/**
	 * Add all SizeRotatingFileHandler objects to this subresource
	 * @return this
	 * @param value List of SizeRotatingFileHandler objects.
	 */
	@SuppressWarnings("unchecked")
	public T sizeRotatingFileHandlers(List<SizeRotatingFileHandler> value) {
		this.subresources.sizeRotatingFileHandlers.addAll(value);
		return (T) this;
	}

	/**
	 * Add the SizeRotatingFileHandler object to the list of subresources
	 * @param value The SizeRotatingFileHandler to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T sizeRotatingFileHandler(SizeRotatingFileHandler value) {
		this.subresources.sizeRotatingFileHandlers.add(value);
		return (T) this;
	}

	/**
	 * Add all SyslogHandler objects to this subresource
	 * @return this
	 * @param value List of SyslogHandler objects.
	 */
	@SuppressWarnings("unchecked")
	public T syslogHandlers(List<SyslogHandler> value) {
		this.subresources.syslogHandlers.addAll(value);
		return (T) this;
	}

	/**
	 * Add the SyslogHandler object to the list of subresources
	 * @param value The SyslogHandler to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T syslogHandler(SyslogHandler value) {
		this.subresources.syslogHandlers.add(value);
		return (T) this;
	}

	/**
	 * Add all CustomHandler objects to this subresource
	 * @return this
	 * @param value List of CustomHandler objects.
	 */
	@SuppressWarnings("unchecked")
	public T customHandlers(List<CustomHandler> value) {
		this.subresources.customHandlers.addAll(value);
		return (T) this;
	}

	/**
	 * Add the CustomHandler object to the list of subresources
	 * @param value The CustomHandler to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T customHandler(CustomHandler value) {
		this.subresources.customHandlers.add(value);
		return (T) this;
	}

	/**
	 * Child mutators for LoggingProfile
	 */
	@ModelNodeSubresources
	public class LoggingProfileResources {
		/**
		 * Defines a handler which writes to a file, rotating the log after a time period derived from the given suffix string, which should be in a format understood by java.text.SimpleDateFormat.
		 */
		private List<PeriodicRotatingFileHandler> periodicRotatingFileHandlers = new java.util.ArrayList<>();
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
		 * Log files that are available to be read.
		 */
		private List<LogFile> logFiles = new java.util.ArrayList<>();
		/**
		 * A pattern formatter to be used with handlers.
		 */
		private List<PatternFormatter> patternFormatters = new java.util.ArrayList<>();
		/**
		 * Defines a handler which writes to a file, rotating the log after the size of the file grows beyond a certain point and keeping a fixed number of backups.
		 */
		private List<SizeRotatingFileHandler> sizeRotatingFileHandlers = new java.util.ArrayList<>();
		/**
		 * Defines a syslog handler.
		 */
		private List<SyslogHandler> syslogHandlers = new java.util.ArrayList<>();
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
	@SuppressWarnings("unchecked")
	public T root(Root value) {
		this.root = value;
		return (T) this;
	}
}