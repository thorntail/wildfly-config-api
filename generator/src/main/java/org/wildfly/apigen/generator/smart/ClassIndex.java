package org.wildfly.apigen.generator.smart;

import org.wildfly.config.model.AddressTemplate;

/**
 * @author Bob McWhirter
 */
public interface ClassIndex {
    ClassPlan lookup(AddressTemplate address);
}
