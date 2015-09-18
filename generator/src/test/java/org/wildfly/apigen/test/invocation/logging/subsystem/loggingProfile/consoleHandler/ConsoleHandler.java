package org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.consoleHandler;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
/**
 * Defines a handler which writes to the console.
 */
@Address("/subsystem=logging/logging-profile=*/console-handler=*")
public class ConsoleHandler {

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
	public ConsoleHandler autoflush(Boolean value) {
		this.autoflush = value;
		return this;
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
	public ConsoleHandler enabled(Boolean value) {
		this.enabled = value;
		return this;
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
	public ConsoleHandler encoding(String value) {
		this.encoding = value;
		return this;
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
	public ConsoleHandler filterSpec(String value) {
		this.filterSpec = value;
		return this;
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
	public ConsoleHandler formatter(String value) {
		this.formatter = value;
		return this;
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
	public ConsoleHandler level(String value) {
		this.level = value;
		return this;
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
	public ConsoleHandler name(String value) {
		this.name = value;
		return this;
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
	public ConsoleHandler namedFormatter(String value) {
		this.namedFormatter = value;
		return this;
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
	public ConsoleHandler target(String value) {
		this.target = value;
		return this;
	}
}