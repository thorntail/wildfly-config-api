package org.wildfly.apigen.test.invocation;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.dmr.ModelNode;
import org.junit.Before;
import org.junit.Test;
import org.wildfly.apigen.invocation.Marshaller;
import org.wildfly.apigen.test.AbstractTestCase;
import org.wildfly.apigen.test.invocation.mail.Mail;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.MailSession;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.custom.Custom;

import java.util.ArrayList;
import java.util.List;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * @author Lance Ball
 */
public class MarshallerTestCase extends AbstractTestCase {
    private Mail mail;
    private MailSession mailSession;
    private PathAddress smtpServerAddress = PathAddress.pathAddress(PathElement.pathElement(SUBSYSTEM, "mail"));

    @Before
    public void fixture() {
        mail = new Mail();
        mailSession = new MailSession("Unicorn Mail");
        mailSession.debug(true);
        mailSession.from("sparky@rainbow.com");
        mailSession.jndiName("java:/mail/Test");
        mail.mailSessions(mailSession);
        mailSession.customs(new Custom("CUSTOM").username("elmer").password("fudd"));
    }

    @Test
    public void testMarshalling() throws Exception {
        List<ModelNode> list = Marshaller.marshal(mail);
        for (ModelNode node : list) {
            System.out.println(node);
        }
    }

    @Test
    public void testModelNodeOutput() {
        // This is not so much a test as it is visual confirmation of a correctly configured model node chain for swarm
        List<ModelNode> list = new ArrayList<>();

        ModelNode node = new ModelNode();
        node.get(OP_ADDR).set(EXTENSION, "org.jboss.as.mail");
        node.get(OP).set(ADD);
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(smtpServerAddress.toModelNode());
        node.get(OP).set(ADD);
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(smtpServerAddress.append("mail-session", "smtpserver-name").toModelNode());
        node.get(OP).set(ADD);
        node.get("jndi-name").set("smtpserver-jndi-name");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(smtpServerAddress.append("mail-session", "smtpserver-name").append("server", "smtp").toModelNode());
        node.get(OP).set(ADD);
        node.get("outbound-socket-binding-ref").set("smtpserver-socket-binding-ref");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(PathAddress.pathAddress("socket-binding-group", "default-sockets").append("remote-destination-outbound-socket-binding", "smtpserver-socket-binding-ref").toModelNode());
        node.get(OP).set(ADD);
        node.get("host").set("hostname");
        node.get("port").set("25");
        list.add(node);

        for (ModelNode n : list) {
            System.out.println(n);
        }
    }
}