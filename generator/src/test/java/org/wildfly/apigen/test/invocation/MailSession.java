package org.wildfly.apigen.test.invocation;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;

@Address("/subsystem=mail/mail-session=*")
public class MailSession {

	private Boolean debug;
	private String from;
	private String jndiName;

	/**
	 * Enables JavaMail debugging
	 */
	@Binding(detypedName = "debug")
	public Boolean getDebug() {
		return debug;
	}

	/**
	 * Enables JavaMail debugging
	 */
	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	/**
	 * From address that is used as default from, if not set when sending
	 */
	@Binding(detypedName = "from")
	public String getFrom() {
		return from;
	}

	/**
	 * From address that is used as default from, if not set when sending
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * JNDI name to where mail session should be bound
	 */
	@Binding(detypedName = "jndi-name")
	public String getJndiName() {
		return jndiName;
	}

	/**
	 * JNDI name to where mail session should be bound
	 */
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

}
