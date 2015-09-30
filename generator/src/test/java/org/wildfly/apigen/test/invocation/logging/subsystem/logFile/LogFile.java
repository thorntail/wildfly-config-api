package org.wildfly.apigen.test.invocation.logging.subsystem.logFile;

import org.wildfly.config.runtime.Address;
import org.wildfly.config.runtime.ModelNodeBinding;
/**
 * Log files that are available to be read.
 */
@Address("/subsystem=logging/log-file=*")
public class LogFile<T extends LogFile> {

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
	@ModelNodeBinding(detypedName = "file-size")
	public Long fileSize() {
		return this.fileSize;
	}

	/**
	 * The size of the log file in bytes.
	 */
	@SuppressWarnings("unchecked")
	public T fileSize(Long value) {
		this.fileSize = value;
		return (T) this;
	}

	/**
	 * The date, in milliseconds, the file was last modified.
	 */
	@ModelNodeBinding(detypedName = "last-modified-time")
	public Long lastModifiedTime() {
		return this.lastModifiedTime;
	}

	/**
	 * The date, in milliseconds, the file was last modified.
	 */
	@SuppressWarnings("unchecked")
	public T lastModifiedTime(Long value) {
		this.lastModifiedTime = value;
		return (T) this;
	}

	/**
	 * The date, in ISO 8601 format, the file was last modified.
	 */
	@ModelNodeBinding(detypedName = "last-modified-timestamp")
	public String lastModifiedTimestamp() {
		return this.lastModifiedTimestamp;
	}

	/**
	 * The date, in ISO 8601 format, the file was last modified.
	 */
	@SuppressWarnings("unchecked")
	public T lastModifiedTimestamp(String value) {
		this.lastModifiedTimestamp = value;
		return (T) this;
	}

	/**
	 * Provides the server log as a response attachment. The response result value is the unique id of the attachment.
	 */
	@ModelNodeBinding(detypedName = "stream")
	public Long stream() {
		return this.stream;
	}

	/**
	 * Provides the server log as a response attachment. The response result value is the unique id of the attachment.
	 */
	@SuppressWarnings("unchecked")
	public T stream(Long value) {
		this.stream = value;
		return (T) this;
	}
}