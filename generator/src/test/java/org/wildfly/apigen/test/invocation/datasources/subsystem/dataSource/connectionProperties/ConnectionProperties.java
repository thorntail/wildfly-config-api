package org.wildfly.apigen.test.invocation.datasources.subsystem.dataSource.connectionProperties;

import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;
/**
 * The connection-properties element allows you to pass in arbitrary connection properties to the Driver.connect(url, props) method
 */
@Address("/subsystem=datasources/data-source=*/connection-properties=*")
public class ConnectionProperties<T extends ConnectionProperties> {

	private String key;
	private String value;

	public ConnectionProperties(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * Each connection-property specifies a string name/value pair with the property name coming from the name attribute and the value coming from the element content
	 */
	@ModelNodeBinding(detypedName = "value")
	public String value() {
		return this.value;
	}

	/**
	 * Each connection-property specifies a string name/value pair with the property name coming from the name attribute and the value coming from the element content
	 */
	@SuppressWarnings("unchecked")
	public T value(String value) {
		this.value = value;
		return (T) this;
	}
}