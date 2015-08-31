package org.wildfly.apigen.test.invocation;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ValueExpression;
import org.junit.Test;
import org.wildfly.apigen.invocation.Marshaller;
import org.wildfly.apigen.test.AbstractTestCase;
import org.wildfly.apigen.test.invocation.logging.Logging;
import org.wildfly.apigen.test.invocation.logging.subsystem.consoleHandler.ConsoleHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.customFormatter.CustomFormatter;
import org.wildfly.apigen.test.invocation.logging.subsystem.customHandler.CustomHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.fileHandler.FileHandler;
import org.wildfly.apigen.test.invocation.logging.subsystem.patternFormatter.PatternFormatter;
import org.wildfly.apigen.test.invocation.logging.subsystem.rootLogger.Root;
import org.wildfly.apigen.test.invocation.mail.Mail;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.MailSession;
import org.wildfly.apigen.test.invocation.mail.subsystem.mailSession.server.Smtp;

import java.util.ArrayList;
import java.util.List;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * @author Lance Ball
 */
public class MarshallerTestCase extends AbstractTestCase {

    @Test
    public void testMarshalling() throws Exception {
        Mail mail = new Mail();
        mail.mailSession(new MailSession("smtpserver-name")
                .jndiName("smtpserver-jndi-name")
                .smtp(new Smtp().outboundSocketBindingRef("smtpserver-socket-binding-ref")));
        List<ModelNode> list = Marshaller.marshal(mail);
//        for (ModelNode node : list) {
//            System.out.println(node);
//        }
    }

    @Test
    public void testModelNodeOutput() {
        // This is not so much a test as it is visual confirmation of a correctly configured model node chain for swarm
        PathAddress smtpServerAddress = PathAddress.pathAddress(PathElement.pathElement(SUBSYSTEM, "mail"));

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

//        for (ModelNode n : list) {
//            System.out.println(n);
//        }
    }

    @Test
    public void testLoggingMarshalling() throws Exception {
        Logging logging = new Logging();
        final ArrayList<Object> rootHandlers = new ArrayList<>();
        rootHandlers.add("root-handler-one");
        rootHandlers.add("root-handler-two");
        logging.patternFormatter(
                new PatternFormatter("pattern-formatter-name")
                        .pattern("pattern-formatter-pattern"))
                .customFormatter(new CustomFormatter("custom-formatter-name")
                        .module("formatter-module")
                        .attributeClass("FormatterClassName"))
                .consoleHandler(new ConsoleHandler("CONSOLE")
                        .level("INFO")
                        .namedFormatter("formatter-name"))
                .fileHandler(new FileHandler("file-handler-name")
                        .level("INFO")
                        .namedFormatter("formatter-name"))
                .customHandler(new CustomHandler("custom-handler-name")
                        .module("custom-handler-module")
                        .attributeClass("HandlerClassName")
                        .level("INFO"))
                .root(new Root().level("INFO")
                        .handlers(rootHandlers));
        List<ModelNode> list = Marshaller.marshal(logging);
        for (ModelNode n : list) {
            System.out.println(n);
        }
    }

    @Test
    public void testLoggingFraction() {
        System.out.println("----< LOGGING FRACTION >----");
        // This is not so much a test as it is visual confirmation of a correctly configured model node chain for swarm
        final PathAddress loggingAddress = PathAddress.pathAddress(PathElement.pathElement(SUBSYSTEM, "logging"));
        List<ModelNode> list = new ArrayList<>();

        ModelNode address = new ModelNode();

        address.setEmptyList();

        // Add logging subsystem
        ModelNode node = new ModelNode();
        node.get(OP_ADDR).set(loggingAddress.toModelNode());
        node.get(OP).set(ADD);
        list.add(node);

        // Add a pattern formatter
        node = new ModelNode();
        node.get(OP_ADDR).set(loggingAddress.append("pattern-formatter", "pattern-formatter-name").toModelNode());
        node.get(OP).set(ADD);
        node.get("pattern").set("pattern-formatter-pattern");
        list.add(node);

        // Add a custom formatter
        node = new ModelNode();
        node.get(OP_ADDR).set(loggingAddress.append("custom-formatter", "custom-formatter-name").toModelNode());
        node.get(OP).set(ADD);
        node.get("module").set("formatter-module");
        node.get("class").set("FormatterClassName");

        // Custom formatters may have properties
        ModelNode properties = new ModelNode();
        String propertyString = "someKey=someValue,otherKey=otherValue";
        properties.get("metaData").set(new ValueExpression(propertyString));
        node.get("properties").set(properties);
        list.add(node);

        // Add a console handler
        node = new ModelNode();
        node.get(OP_ADDR).set(loggingAddress.append("console-handler", "CONSOLE").toModelNode());
        node.get(OP).set(ADD);
        node.get("level").set("INFO");
        node.get("named-formatter").set("formatter-name");
        list.add(node);

        // Add a file handler
        node = new ModelNode();
        node.get(OP_ADDR).set(loggingAddress.append("file-handler", "file-handler-name").toModelNode());
        node.get(OP).set(ADD);
        node.get("level").set("INFO");
        node.get("named-formatter").set("formatter-name");

        ModelNode file = new ModelNode();
        file.get("path").set("/path/to/some/log");
        file.get("relative-to").set("jboss.server.log.dir");
        node.get("file").set(file);
        node.get("append").set(true);
        list.add(node);

        // Add a custom handler
        node = new ModelNode();
        node.get(OP_ADDR).set(loggingAddress.append("custom-handler", "custom-handler-name").toModelNode());
        node.get(OP).set(ADD);
        node.get("class").set("HandlerClasName");
        node.get("module").set("custom-handler-module");
        node.get("named-formatter").set("custom-handler-formatter-name");

        properties = new ModelNode();
        properties.get("one-key").set("one-key-value-expression");
        properties.get("two-key").set("two-key-value-expression");
        node.get("properties").set(properties);
        list.add(node);

        // Finally add the root logger
        node = new ModelNode();
        node.get(OP_ADDR).set(loggingAddress.append("root-logger", "ROOT").toModelNode());
        node.get(OP).set(ADD);
        node.get("handlers").add("root-handler-one");
        node.get("handlers").add("root-handler-two");
        node.get("level").set("INFO");
        list.add(node);

        for (ModelNode n : list) {
            System.out.println(n);
        }
    }
}