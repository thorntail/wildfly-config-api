package org.wildfly.apigen;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.apigen.operations.DefaultStatementContext;
import org.wildfly.apigen.operations.ReadDescription;

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


        AddressTemplate address = AddressTemplate.of("/subsystem=datasources/data-source=*");
        ReadDescription op = new ReadDescription(address);

        ModelNode response = client.execute(op.resolve(new DefaultStatementContext()));
        ResourceDescription description = ResourceDescription.from(response);

        description.getAttributes().forEach(
                att -> {
                    System.out.println(att.getName()+" :: "+att.getValue().get(TYPE).toString());
                }
        );

    }
}
