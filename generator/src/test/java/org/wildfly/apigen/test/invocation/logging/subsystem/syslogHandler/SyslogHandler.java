package org.wildfly.apigen.test.invocation.logging.subsystem.syslogHandler;

import org.wildfly.config.runtime.Address;
import org.wildfly.config.runtime.ModelNodeBinding;
/**
 * Defines a syslog handler.
 */
@Address("/subsystem=logging/syslog-handler=*")
public class SyslogHandler<T extends SyslogHandler> {

	private String key;
	private String appName;
	private Boolean enabled;
	private String facility;
	private String hostname;
	private String level;
	private Integer port;
	private String serverAddress;
	private String syslogFormat;

	public SyslogHandler(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * The app name used when formatting the message in RFC5424 format. By default the app name is "java".
	 */
	@ModelNodeBinding(detypedName = "app-name")
	public String appName() {
		return this.appName;
	}

	/**
	 * The app name used when formatting the message in RFC5424 format. By default the app name is "java".
	 */
	@SuppressWarnings("unchecked")
	public T appName(String value) {
		this.appName = value;
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
	 * Facility as defined by RFC-5424 (http://tools.ietf.org/html/rfc5424)and RFC-3164 (http://tools.ietf.org/html/rfc3164).
	 */
	@ModelNodeBinding(detypedName = "facility")
	public String facility() {
		return this.facility;
	}

	/**
	 * Facility as defined by RFC-5424 (http://tools.ietf.org/html/rfc5424)and RFC-3164 (http://tools.ietf.org/html/rfc3164).
	 */
	@SuppressWarnings("unchecked")
	public T facility(String value) {
		this.facility = value;
		return (T) this;
	}

	/**
	 * The name of the host the messages are being sent from. For example the name of the host the application server is running on.
	 */
	@ModelNodeBinding(detypedName = "hostname")
	public String hostname() {
		return this.hostname;
	}

	/**
	 * The name of the host the messages are being sent from. For example the name of the host the application server is running on.
	 */
	@SuppressWarnings("unchecked")
	public T hostname(String value) {
		this.hostname = value;
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
	 * The port the syslog server is listening on.
	 */
	@ModelNodeBinding(detypedName = "port")
	public Integer port() {
		return this.port;
	}

	/**
	 * The port the syslog server is listening on.
	 */
	@SuppressWarnings("unchecked")
	public T port(Integer value) {
		this.port = value;
		return (T) this;
	}

	/**
	 * The address of the syslog server.
	 */
	@ModelNodeBinding(detypedName = "server-address")
	public String serverAddress() {
		return this.serverAddress;
	}

	/**
	 * The address of the syslog server.
	 */
	@SuppressWarnings("unchecked")
	public T serverAddress(String value) {
		this.serverAddress = value;
		return (T) this;
	}

	/**
	 * Formats the log message according to the RFC specification.
	 */
	@ModelNodeBinding(detypedName = "syslog-format")
	public String syslogFormat() {
		return this.syslogFormat;
	}

	/**
	 * Formats the log message according to the RFC specification.
	 */
	@SuppressWarnings("unchecked")
	public T syslogFormat(String value) {
		this.syslogFormat = value;
		return (T) this;
	}
}