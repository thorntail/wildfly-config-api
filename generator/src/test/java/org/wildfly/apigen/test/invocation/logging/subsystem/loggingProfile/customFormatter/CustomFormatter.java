package org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.customFormatter;

import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;
import java.util.Map;
/**
 * A custom formatter to be used with handlers. Note that most log records are formatted in the printf format. Formatters may require invocation of the org.jboss.logmanager.ExtLogRecord#getFormattedMessage() for the message to be properly formatted.
 */
@Address("/subsystem=logging/logging-profile=*/custom-formatter=*")
public class CustomFormatter<T extends CustomFormatter> {

	private String key;
	private String attributeClass;
	private String module;
	private Map properties;

	public CustomFormatter(String key) {
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