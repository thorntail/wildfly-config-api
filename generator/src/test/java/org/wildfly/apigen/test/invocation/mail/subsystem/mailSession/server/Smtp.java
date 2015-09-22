package org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.server;

import org.wildfly.apigen.invocation.Implicit;
import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
/**
 * Mail session server
 */
@Address("/subsystem=mail/mail-session=*/server=smtp")
@Implicit
public class Smtp<T extends Smtp> {

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
	@ModelNodeBinding(detypedName = "outbound-socket-binding-ref")
	public String outboundSocketBindingRef() {
		return this.outboundSocketBindingRef;
	}

	/**
	 * Outbound Socket binding to mail server
	 */
	@SuppressWarnings("unchecked")
	public T outboundSocketBindingRef(String value) {
		this.outboundSocketBindingRef = value;
		return (T) this;
	}

	/**
	 * Password to authenticate on server
	 */
	@ModelNodeBinding(detypedName = "password")
	public String password() {
		return this.password;
	}

	/**
	 * Password to authenticate on server
	 */
	@SuppressWarnings("unchecked")
	public T password(String value) {
		this.password = value;
		return (T) this;
	}

	/**
	 * Does server require SSL?
	 */
	@ModelNodeBinding(detypedName = "ssl")
	public Boolean ssl() {
		return this.ssl;
	}

	/**
	 * Does server require SSL?
	 */
	@SuppressWarnings("unchecked")
	public T ssl(Boolean value) {
		this.ssl = value;
		return (T) this;
	}

	/**
	 * Does server require TLS?
	 */
	@ModelNodeBinding(detypedName = "tls")
	public Boolean tls() {
		return this.tls;
	}

	/**
	 * Does server require TLS?
	 */
	@SuppressWarnings("unchecked")
	public T tls(Boolean value) {
		this.tls = value;
		return (T) this;
	}

	/**
	 * Username to authenticate on server
	 */
	@ModelNodeBinding(detypedName = "username")
	public String username() {
		return this.username;
	}

	/**
	 * Username to authenticate on server
	 */
	@SuppressWarnings("unchecked")
	public T username(String value) {
		this.username = value;
		return (T) this;
	}
}