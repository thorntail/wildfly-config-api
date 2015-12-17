package org.wildfly.swarm.config.generator.generator;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.CaseFormat;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ALLOWED;

/**
 * @author Bob McWhirter
 */
public class EnumPlan implements Comparable<EnumPlan> {

    private final List<EnumRequirement> requirements;

    private String packageName;

    private String className;

    public EnumPlan(String subsystemPackage, List<EnumRequirement> requirements) {
        this.requirements = requirements;

        if ( subsystemPackage != null ) {
            this.packageName = subsystemPackage;

            for (EnumRequirement requirement : this.requirements) {
                String candidate = requirement.getOriginatingClassPlan().getPackageName();
                if (candidate.length() > subsystemPackage.length()) {
                    if (candidate.length() < this.packageName.length()) {
                        this.packageName = candidate;
                    }
                }
            }
        }

        this.className = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, requirements.get(0).getName());
    }

    public List<ClassPlan> getOriginatingClassPlans() {
        return this.requirements.stream().map( e->e.getOriginatingClassPlan() ).collect(Collectors.toList() );
    }

    public boolean matches(ClassPlan requester, Property attr) {
        return this.requirements.stream().anyMatch((e) -> {
            return e.getOriginatingClassPlan() == requester && e.getProperty().getName().equals(attr.getName());
        });
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getClassName() {
        return this.className;
    }

    public String getFullyQualifiedClassName() {
        return this.packageName + "." + this.className;
    }

    public List<ModelNode> getAllowedValues() {
        return this.requirements.get(0).getValue().get(ALLOWED).asList();
    }

    public String toString() {
        return this.requirements.get(0).getName() + " (" + this.requirements.size() + ") -> " + getAllowedValues();
    }

    @Override
    public int compareTo(EnumPlan o) {
        return getFullyQualifiedClassName().compareTo(o.getFullyQualifiedClassName());
    }
}
