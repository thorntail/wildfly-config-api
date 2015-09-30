package org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.logger;

import org.wildfly.config.runtime.Address;
import org.wildfly.config.runtime.ModelNodeBinding;
import java.util.List;
/**
 * Defines a logger category.
 */
@Address("/subsystem=logging/logging-profile=*/logger=*")
public class Logger<T extends Logger> {

	private String key;
	private String category;
	private String filterSpec;
	private List<String> handlers;
	private String level;
	private Boolean useParentHandlers;

	public Logger(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * Specifies the category for the logger.
	 */
	@ModelNodeBinding(detypedName = "category")
	public String category() {
		return this.category;
	}

	/**
	 * Specifies the category for the logger.
	 */
	@SuppressWarnings("unchecked")
	public T category(String value) {
		this.category = value;
		return (T) this;
	}

	/**
	 * A filter expression value to define a filter. Example for a filter that does not match a pattern: not(match("JBAS.*"))
	 */
	@ModelNodeBinding(detypedName = "filter-spec")
	public String filterSpec() {
		return this.filterSpec;
	}

	/**
	 * A filter expression value to define a filter. Example for a filter that does not match a pattern: not(match("JBAS.*"))
	 */
	@SuppressWarnings("unchecked")
	public T filterSpec(String value) {
		this.filterSpec = value;
		return (T) this;
	}

	/**
	 * The handlers associated with the logger.
	 */
	@ModelNodeBinding(detypedName = "handlers")
	public List<String> handlers() {
		return this.handlers;
	}

	/**
	 * The handlers associated with the logger.
	 */
	@SuppressWarnings("unchecked")
	public T handlers(List<String> value) {
		this.handlers = value;
		return (T) this;
	}

	/**
	 * The log level specifying which message levels will be logged by the logger. Message levels lower than this value will be discarded.
	 */
	@ModelNodeBinding(detypedName = "level")
	public String level() {
		return this.level;
	}

	/**
	 * The log level specifying which message levels will be logged by the logger. Message levels lower than this value will be discarded.
	 */
	@SuppressWarnings("unchecked")
	public T level(String value) {
		this.level = value;
		return (T) this;
	}

	/**
	 * Specifies whether or not this logger should send its output to it's parent Logger.
	 */
	@ModelNodeBinding(detypedName = "use-parent-handlers")
	public Boolean useParentHandlers() {
		return this.useParentHandlers;
	}

	/**
	 * Specifies whether or not this logger should send its output to it's parent Logger.
	 */
	@SuppressWarnings("unchecked")
	public T useParentHandlers(Boolean value) {
		this.useParentHandlers = value;
		return (T) this;
	}
}