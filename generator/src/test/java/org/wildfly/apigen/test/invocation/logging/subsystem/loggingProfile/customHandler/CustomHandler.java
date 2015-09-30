package org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.customHandler;

import org.wildfly.config.runtime.Address;
import org.wildfly.config.runtime.ModelNodeBinding;
import java.util.Map;
/**
 * Defines a custom logging handler. The custom handler must extend java.util.logging.Handler.
 */
@Address("/subsystem=logging/logging-profile=*/custom-handler=*")
public class CustomHandler<T extends CustomHandler> {

	private String key;
	private String attributeClass;
	private Boolean enabled;
	private String encoding;
	private String filterSpec;
	private String formatter;
	private String level;
	private String module;
	private String name;
	private String namedFormatter;
	private Map properties;

	public CustomHandler(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * The logging handler class to be used.
	 */
	@ModelNodeBinding(detypedName = "class")
	public String attributeClass() {
		return this.attributeClass;
	}

	/**
	 * The logging handler class to be used.
	 */
	@SuppressWarnings("unchecked")
	public T attributeClass(String value) {
		this.attributeClass = value;
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
	 * The module that the logging handler depends on.
	 */
	@ModelNodeBinding(detypedName = "module")
	public String module() {
		return this.module;
	}

	/**
	 * The module that the logging handler depends on.
	 */
	@SuppressWarnings("unchecked")
	public T module(String value) {
		this.module = value;
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
	 * Defines the properties used for the logging handler. All properties must be accessible via a setter method.
	 */
	@ModelNodeBinding(detypedName = "properties")
	public Map properties() {
		return this.properties;
	}

	/**
	 * Defines the properties used for the logging handler. All properties must be accessible via a setter method.
	 */
	@SuppressWarnings("unchecked")
	public T properties(Map value) {
		this.properties = value;
		return (T) this;
	}
}