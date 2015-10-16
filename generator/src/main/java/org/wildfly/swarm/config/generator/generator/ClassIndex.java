package org.wildfly.swarm.config.generator.generator;

import org.wildfly.swarm.config.runtime.model.AddressTemplate;

/**
 * @author Bob McWhirter
 */
public interface ClassIndex {
    ClassPlan lookup(AddressTemplate address);
}
