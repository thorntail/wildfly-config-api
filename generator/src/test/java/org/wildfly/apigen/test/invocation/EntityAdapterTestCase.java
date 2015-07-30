package org.wildfly.apigen.test.invocation;

import org.jboss.dmr.ModelNode;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.apigen.invocation.EntityAdapter;
import org.wildfly.apigen.test.AbstractTestCase;


/**
 * Verifies the Model API helper classes and it usage
 *
 * @author Heiko Braun
 * @since 29/07/15
 */
public class EntityAdapterTestCase extends AbstractTestCase {


    private MailSession mailSession;

    @Before
    public void fixture() {
        mailSession = new MailSession();
        mailSession.setDebug(true);
        mailSession.setFrom("john@doe.com");
        mailSession.setJndiName("java:/mail/Test");
    }

    @Test
    public void testSimpleResourceMarshalling() throws Exception {
        EntityAdapter<MailSession> entityAdapter = new EntityAdapter<>(MailSession.class);
        ModelNode modelNode = entityAdapter.fromEntity(mailSession);
        System.out.println(modelNode);
    }
}
