package org.wildfly.apigen.operations;

import org.jboss.dmr.ModelNode;
import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.Operation;
import org.wildfly.apigen.model.ResourceAddress;
import org.wildfly.apigen.model.StatementContext;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class ReadChildTypes implements AddressTemplateSupport {
    private AddressTemplate address;

    public ReadChildTypes(AddressTemplate address) {
        this.address = address;
    }

    @Override
    public ModelNode resolve(StatementContext ctx) {

        ResourceAddress address = this.address.resolve(ctx);
        ModelNode op = new ModelNode();
        op.get(ADDRESS).set(address);
        op.get(OP).set(READ_CHILDREN_TYPES_OPERATION);
        op.get("include-singletons").set(true);
        return op;
    }
}
