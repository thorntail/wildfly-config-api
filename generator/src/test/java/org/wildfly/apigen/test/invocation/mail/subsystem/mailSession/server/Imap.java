package org.wildfly.swarm.config.mail.subsystem.mailSession.server;

import org.wildfly.apigen.invocation.Implicit;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
/**
 * Mail session server
 */
@Address("/subsystem=mail/mail-session=*/server=imap")
@Implicit
public class Imap {

	private String key;
	private String outboundSocketBindingRef;
	private String password;
	private Boolean ssl;
	private Boolean tls;
	private String username;

	public Imap() {
		this.key = "imap";
	}

	public String getKey() {
		return this.key;
	}

	/**
	 * Outbound Socket binding to mail server
	 */
	@Binding(detypedName = "outbound-socket-binding-ref")
	public String outboundSocketBindingRef() {
		return this.outboundSocketBindingRef;
	}

	/**
	 * Outbound Socket binding to mail server
	 */
	public Imap outboundSocketBindingRef(String value) {
		this.outboundSocketBindingRef = value;
		return this;
	}

	/**
	 * Password to authenticate on server
	 */
	@Binding(detypedName = "password")
	public String password() {
		return this.password;
	}

	/**
	 * Password to authenticate on server
	 */
	public Imap password(String value) {
		this.password = value;
		return this;
	}

	/**
	 * Does server require SSL?
	 */
	@Binding(detypedName = "ssl")
	public Boolean ssl() {
		return this.ssl;
	}

	/**
	 * Does server require SSL?
	 */
	public Imap ssl(Boolean value) {
		this.ssl = value;
		return this;
	}

	/**
	 * Does server require TLS?
	 */
	@Binding(detypedName = "tls")
	public Boolean tls() {
		return this.tls;
	}

	/**
	 * Does server require TLS?
	 */
	public Imap tls(Boolean value) {
		this.tls = value;
		return this;
	}

	/**
	 * Username to authenticate on server
	 */
	@Binding(detypedName = "username")
	public String username() {
		return this.username;
	}

	/**
	 * Username to authenticate on server
	 */
	public Imap username(String value) {
		this.username = value;
		return this;
	}
}