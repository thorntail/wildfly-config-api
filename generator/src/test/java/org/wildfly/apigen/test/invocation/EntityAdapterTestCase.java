package org.wildfly.apigen.test.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.dmr.ValueExpression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.swarm.config.runtime.invocation.EntityAdapter;
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
    public void testExpressions() throws Exception {
        mailSession.put("from", "${mail.smtp.from:mary@doe.com}");
        EntityAdapter<MailSession> entityAdapter = new EntityAdapter<>(MailSession.class);
        ModelNode modelNode = entityAdapter.fromEntity(mailSession);

        System.out.println(modelNode);

        Assert.assertEquals(ModelType.EXPRESSION, modelNode.get("from").getType());


        // the other way around
        ModelNode payload = new ModelNode();
        payload.get("jndi-name").set(new ValueExpression("${mail.jndi.name}"));

        MailSession entity = entityAdapter.fromDMR("TestMail", payload);
        Assert.assertTrue("Expression for 'jndiName' has not been resolved", entity.keySet().contains("jndiName"));

    }

    @Test
    public void testSimpleResourceMarshalling() throws Exception {
        EntityAdapter<MailSession> entityAdapter = new EntityAdapter<>(MailSession.class);
        ModelNode modelNode = entityAdapter.fromEntity(mailSession);
        Assert.assertTrue(modelNode.get("debug").asBoolean());
        Assert.assertEquals("john@doe.com", modelNode.get("from").asString());
        Assert.assertEquals("java:/mail/Test", modelNode.get("jndi-name").asString());

        MailSession session = entityAdapter.fromDMR("key", modelNode);
        Assert.assertTrue(session.debug());
        Assert.assertEquals("john@doe.com", session.from());
        Assert.assertEquals("java:/mail/Test", session.jndiName());
    }

    @Test
    public void testComplexResourceMarshalling() throws Exception {
        EntityAdapter<Mail> adapter = new EntityAdapter<>(Mail.class);
        ModelNode node = adapter.fromEntity(mail);
//        System.out.println(node);
    }

    @Test
    public void testValueExpressions() throws Exception {
        System.setProperty("mail.test.jndi.name", "java:/mail/ValueTest");
        mailSession.jndiName("${mail.test.jndi.name}");
        EntityAdapter<MailSession> adapter = new EntityAdapter<>(MailSession.class);
        ModelNode node = adapter.fromEntity(mailSession);
        Assert.assertEquals("java:/mail/ValueTest", node.get("jndi-name").asString());
    }
}
