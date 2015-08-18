package org.wildfly.swarm.config.mail.subsystem.mailSession.server;

import org.wildfly.apigen.invocation.Implicit;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;
/**
 * Mail session server
 */
@Address("/subsystem=mail/mail-session=*/server=smtp")
@Implicit
public class Smtp {

	private String key;
	private String outboundSocketBindingRef;
	private String password;
	private Boolean ssl;
	private Boolean tls;
	private String username;

	public Smtp() {
		this.key = "smtp";
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
	public Smtp outboundSocketBindingRef(String value) {
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
	public Smtp password(String value) {
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
	public Smtp ssl(Boolean value) {
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
	public Smtp tls(Boolean value) {
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
	public Smtp username(String value) {
		this.username = value;
		return this;
	}
}