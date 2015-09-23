package org.wildfly.apigen.test.invocation.logging.subsystem.patternFormatter;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
/**
 * A pattern formatter to be used with handlers.
 */
@Address("/subsystem=logging/pattern-formatter=*")
public class PatternFormatter<T extends PatternFormatter> {

	private String key;
	private String colorMap;
	private String pattern;

	public PatternFormatter(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * The color-map attribute allows for a comma delimited list of colors to be used for different levels with a pattern formatter. The format for the color mapping pattern is level-name:color-name.Valid Levels; severe, fatal, error, warn, warning, info, debug, trace, config, fine, finer, finest Valid Colors; black, green, red, yellow, blue, magenta, cyan, white, brightblack, brightred, brightgreen, brightblue, brightyellow, brightmagenta, brightcyan, brightwhite
	 */
	@ModelNodeBinding(detypedName = "color-map")
	public String colorMap() {
		return this.colorMap;
	}

	/**
	 * The color-map attribute allows for a comma delimited list of colors to be used for different levels with a pattern formatter. The format for the color mapping pattern is level-name:color-name.Valid Levels; severe, fatal, error, warn, warning, info, debug, trace, config, fine, finer, finest Valid Colors; black, green, red, yellow, blue, magenta, cyan, white, brightblack, brightred, brightgreen, brightblue, brightyellow, brightmagenta, brightcyan, brightwhite
	 */
	@SuppressWarnings("unchecked")
	public T colorMap(String value) {
		this.colorMap = value;
		return (T) this;
	}

	/**
	 * Defines a pattern for the formatter.
	 */
	@ModelNodeBinding(detypedName = "pattern")
	public String pattern() {
		return this.pattern;
	}

	/**
	 * Defines a pattern for the formatter.
	 */
	@SuppressWarnings("unchecked")
	public T pattern(String value) {
		this.pattern = value;
		return (T) this;
	}
}