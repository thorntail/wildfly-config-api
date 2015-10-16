package org.wildfly.apigen.test.invocation.mail;

import org.wildfly.swarm.config.runtime.Implicit;
import org.wildfly.swarm.config.runtime.Address;

import java.util.List;
import org.wildfly.swarm.config.runtime.Subresource;
import org.wildfly.swarm.config.runtime.ModelNodeSubresources;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.MailSession;

/**
 * The mail subsystem
 */
@Address("/subsystem=mail")
@Implicit
public class Mail<T extends Mail> {

	private String key;
	private MailResources subresources = new MailResources();

	public Mail() {
		this.key = "mail";
	}

	public String getKey() {
		return this.key;
	}

	public MailResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all MailSession objects to this subresource
	 * @return this
	 * @param value List of MailSession objects.
	 */
	@SuppressWarnings("unchecked")
	public T mailSessions(List<MailSession> value) {
		this.subresources.mailSessions.addAll(value);
		return (T) this;
	}

	/**
	 * Add the MailSession object to the list of subresources
	 * @param value The MailSession to add
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T mailSession(MailSession value) {
		this.subresources.mailSessions.add(value);
		return (T) this;
	}

	/**
	 * Child mutators for Mail
	 */
	@ModelNodeSubresources
	public class MailResources {
		/**
		 * Mail session definition
		 */
		private List<MailSession> mailSessions = new java.util.ArrayList<>();

		/**
		 * Get the list of MailSession resources
		 * @return the list of resources
		 */
		@Subresource
		public List<MailSession> mailSessions() {
			return this.mailSessions;
		}
	}
}