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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * @author Lance Ball
 */
public class MarshallerTestCase extends AbstractTestCase {

    @Test
    public void testLoggingMarshalling() throws Exception {
//        System.out.println("----< LOGGING MARSHALLING >----");
        Logging logging = new Logging();

        // Simple support for ModelType.LIST
        final ArrayList<Object> rootHandlers = new ArrayList<>();
        rootHandlers.add("root-handler-one");
        rootHandlers.add("root-handler-two");

        // Simple support for ModelType.OBJECT
        final HashMap<String, String> file = new HashMap();
        file.put("path", "/path/to/some/log");
        file.put("relative-to", "jboss.server.log.dir");

        final HashMap<String, String> formatterProperties = new HashMap<>();
        formatterProperties.put("metaData", "someKey=someValue,otherKey=otherValue");

        final HashMap<String, String> handlerProperties = new HashMap<>();
        handlerProperties.put("one-key", "one-key-value-expression");
        handlerProperties.put("two-key", "two-key-value-expression");

        logging.patternFormatter(
                new PatternFormatter("pattern-formatter-name")
                        .pattern("pattern-formatter-pattern"))
                .customFormatter(new CustomFormatter("custom-formatter-name")
                        .module("formatter-module")
                        .attributeClass("FormatterClassName")
                        .properties(formatterProperties))
                .consoleHandler(new ConsoleHandler("CONSOLE")
                        .level("INFO")
                        .namedFormatter("formatter-name"))
                .fileHandler(new FileHandler("file-handler-name")
                        .level("INFO")
                        .namedFormatter("formatter-name")
                        .file(file))
                .customHandler(new CustomHandler("custom-handler-name")
                        .module("custom-handler-module")
                        .attributeClass("HandlerClassName")
                        .level("INFO")
                        .properties(handlerProperties))
                .root(new Root().level("INFO")
                        .handlers(rootHandlers));
        List<ModelNode> list = Marshaller.marshal(logging);
//        for (ModelNode n : list) {
//            System.out.println(n);
//        }
    }

    @Test
    public void testLoggingFraction() {
//        System.out.println("----< LOGGING FRACTION >----");
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

//        for (ModelNode n : list) {
//            System.out.println(n);
//        }
    }

    @Test
    public void testMailFraction() {
        System.out.println("----< MAIL FRACTION >----");
        // This is not so much a test as it is visual confirmation of a correctly configured model node chain for swarm
        PathAddress smtpServerAddress = PathAddress.pathAddress(PathElement.pathElement(SUBSYSTEM, "mail"));
        List<ModelNode> list = new ArrayList<>();

        ModelNode node = new ModelNode();
        node.get(OP_ADDR).set(smtpServerAddress.toModelNode());
        node.get(OP).set(ADD);
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(smtpServerAddress.append("mail-session", "smtp-server-name").toModelNode());
        node.get(OP).set(ADD);
        node.get("jndi-name").set("smtp-server-jndi-name");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(smtpServerAddress.append("mail-session", "smtp-server-name").append("server", "smtp").toModelNode());
        node.get(OP).set(ADD);
        node.get("outbound-socket-binding-ref").set("smtp-server-outbound-socket-binding-ref");
        list.add(node);

        node = new ModelNode();
        node.get(OP_ADDR).set(PathAddress.pathAddress("socket-binding-group", "default-sockets").append("remote-destination-outbound-socket-binding", "smtp-server-outbound-socket-binding-ref").toModelNode());
        node.get(OP).set(ADD);
        node.get("host").set("smtp-server-host");
        node.get("port").set("521");
        list.add(node);

        for (ModelNode n : list) {
            System.out.println(n);
        }
    }

    @Test
    public void testMailMarshalling() throws Exception {
        System.out.println("----< MAIL MARSHALLING >----");
        final Mail mail = new Mail();
        final MailSession mailSession = new MailSession("smtp-server-name")
                .smtp(new Smtp().outboundSocketBindingRef("smtp-server-outbound-socket-binding-ref"))
                .jndiName("smtp-server-jndi-name");

        mail.mailSession( mailSession );

        List<ModelNode> list = Marshaller.marshal(mail);

        for (ModelNode n : list) {
            System.out.println(n);
        }

    }
}