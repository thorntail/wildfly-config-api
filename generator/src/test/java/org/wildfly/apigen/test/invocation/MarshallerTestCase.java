package org.wildfly.apigen.test.invocation;

import org.jboss.dmr.ModelNode;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.apigen.invocation.Marshaller;
import org.wildfly.apigen.test.AbstractTestCase;
import org.wildfly.swarm.config.mail.Mail;
import org.wildfly.swarm.config.mail.subsystem.mailSession.MailSession;

import java.util.List;

/**
 * @author Lance Ball
 */
public class MarshallerTestCase extends AbstractTestCase {
    private Mail mail;
    private MailSession mailSession;

    @Before
    public void fixture() {
        mail = new Mail();
        mailSession = new MailSession("Unicorn Mail");
        mailSession.debug(true);
        mailSession.from("sparky@rainbow.com");
        mailSession.jndiName("java:/mail/Test");
        mail.mailSessions(mailSession);
    }

    @Test
    public void testMarshalling() throws Exception {
        List<ModelNode> list = Marshaller.marshal(mail);
        for (ModelNode node : list) {
            System.out.println(node);
        }
    }
}