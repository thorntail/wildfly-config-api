package org.wildfly.apigen.test.invocation.logging.subsystem.sizeRotatingFileHandler;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import java.util.Map;
/**
 * Defines a handler which writes to a file, rotating the log after the size of the file grows beyond a certain point and keeping a fixed number of backups.
 */
@Address("/subsystem=logging/size-rotating-file-handler=*")
public class SizeRotatingFileHandler {

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
	@Binding(detypedName = "append")
	public Boolean append() {
		return this.append;
	}

	/**
	 * Specify whether to append to the target file.
	 */
	public SizeRotatingFileHandler append(Boolean value) {
		this.append = value;
		return this;
	}

	/**
	 * Automatically flush after each write.
	 */
	@Binding(detypedName = "autoflush")
	public Boolean autoflush() {
		return this.autoflush;
	}

	/**
	 * Automatically flush after each write.
	 */
	public SizeRotatingFileHandler autoflush(Boolean value) {
		this.autoflush = value;
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
	public SizeRotatingFileHandler enabled(Boolean value) {
		this.enabled = value;
		return this;
	}

	/**
	 * The character encoding used by this Handler.
	 */
	@Binding(detypedName = "encoding")
	public String encoding() {
		return this.encoding;
	}

	/**
	 * The character encoding used by this Handler.
	 */
	public SizeRotatingFileHandler encoding(String value) {
		this.encoding = value;
		return this;
	}

	/**
	 * The file description consisting of the path and optional relative to path.
	 */
	@Binding(detypedName = "file")
	public Map file() {
		return this.file;
	}

	/**
	 * The file description consisting of the path and optional relative to path.
	 */
	public SizeRotatingFileHandler file(Map value) {
		this.file = value;
		return this;
	}

	/**
	 * Defines a simple filter type.
	 */
	@Binding(detypedName = "filter")
	public Map filter() {
		return this.filter;
	}

	/**
	 * Defines a simple filter type.
	 */
	public SizeRotatingFileHandler filter(Map value) {
		this.filter = value;
		return this;
	}

	/**
	 * A filter expression value to define a filter. Example for a filter that does not match a pattern: not(match("JBAS.*"))
	 */
	@Binding(detypedName = "filter-spec")
	public String filterSpec() {
		return this.filterSpec;
	}

	/**
	 * A filter expression value to define a filter. Example for a filter that does not match a pattern: not(match("JBAS.*"))
	 */
	public SizeRotatingFileHandler filterSpec(String value) {
		this.filterSpec = value;
		return this;
	}

	/**
	 * Defines a pattern for the formatter.
	 */
	@Binding(detypedName = "formatter")
	public String formatter() {
		return this.formatter;
	}

	/**
	 * Defines a pattern for the formatter.
	 */
	public SizeRotatingFileHandler formatter(String value) {
		this.formatter = value;
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
	public SizeRotatingFileHandler level(String value) {
		this.level = value;
		return this;
	}

	/**
	 * The maximum number of backups to keep.
	 */
	@Binding(detypedName = "max-backup-index")
	public Integer maxBackupIndex() {
		return this.maxBackupIndex;
	}

	/**
	 * The maximum number of backups to keep.
	 */
	public SizeRotatingFileHandler maxBackupIndex(Integer value) {
		this.maxBackupIndex = value;
		return this;
	}

	/**
	 * The name of the handler.
	 */
	@Binding(detypedName = "name")
	public String name() {
		return this.name;
	}

	/**
	 * The name of the handler.
	 */
	public SizeRotatingFileHandler name(String value) {
		this.name = value;
		return this;
	}

	/**
	 * The name of the defined formatter to be used on the handler.
	 */
	@Binding(detypedName = "named-formatter")
	public String namedFormatter() {
		return this.namedFormatter;
	}

	/**
	 * The name of the defined formatter to be used on the handler.
	 */
	public SizeRotatingFileHandler namedFormatter(String value) {
		this.namedFormatter = value;
		return this;
	}

	/**
	 * Indicates the file should be rotated each time the file attribute is changed. This always happens when at initialization time.
	 */
	@Binding(detypedName = "rotate-on-boot")
	public Boolean rotateOnBoot() {
		return this.rotateOnBoot;
	}

	/**
	 * Indicates the file should be rotated each time the file attribute is changed. This always happens when at initialization time.
	 */
	public SizeRotatingFileHandler rotateOnBoot(Boolean value) {
		this.rotateOnBoot = value;
		return this;
	}

	/**
	 * The size at which to rotate the log file.
	 */
	@Binding(detypedName = "rotate-size")
	public String rotateSize() {
		return this.rotateSize;
	}

	/**
	 * The size at which to rotate the log file.
	 */
	public SizeRotatingFileHandler rotateSize(String value) {
		this.rotateSize = value;
		return this;
	}

	/**
	 * Set the suffix string. The string is in a format which can be understood by java.text.SimpleDateFormat. The suffix does not determine when the file should be rotated.
	 */
	@Binding(detypedName = "suffix")
	public String suffix() {
		return this.suffix;
	}

	/**
	 * Set the suffix string. The string is in a format which can be understood by java.text.SimpleDateFormat. The suffix does not determine when the file should be rotated.
	 */
	public SizeRotatingFileHandler suffix(String value) {
		this.suffix = value;
		return this;
	}
}