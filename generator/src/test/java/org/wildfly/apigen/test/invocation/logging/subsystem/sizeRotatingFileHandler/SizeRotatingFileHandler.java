package org.wildfly.apigen.test.invocation.logging.subsystem.sizeRotatingFileHandler;

import org.wildfly.config.runtime.Address;
import org.wildfly.config.runtime.ModelNodeBinding;
import java.util.Map;
/**
 * Defines a handler which writes to a file, rotating the log after the size of the file grows beyond a certain point and keeping a fixed number of backups.
 */
@Address("/subsystem=logging/size-rotating-file-handler=*")
public class SizeRotatingFileHandler<T extends SizeRotatingFileHandler> {

	private String key;
	private Boolean append;
	private Boolean autoflush;
	private Boolean enabled;
	private String encoding;
	private Map file;
	private Map filter;
	private String filterSpec;
	private String formatter;
	private String level;
	private Integer maxBackupIndex;
	private String name;
	private String namedFormatter;
	private Boolean rotateOnBoot;
	private String rotateSize;
	private String suffix;

	public SizeRotatingFileHandler(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * Specify whether to append to the target file.
	 */
	@ModelNodeBinding(detypedName = "append")
	public Boolean append() {
		return this.append;
	}

	/**
	 * Specify whether to append to the target file.
	 */
	@SuppressWarnings("unchecked")
	public T append(Boolean value) {
		this.append = value;
		return (T) this;
	}

	/**
	 * Automatically flush after each write.
	 */
	@ModelNodeBinding(detypedName = "autoflush")
	public Boolean autoflush() {
		return this.autoflush;
	}

	/**
	 * Automatically flush after each write.
	 */
	@SuppressWarnings("unchecked")
	public T autoflush(Boolean value) {
		this.autoflush = value;
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
	 * The character encoding used by this Handler.
	 */
	@ModelNodeBinding(detypedName = "encoding")
	public String encoding() {
		return this.encoding;
	}

	/**
	 * The character encoding used by this Handler.
	 */
	@SuppressWarnings("unchecked")
	public T encoding(String value) {
		this.encoding = value;
		return (T) this;
	}

	/**
	 * The file description consisting of the path and optional relative to path.
	 */
	@ModelNodeBinding(detypedName = "file")
	public Map file() {
		return this.file;
	}

	/**
	 * The file description consisting of the path and optional relative to path.
	 */
	@SuppressWarnings("unchecked")
	public T file(Map value) {
		this.file = value;
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
	 * Defines a pattern for the formatter.
	 */
	@ModelNodeBinding(detypedName = "formatter")
	public String formatter() {
		return this.formatter;
	}

	/**
	 * Defines a pattern for the formatter.
	 */
	@SuppressWarnings("unchecked")
	public T formatter(String value) {
		this.formatter = value;
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
	 * The maximum number of backups to keep.
	 */
	@ModelNodeBinding(detypedName = "max-backup-index")
	public Integer maxBackupIndex() {
		return this.maxBackupIndex;
	}

	/**
	 * The maximum number of backups to keep.
	 */
	@SuppressWarnings("unchecked")
	public T maxBackupIndex(Integer value) {
		this.maxBackupIndex = value;
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
	 * The name of the defined formatter to be used on the handler.
	 */
	@ModelNodeBinding(detypedName = "named-formatter")
	public String namedFormatter() {
		return this.namedFormatter;
	}

	/**
	 * The name of the defined formatter to be used on the handler.
	 */
	@SuppressWarnings("unchecked")
	public T namedFormatter(String value) {
		this.namedFormatter = value;
		return (T) this;
	}

	/**
	 * Indicates the file should be rotated each time the file attribute is changed. This always happens when at initialization time.
	 */
	@ModelNodeBinding(detypedName = "rotate-on-boot")
	public Boolean rotateOnBoot() {
		return this.rotateOnBoot;
	}

	/**
	 * Indicates the file should be rotated each time the file attribute is changed. This always happens when at initialization time.
	 */
	@SuppressWarnings("unchecked")
	public T rotateOnBoot(Boolean value) {
		this.rotateOnBoot = value;
		return (T) this;
	}

	/**
	 * The size at which to rotate the log file.
	 */
	@ModelNodeBinding(detypedName = "rotate-size")
	public String rotateSize() {
		return this.rotateSize;
	}

	/**
	 * The size at which to rotate the log file.
	 */
	@SuppressWarnings("unchecked")
	public T rotateSize(String value) {
		this.rotateSize = value;
		return (T) this;
	}

	/**
	 * Set the suffix string. The string is in a format which can be understood by java.text.SimpleDateFormat. The suffix does not determine when the file should be rotated.
	 */
	@ModelNodeBinding(detypedName = "suffix")
	public String suffix() {
		return this.suffix;
	}

	/**
	 * Set the suffix string. The string is in a format which can be understood by java.text.SimpleDateFormat. The suffix does not determine when the file should be rotated.
	 */
	@SuppressWarnings("unchecked")
	public T suffix(String value) {
		this.suffix = value;
		return (T) this;
	}
}