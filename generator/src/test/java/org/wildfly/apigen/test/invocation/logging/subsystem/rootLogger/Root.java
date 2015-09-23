package org.wildfly.apigen.test.invocation.logging.subsystem.rootLogger;

import org.wildfly.apigen.invocation.Implicit;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
import java.util.Map;
import java.util.List;
/**
 * Defines the root logger for this log context.
 */
@Address("/subsystem=logging/root-logger=ROOT")
@Implicit
public class Root<T extends Root> {

	private String key;
	private Map filter;
	private String filterSpec;
	private List<String> handlers;
	private String level;

	public Root() {
		this.key = "ROOT";
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * Defines a simple filter type.
	 */
	@ModelNodeBinding(detypedName = "filter")
	public Map filter() {
		return this.filter;
	}

	/**
	 * Defines a simple filter type.
	 */
	@SuppressWarnings("unchecked")
	public T filter(Map value) {
		this.filter = value;
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
	 * The handlers associated with the root logger.
	 */
	@ModelNodeBinding(detypedName = "handlers")
	public List<String> handlers() {
		return this.handlers;
	}

	/**
	 * The handlers associated with the root logger.
	 */
	@SuppressWarnings("unchecked")
	public T handlers(List<String> value) {
		this.handlers = value;
		return (T) this;
	}

	/**
	 * The log level specifying which message levels will be logged by the root logger. Message levels lower than this value will be discarded.
	 */
	@ModelNodeBinding(detypedName = "level")
	public String level() {
		return this.level;
	}

	/**
	 * The log level specifying which message levels will be logged by the root logger. Message levels lower than this value will be discarded.
	 */
	@SuppressWarnings("unchecked")
	public T level(String value) {
		this.level = value;
		return (T) this;
	}
}