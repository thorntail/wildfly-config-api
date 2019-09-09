package org.wildfly.swarm.config.generator.generator;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class AuthCallback implements CallbackHandler{
    private final String name;
    private final String password;

    public AuthCallback(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
        for (Callback current : callbacks) {
            if (current instanceof NameCallback) {
                NameCallback ncb = (NameCallback) current;
                ncb.setName(name);
            } else if (current instanceof PasswordCallback) {
                PasswordCallback pcb = (PasswordCallback) current;
                pcb.setPassword(password.toCharArray());
            } else if (current instanceof RealmCallback) {
                RealmCallback rcb = (RealmCallback) current;
                rcb.setText(rcb.getDefaultText());
            } else {
                throw new UnsupportedCallbackException(current);
            }
        }
    }
}
