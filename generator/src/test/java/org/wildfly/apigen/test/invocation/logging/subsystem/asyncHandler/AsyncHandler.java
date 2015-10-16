package org.wildfly.apigen.test.invocation.logging.subsystem.asyncHandler;

import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;
import java.util.Map;
import java.util.List;
/**
 * Defines a handler which writes to the sub-handlers in an asynchronous thread. Used for handlers which introduce a substantial amount of lag.
 */
@Address("/subsystem=logging/async-handler=*")
public class AsyncHandler<T extends AsyncHandler> {

	private String key;
	private Boolean enabled;
	private Map filter;
	private String filterSpec;
	private String level;
	private String name;
	private String overflowAction;
	private Integer queueLength;
	private List<String> subhandlers;

	public AsyncHandler(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
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
	 * The log level specifying which message levels will be logged by this handler. Message levels lower than this value will be discarded.
	 */
	@ModelNodeBinding(detypedName = "level")
	public String level() {
		return this.level;
	}

	/**
	 * The log level specifying which message levels will be logged by this handler. Message levels lower than this value will be discarded.
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
	 * Specify what action to take when the overflowing.  The valid options are 'block' and 'discard'
	 */
	@ModelNodeBinding(detypedName = "overflow-action")
	public String overflowAction() {
		return this.overflowAction;
	}

	/**
	 * Specify what action to take when the overflowing.  The valid options are 'block' and 'discard'
	 */
	@SuppressWarnings("unchecked")
	public T overflowAction(String value) {
		this.overflowAction = value;
		return (T) this;
	}

	/**
	 * The queue length to use before flushing writing
	 */
	@ModelNodeBinding(detypedName = "queue-length")
	public Integer queueLength() {
		return this.queueLength;
	}

	/**
	 * The queue length to use before flushing writing
	 */
	@SuppressWarnings("unchecked")
	public T queueLength(Integer value) {
		this.queueLength = value;
		return (T) this;
	}

	/**
	 * The Handlers associated with this async handler.
	 */
	@ModelNodeBinding(detypedName = "subhandlers")
	public List<String> subhandlers() {
		return this.subhandlers;
	}

	/**
	 * The Handlers associated with this async handler.
	 */
	@SuppressWarnings("unchecked")
	public T subhandlers(List<String> value) {
		this.subhandlers = value;
		return (T) this;
	}
}