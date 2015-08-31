package org.wildfly.apigen.test.invocation.logging.subsystem.loggingProfile.logFile;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
/**
 * Log files that are available to be read.
 */
@Address("/subsystem=logging/logging-profile=*/log-file=*")
public class LogFile {

	private String key;
	private Long fileSize;
	private Long lastModifiedTime;
	private String lastModifiedTimestamp;
	private Long stream;

	public LogFile(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * The size of the log file in bytes.
	 */
	@Binding(detypedName = "file-size")
	public Long fileSize() {
		return this.fileSize;
	}

	/**
	 * The size of the log file in bytes.
	 */
	public LogFile fileSize(Long value) {
		this.fileSize = value;
		return this;
	}

	/**
	 * The date, in milliseconds, the file was last modified.
	 */
	@Binding(detypedName = "last-modified-time")
	public Long lastModifiedTime() {
		return this.lastModifiedTime;
	}

	/**
	 * The date, in milliseconds, the file was last modified.
	 */
	public LogFile lastModifiedTime(Long value) {
		this.lastModifiedTime = value;
		return this;
	}

	/**
	 * The date, in ISO 8601 format, the file was last modified.
	 */
	@Binding(detypedName = "last-modified-timestamp")
	public String lastModifiedTimestamp() {
		return this.lastModifiedTimestamp;
	}

	/**
	 * The date, in ISO 8601 format, the file was last modified.
	 */
	public LogFile lastModifiedTimestamp(String value) {
		this.lastModifiedTimestamp = value;
		return this;
	}

	/**
	 * Provides the server log as a response attachment. The response result value is the unique id of the attachment.
	 */
	@Binding(detypedName = "stream")
	public Long stream() {
		return this.stream;
	}

	/**
	 * Provides the server log as a response attachment. The response result value is the unique id of the attachment.
	 */
	public LogFile stream(Long value) {
		this.stream = value;
		return this;
	}
}