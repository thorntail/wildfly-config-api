package org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.consoleHandler;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
/**
 * Defines a handler which writes to the console.
 */
@Address("/subsystem=logging/logging-profile=*/console-handler=*")
public class ConsoleHandler<T extends ConsoleHandler> {

	private String key;
	private Boolean autoflush;
	private Boolean enabled;
	private String encoding;
	private String filterSpec;
	private String formatter;
	private String level;
	private String name;
	private String namedFormatter;
	private String target;

	public ConsoleHandler(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * Automatically flush after each write.
	 */
	@ModelNodeBinding(detypedName = "autoflush")
	public Boolean autoflush() {
		return this.autoflush;
	}

	/**
	 * Automatically flush after each write.
	 */
	@SuppressWarnings("unchecked")
	public T autoflush(Boolean value) {
		this.autoflush = value;
		return (T) this;
	}

	/**
	 * If set to true the handler is enabled and functioning as normal, if set to false the handler is ignored when processing log messages.
	 */
	@ModelNodeBinding(detypedName = "enabled")
	public Boolean enabled() {
		return this.enabled;
	}

	/**
	 * If set to true the handler is enabled and functioning as normal, if set to false the handler is ignored when processing log messages.
	 */
	@SuppressWarnings("unchecked")
	public T enabled(Boolean value) {
		this.enabled = value;
		return (T) this;
	}

	/**
	 * The character encoding used by this Handler.
	 */
	@ModelNodeBinding(detypedName = "encoding")
	public String encoding() {
		return this.encoding;
	}

	/**
	 * The character encoding used by this Handler.
	 */
	@SuppressWarnings("unchecked")
	public T encoding(String value) {
		this.encoding = value;
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
	 * Defines a pattern for the formatter.
	 */
	@ModelNodeBinding(detypedName = "formatter")
	public String formatter() {
		return this.formatter;
	}

	/**
	 * Defines a pattern for the formatter.
	 */
	@SuppressWarnings("unchecked")
	public T formatter(String value) {
		this.formatter = value;
		return (T) this;
	}

	/**
	 * The log level specifying which message levels will be logged by this logger. Message levels lower than this value will be discarded.
	 */
	@ModelNodeBinding(detypedName = "level")
	public String level() {
		return this.level;
	}

	/**
	 * The log level specifying which message levels will be logged by this logger. Message levels lower than this value will be discarded.
	 */
	@SuppressWarnings("unchecked")
	public T level(String value) {
		this.level = value;
		return (T) this;
	}

	/**
	 * The name of the handler.
	 */
	@ModelNodeBinding(detypedName = "name")
	public String name() {
		return this.name;
	}

	/**
	 * The name of the handler.
	 */
	@SuppressWarnings("unchecked")
	public T name(String value) {
		this.name = value;
		return (T) this;
	}

	/**
	 * The name of the defined formatter to be used on the handler.
	 */
	@ModelNodeBinding(detypedName = "named-formatter")
	public String namedFormatter() {
		return this.namedFormatter;
	}

	/**
	 * The name of the defined formatter to be used on the handler.
	 */
	@SuppressWarnings("unchecked")
	public T namedFormatter(String value) {
		this.namedFormatter = value;
		return (T) this;
	}

	/**
	 * Defines the target of the console handler. The value can be System.out, System.err or console.
	 */
	@ModelNodeBinding(detypedName = "target")
	public String target() {
		return this.target;
	}

	/**
	 * Defines the target of the console handler. The value can be System.out, System.err or console.
	 */
	@SuppressWarnings("unchecked")
	public T target(String value) {
		this.target = value;
		return (T) this;
	}
}