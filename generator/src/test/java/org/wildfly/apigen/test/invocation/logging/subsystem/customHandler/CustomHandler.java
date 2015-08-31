package org.wildfly.apigen.test.invocation.logging.subsystem.customHandler;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import java.util.Map;
/**
 * Defines a custom logging handler. The custom handler must extend java.util.logging.Handler.
 */
@Address("/subsystem=logging/custom-handler=*")
public class CustomHandler {

	private String key;
	private String attributeClass;
	private Boolean enabled;
	private String encoding;
	private Map filter;
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
	@Binding(detypedName = "class")
	public String attributeClass() {
		return this.attributeClass;
	}

	/**
	 * The logging handler class to be used.
	 */
	public CustomHandler attributeClass(String value) {
		this.attributeClass = value;
		return this;
	}

	/**
	 * If set to true the handler is enabled and functioning as normal, if set to false the handler is ignored when processing log messages.
	 */
	@Binding(detypedName = "enabled")
	public Boolean enabled() {
		return this.enabled;
	}

	/**
	 * If set to true the handler is enabled and functioning as normal, if set to false the handler is ignored when processing log messages.
	 */
	public CustomHandler enabled(Boolean value) {
		this.enabled = value;
		return this;
	}

	/**
	 * The character encoding used by this Handler.
	 */
	@Binding(detypedName = "encoding")
	public String encoding() {
		return this.encoding;
	}

	/**
	 * The character encoding used by this Handler.
	 */
	public CustomHandler encoding(String value) {
		this.encoding = value;
		return this;
	}

	/**
	 * Defines a simple filter type.
	 */
	@Binding(detypedName = "filter")
	public Map filter() {
		return this.filter;
	}

	/**
	 * Defines a simple filter type.
	 */
	public CustomHandler filter(Map value) {
		this.filter = value;
		return this;
	}

	/**
	 * A filter expression value to define a filter. Example for a filter that does not match a pattern: not(match("JBAS.*"))
	 */
	@Binding(detypedName = "filter-spec")
	public String filterSpec() {
		return this.filterSpec;
	}

	/**
	 * A filter expression value to define a filter. Example for a filter that does not match a pattern: not(match("JBAS.*"))
	 */
	public CustomHandler filterSpec(String value) {
		this.filterSpec = value;
		return this;
	}

	/**
	 * Defines a pattern for the formatter.
	 */
	@Binding(detypedName = "formatter")
	public String formatter() {
		return this.formatter;
	}

	/**
	 * Defines a pattern for the formatter.
	 */
	public CustomHandler formatter(String value) {
		this.formatter = value;
		return this;
	}

	/**
	 * The log level specifying which message levels will be logged by this logger. Message levels lower than this value will be discarded.
	 */
	@Binding(detypedName = "level")
	public String level() {
		return this.level;
	}

	/**
	 * The log level specifying which message levels will be logged by this logger. Message levels lower than this value will be discarded.
	 */
	public CustomHandler level(String value) {
		this.level = value;
		return this;
	}

	/**
	 * The module that the logging handler depends on.
	 */
	@Binding(detypedName = "module")
	public String module() {
		return this.module;
	}

	/**
	 * The module that the logging handler depends on.
	 */
	public CustomHandler module(String value) {
		this.module = value;
		return this;
	}

	/**
	 * The name of the handler.
	 */
	@Binding(detypedName = "name")
	public String name() {
		return this.name;
	}

	/**
	 * The name of the handler.
	 */
	public CustomHandler name(String value) {
		this.name = value;
		return this;
	}

	/**
	 * The name of the defined formatter to be used on the handler.
	 */
	@Binding(detypedName = "named-formatter")
	public String namedFormatter() {
		return this.namedFormatter;
	}

	/**
	 * The name of the defined formatter to be used on the handler.
	 */
	public CustomHandler namedFormatter(String value) {
		this.namedFormatter = value;
		return this;
	}

	/**
	 * Defines the properties used for the logging handler. All properties must be accessible via a setter method.
	 */
	@Binding(detypedName = "properties")
	public Map properties() {
		return this.properties;
	}

	/**
	 * Defines the properties used for the logging handler. All properties must be accessible via a setter method.
	 */
	public CustomHandler properties(Map value) {
		this.properties = value;
		return this;
	}
}