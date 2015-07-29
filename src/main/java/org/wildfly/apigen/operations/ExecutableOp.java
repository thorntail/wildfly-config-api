package org.wildfly.apigen.operations;

import org.jboss.dmr.ModelNode;
import org.wildfly.apigen.model.StatementContext;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public interface ExecutableOp {

    ModelNode resolve(StatementContext ctx);
}
