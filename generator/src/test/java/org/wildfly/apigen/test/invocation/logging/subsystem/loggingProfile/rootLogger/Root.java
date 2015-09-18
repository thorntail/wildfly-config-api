package org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.rootLogger;

import org.wildfly.apigen.invocation.Implicit;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
import java.util.List;
/**
 * Defines the root logger for this log context.
 */
@Address("/subsystem=logging/logging-profile=*/root-logger=ROOT")
@Implicit
public class Root {

	private String key;
	private String filterSpec;
	private List handlers;
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
	public Root filterSpec(String value) {
		this.filterSpec = value;
		return this;
	}

	/**
	 * The handlers associated with the root logger.
	 */
	@ModelNodeBinding(detypedName = "handlers")
	public List handlers() {
		return this.handlers;
	}

	/**
	 * The handlers associated with the root logger.
	 */
	public Root handlers(List value) {
		this.handlers = value;
		return this;
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
	public Root level(String value) {
		this.level = value;
		return this;
	}
}