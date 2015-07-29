package org.wildfly.apigen;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class Main {

    private final static String host = "localhost";
    private final static int port = 9990;

    public static void main(String[] args) throws Exception {


        ModelControllerClient client = ModelControllerClient.Factory.create(host, port, new AuthCallback(args));

        ModelNode op = new ModelNode();
        op.get(OP).set(READ_ATTRIBUTE_OPERATION);
        op.get(ADDRESS).setEmptyList();
        op.get(NAME).set("release-version");

        ModelNode response = client.execute(op);
        System.out.println("Server version: "+ response.get(RESULT).asString());


    }
}
