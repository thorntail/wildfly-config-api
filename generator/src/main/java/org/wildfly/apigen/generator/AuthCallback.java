package org.wildfly.apigen.generator;

import java.io.IOException;

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
    String[] args;

    public AuthCallback(String[] args) {
        this.args = args;
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback current : callbacks) {
            if (current instanceof NameCallback) {
                NameCallback ncb = (NameCallback) current;
                ncb.setName(args[0]);
            } else if (current instanceof PasswordCallback) {
                PasswordCallback pcb = (PasswordCallback) current;
                pcb.setPassword(args[1].toCharArray());
            } else if (current instanceof RealmCallback) {
                RealmCallback rcb = (RealmCallback) current;
                rcb.setText(rcb.getDefaultText());
            } else {
                throw new UnsupportedCallbackException(current);
            }
        }
    }
}
