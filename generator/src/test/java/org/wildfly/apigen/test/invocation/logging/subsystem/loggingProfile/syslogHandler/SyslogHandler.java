package org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.syslogHandler;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
/**
 * Defines a syslog handler.
 */
@Address("/subsystem=logging/logging-profile=*/syslog-handler=*")
public class SyslogHandler {

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
	@Binding(detypedName = "app-name")
	public String appName() {
		return this.appName;
	}

	/**
	 * The app name used when formatting the message in RFC5424 format. By default the app name is "java".
	 */
	public SyslogHandler appName(String value) {
		this.appName = value;
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
	public SyslogHandler enabled(Boolean value) {
		this.enabled = value;
		return this;
	}

	/**
	 * Facility as defined by RFC-5424 (http://tools.ietf.org/html/rfc5424)and RFC-3164 (http://tools.ietf.org/html/rfc3164).
	 */
	@Binding(detypedName = "facility")
	public String facility() {
		return this.facility;
	}

	/**
	 * Facility as defined by RFC-5424 (http://tools.ietf.org/html/rfc5424)and RFC-3164 (http://tools.ietf.org/html/rfc3164).
	 */
	public SyslogHandler facility(String value) {
		this.facility = value;
		return this;
	}

	/**
	 * The name of the host the messages are being sent from. For example the name of the host the application server is running on.
	 */
	@Binding(detypedName = "hostname")
	public String hostname() {
		return this.hostname;
	}

	/**
	 * The name of the host the messages are being sent from. For example the name of the host the application server is running on.
	 */
	public SyslogHandler hostname(String value) {
		this.hostname = value;
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
	public SyslogHandler level(String value) {
		this.level = value;
		return this;
	}

	/**
	 * The port the syslog server is listening on.
	 */
	@Binding(detypedName = "port")
	public Integer port() {
		return this.port;
	}

	/**
	 * The port the syslog server is listening on.
	 */
	public SyslogHandler port(Integer value) {
		this.port = value;
		return this;
	}

	/**
	 * The address of the syslog server.
	 */
	@Binding(detypedName = "server-address")
	public String serverAddress() {
		return this.serverAddress;
	}

	/**
	 * The address of the syslog server.
	 */
	public SyslogHandler serverAddress(String value) {
		this.serverAddress = value;
		return this;
	}

	/**
	 * Formats the log message according to the RFC specification.
	 */
	@Binding(detypedName = "syslog-format")
	public String syslogFormat() {
		return this.syslogFormat;
	}

	/**
	 * Formats the log message according to the RFC specification.
	 */
	public SyslogHandler syslogFormat(String value) {
		this.syslogFormat = value;
		return this;
	}
}