package org.wildfly.swarm.config.generator.operations;

import org.jboss.dmr.ModelNode;
import org.wildfly.swarm.config.runtime.model.StatementContext;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public interface AddressTemplateSupport {

    ModelNode resolve(StatementContext ctx);
}
