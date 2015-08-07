package org.wildfly.apigen.test.invocation.mail;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
import java.util.List;
import org.wildfly.apigen.invocation.Subresource;
/**
 * The mail subsystem
 */
@Address("/subsystem=mail")
public class Subsystem {

	private SubsystemResources subresources = new SubsystemResources();

	public SubsystemResources subresources() {
		return this.subresources;
	}

	/**
	 * Add all MailSession objects to this subresource
	 * @return this
	 * @param value List of MailSession objects.
	 */
	public Subsystem mailSessions(List<MailSession> value) {
		this.subresources.mailSessions.addAll(value);
		return this;
	}

	/**
	 * Add the MailSession object to the list of subresources
	 * @param value The MailSession to add
	 * @return this
	 */
	public Subsystem mailSessions(MailSession value) {
		this.subresources.mailSessions.add(value);
		return this;
	}

	/**
	 * Child mutators for Subsystem
	 */
	class SubsystemResources {
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