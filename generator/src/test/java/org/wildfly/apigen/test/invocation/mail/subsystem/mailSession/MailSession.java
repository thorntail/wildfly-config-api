package org.wildfly.apigen.test.invocation.mail.subsystem.mailSession;

import org.wildfly.config.runtime.Address;
import org.wildfly.config.runtime.ModelNodeBinding;
import java.util.List;
import org.wildfly.config.runtime.Subresource;
import org.wildfly.config.runtime.ModelNodeSubresources;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.custom.Custom;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.server.Pop3;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.server.Smtp;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.server.Imap;
/**
 * Mail session definition
 */
@Address("/subsystem=mail/mail-session=*")
public class MailSession<T extends MailSession> {

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
	@ModelNodeBinding(detypedName = "debug")
	public Boolean debug() {
		return this.debug;
	}

	/**
	 * Enables JavaMail debugging
	 */
	@SuppressWarnings("unchecked")
	public T debug(Boolean value) {
		this.debug = value;
		return (T) this;
	}

	/**
	 * From address that is used as default from, if not set when sending
	 */
	@ModelNodeBinding(detypedName = "from")
	public String from() {
		return this.from;
	}

	/**
	 * From address that is used as default from, if not set when sending
	 */
	@SuppressWarnings("unchecked")
	public T from(String value) {
		this.from = value;
		return (T) this;
	}

	/**
	 * JNDI name to where mail session should be bound
	 */
	@ModelNodeBinding(detypedName = "jndi-name")
	public String jndiName() {
		return this.jndiName;
	}

	/**
	 * JNDI name to where mail session should be bound
	 */
	@SuppressWarnings("unchecked")
	public T jndiName(String value) {
		this.jndiName = value;
		return (T) this;
	}

	public MailSessionResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all Custom objects to this subresource
	 * @return this
	 * @param value List of Custom objects.
	 */
	@SuppressWarnings("unchecked")
	public T customs(List<Custom> value) {
		this.subresources.customs.addAll(value);
		return (T) this;
	}

	/**
	 * Add the Custom object to the list of subresources
	 * @param value The Custom to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T custom(Custom value) {
		this.subresources.customs.add(value);
		return (T) this;
	}

	/**
	 * Child mutators for MailSession
	 */
	@ModelNodeSubresources
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
	@SuppressWarnings("unchecked")
	public T pop3(Pop3 value) {
		this.pop3 = value;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T smtp(Smtp value) {
		this.smtp = value;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T imap(Imap value) {
		this.imap = value;
		return (T) this;
	}
}