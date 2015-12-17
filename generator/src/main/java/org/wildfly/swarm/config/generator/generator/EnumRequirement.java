package org.wildfly.swarm.config.generator.generator;

import java.util.List;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ALLOWED;

/**
 * @author Bob McWhirter
 */
public class EnumRequirement {

    private final ClassPlan classPlan;
    private final Property property;

    public EnumRequirement(ClassPlan classPlan, Property property) {
        this.classPlan = classPlan;
        this.property = property;
    }

    String getName() {
        return this.property.getName();
    }

    ModelNode getValue() {
        return this.property.getValue();
    }

    Property getProperty() {
        return this.property;
    }

    ClassPlan getOriginatingClassPlan() {
        return this.classPlan;
    }

    public List<ModelNode> getAllowedValues() {
        return this.property.getValue().get(ALLOWED).asList();
    }
}
