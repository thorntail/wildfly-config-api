package org.wildfly.apigen.test.invocation;

import org.jboss.dmr.ModelNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.apigen.invocation.EntityAdapter;
import org.wildfly.apigen.test.AbstractTestCase;
import org.wildfly.apigen.test.invocation.mail.Mail;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.MailSession;


/**
 * Verifies the Model API helper classes and it usage
 *
 * @author Heiko Braun
 * @since 29/07/15
 */
public class EntityAdapterTestCase extends AbstractTestCase {


    private Mail mail;
    private MailSession mailSession;

    @Before
    public void fixture() {
        mail = new Mail();
        mailSession = new MailSession("TestMail");
        mailSession.debug(true);
        mailSession.from("john@doe.com");
        mailSession.jndiName("java:/mail/Test");
        mail.mailSession(mailSession);
    }

    @Test
    public void testSimpleResourceMarshalling() throws Exception {
        EntityAdapter<MailSession> entityAdapter = new EntityAdapter<>(MailSession.class);
        ModelNode modelNode = entityAdapter.fromEntity(mailSession);
        Assert.assertTrue(modelNode.get("debug").asBoolean() == true);
        Assert.assertTrue(modelNode.get("from").asString().equals("john@doe.com"));
        Assert.assertTrue(modelNode.get("jndi-name").asString().equals("java:/mail/Test"));

        MailSession session = entityAdapter.fromDMR("key", modelNode);
        Assert.assertTrue(session.debug() == true);
        Assert.assertTrue(session.from().equals("john@doe.com"));
        Assert.assertTrue(session.jndiName().equals("java:/mail/Test"));

        System.out.println(modelNode);
    }

    @Test
    public void testComplexResourceMarshalling() throws Exception {
        EntityAdapter<Mail> adapter = new EntityAdapter<>(Mail.class);
        ModelNode node = adapter.fromEntity(mail);
        System.out.println(node);
    }
}
