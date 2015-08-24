package org.wildfly.apigen.test.invocation.mail.subsystem.mailSession;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import org.wildfly.apigen.invocation.Subresource;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.custom.Custom;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.server.Imap;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.server.Pop3;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.server.Smtp;

import java.util.List;
/**
 * Mail session definition
 */
@Address("/subsystem=mail/mail-session=*")
public class MailSession {

	private String key;
	private Boolean debug;
	private String from;
	private String jndiName;
	private MailSessionResources subresources = new MailSessionResources();
	private Pop3 pop3;
	private Smtp smtp;
	private Imap imap;

	public MailSession(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * Enables JavaMail debugging
	 */
	@Binding(detypedName = "debug")
	public Boolean debug() {
		return this.debug;
	}

	/**
	 * Enables JavaMail debugging
	 */
	public MailSession debug(Boolean value) {
		this.debug = value;
		return this;
	}

	/**
	 * From address that is used as default from, if not set when sending
	 */
	@Binding(detypedName = "from")
	public String from() {
		return this.from;
	}

	/**
	 * From address that is used as default from, if not set when sending
	 */
	public MailSession from(String value) {
		this.from = value;
		return this;
	}

	/**
	 * JNDI name to where mail session should be bound
	 */
	@Binding(detypedName = "jndi-name")
	public String jndiName() {
		return this.jndiName;
	}

	/**
	 * JNDI name to where mail session should be bound
	 */
	public MailSession jndiName(String value) {
		this.jndiName = value;
		return this;
	}

	@Subresource
	public MailSessionResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all Custom objects to this subresource
	 * @return this
	 * @param value List of Custom objects.
	 */
	public MailSession customs(List<Custom> value) {
		this.subresources.customs.addAll(value);
		return this;
	}

	/**
	 * Add the Custom object to the list of subresources
	 * @param value The Custom to add
	 * @return this
	 */
	public MailSession customs(Custom value) {
		this.subresources.customs.add(value);
		return this;
	}

	/**
	 * Child mutators for MailSession
	 */
	public class MailSessionResources {
		/**
		 * Mail session server
		 */
		private List<Custom> customs = new java.util.ArrayList<>();

		/**
		 * Get the list of Custom resources
		 * @return the list of resources
		 */
		@Subresource
		public List<Custom> customs() {
			return this.customs;
		}
	}

	/**
	 * Mail session server
	 */
	@Subresource
	public Pop3 pop3() {
		return this.pop3;
	}

	/**
	 * Mail session server
	 */
	public MailSession pop3(Pop3 value) {
		this.pop3 = value;
		return this;
	}

	/**
	 * Mail session server
	 */
	@Subresource
	public Smtp smtp() {
		return this.smtp;
	}

	/**
	 * Mail session server
	 */
	public MailSession smtp(Smtp value) {
		this.smtp = value;
		return this;
	}

	/**
	 * Mail session server
	 */
	@Subresource
	public Imap imap() {
		return this.imap;
	}

	/**
	 * Mail session server
	 */
	public MailSession imap(Imap value) {
		this.imap = value;
		return this;
	}
}