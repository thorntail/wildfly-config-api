package org.wildfly.apigen.operations;

import org.jboss.dmr.ModelNode;
import org.wildfly.config.model.StatementContext;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public interface AddressTemplateSupport {

    ModelNode resolve(StatementContext ctx);
}
