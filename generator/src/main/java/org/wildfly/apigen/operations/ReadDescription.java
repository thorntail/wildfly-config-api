package org.wildfly.apigen.operations;

import org.jboss.dmr.ModelNode;
import org.wildfly.config.model.AddressTemplate;
import org.wildfly.apigen.model.Operation;
import org.wildfly.config.model.ResourceAddress;
import org.wildfly.config.model.StatementContext;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class ReadDescription implements AddressTemplateSupport {
    private AddressTemplate address;

    public ReadDescription(AddressTemplate address) {
        this.address = address;
    }

    @Override
    public ModelNode resolve(StatementContext ctx) {

        ResourceAddress address = this.address.resolve(ctx);
        Operation op = new Operation.Builder(READ_RESOURCE_DESCRIPTION_OPERATION, address)
                .param(RECURSIVE, true)
                .build();

        return op;
    }
}
