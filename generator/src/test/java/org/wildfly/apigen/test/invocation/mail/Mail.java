package org.wildfly.apigen.test.invocation.mail;

import org.wildfly.apigen.invocation.Implicit;
import org.wildfly.apigen.invocation.Address;

import java.util.List;
import org.wildfly.apigen.invocation.Subresource;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.MailSession;
/**
 * The mail subsystem
 */
@Address("/subsystem=mail")
@Implicit
public class Mail {

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
	public Mail mailSessions(List<MailSession> value) {
		this.subresources.mailSessions.addAll(value);
		return this;
	}

	/**
	 * Add the MailSession object to the list of subresources
	 * @param value The MailSession to add
	 * @return this
	 */
	public Mail mailSession(MailSession value) {
		this.subresources.mailSessions.add(value);
		return this;
	}

	/**
	 * Child mutators for Mail
	 */
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