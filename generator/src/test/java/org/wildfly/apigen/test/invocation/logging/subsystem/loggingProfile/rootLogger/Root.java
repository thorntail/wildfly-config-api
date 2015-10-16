package org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.rootLogger;

import org.wildfly.swarm.config.runtime.Implicit;
import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;
import java.util.List;
/**
 * Defines the root logger for this log context.
 */
@Address("/subsystem=logging/logging-profile=*/root-logger=ROOT")
@Implicit
public class Root<T extends Root> {

	private String key;
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