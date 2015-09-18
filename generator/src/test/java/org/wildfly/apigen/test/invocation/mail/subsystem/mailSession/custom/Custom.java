package org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.custom;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.ModelNodeBinding;
/**
 * Mail session server
 */
@Address("/subsystem=mail/mail-session=*/custom=*")
public class Custom {

	private String key;
	private String outboundSocketBindingRef;
	private String password;
	private Boolean ssl;
	private Boolean tls;
	private String username;

	public Custom(String key) {
		this.key = key;
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
	public Custom outboundSocketBindingRef(String value) {
		this.outboundSocketBindingRef = value;
		return this;
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
	public Custom password(String value) {
		this.password = value;
		return this;
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
	public Custom ssl(Boolean value) {
		this.ssl = value;
		return this;
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
	public Custom tls(Boolean value) {
		this.tls = value;
		return this;
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
	public Custom username(String value) {
		this.username = value;
		return this;
	}
}