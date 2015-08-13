package org.wildfly.apigen.test.invocation.mail;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import java.util.List;
import org.wildfly.apigen.invocation.Subresource;
/**
 * Mail session definition
 */
@Address("/subsystem=mail/mail-session=*")
public class MailSession {

	private Boolean debug;
	private String from;
	private String jndiName;
	private MailSessionResources subresources = new MailSessionResources();
	private String key;

	public MailSession(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
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

	public MailSessionResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all Server objects to this subresource
	 * @return this
	 * @param value List of Server objects.
	 */
	public MailSession servers(List<Server> value) {
		this.subresources.servers.addAll(value);
		return this;
	}

	/**
	 * Add the Server object to the list of subresources
	 * @param value The Server to add
	 * @return this
	 */
	public MailSession servers(Server value) {
		this.subresources.servers.add(value);
		return this;
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
	class MailSessionResources {
		/**
		 * undefined
		 */
		private List<Server> servers = new java.util.ArrayList<>();
		/**
		 * Mail session server
		 */
		private List<Custom> customs = new java.util.ArrayList<>();

		/**
		 * Get the list of Server resources
		 * @return the list of resources
		 */
		@Subresource
		public List<Server> servers() {
			return this.servers;
		}

		/**
		 * Get the list of Custom resources
		 * @return the list of resources
		 */
		@Subresource
		public List<Custom> customs() {
			return this.customs;
		}
	}
}