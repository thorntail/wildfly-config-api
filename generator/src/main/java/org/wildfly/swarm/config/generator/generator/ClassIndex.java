package org.wildfly.swarm.config.generator.generator;

import org.wildfly.swarm.config.runtime.model.AddressTemplate;

/**
 * Maps resources (through their address) to {@link ClassPlan}'s.
 * This way multiple resources can be represented by the same java class.
 *
 * @author Bob McWhirter
 */
public interface ClassIndex {
    ClassPlan lookup(AddressTemplate address);
}
