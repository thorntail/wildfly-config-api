package org.wildfly.apigen.generator.smart;

import org.wildfly.apigen.generator.ResourceMetaData;

/**
 * @author Bob McWhirter
 */
public class SubsystemPlanner {

    private final ResourceMetaData meta;

    public SubsystemPlanner(ResourceMetaData meta) {
        this.meta = meta;
    }

    public SubsystemPlan plan() {
        SubsystemPlan plan = new SubsystemPlan( meta );
        plan.plan();
        return plan;
    }
}
