package org.wildfly.swarm.config.generator.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.CaseFormat;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;
import org.wildfly.swarm.config.generator.model.ResourceDescription;
import org.wildfly.swarm.config.runtime.model.AddressTemplate;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * @author Bob McWhirter
 */
public class SubsystemPlan implements ClassIndex {

    private final ResourceMetaData meta;

    private final List<ClassPlan> classPlans = new ArrayList<>();

    private final List<EnumPlan> enumPlans = new ArrayList<>();

    private Map<AddressTemplate, ClassPlan> index = new HashMap<>();

    SubsystemPlan(ResourceMetaData meta) {
        this.meta = meta;
        plan();
    }

    @Override
    public ClassPlan lookup(AddressTemplate address) {
        return this.index.get(address);
    }

    @Override
    public EnumPlan lookup(ClassPlan requester, Property attr) {
        return this.enumPlans.stream().filter(e -> e.matches(requester, attr)).findFirst().orElse(null);
    }

    List<ClassPlan> getClassPlans() {
        return this.classPlans;
    }

    List<EnumPlan> getEnumPlans() {
        return this.enumPlans;
    }

    void plan() {
        ClassPlan subsystemClass = new ClassPlan(this.meta);
        classPlans.add(subsystemClass);

        List<ResourceMetaData> list = new ArrayList<>();
        collect(this.meta, list);

        // exclude deprecated attributes
        for (ResourceMetaData each : list) {
            ModelNode attrs = each.getDescription().get(ATTRIBUTES);
            List<Property> props = attrs.asPropertyList();
            for (Property prop : props) {
                ModelNode accessType = prop.getValue().get(ACCESS_TYPE);
                if ( ! accessType.asString().contains( "write" ) ) {
                    continue;
                }

                ModelNode deprecated = prop.getValue().get(DEPRECATED);
                if (deprecated.isDefined()) {
                    String since = deprecated.get(SINCE).asString();
                    if (since.startsWith("5.") || since.startsWith("4.")) {
                        //System.err.println( "keeping: " + prop.getName() + " from " + each.getAddress() + " since " + deprecated.asString() );
                        // keep it
                    } else {
                        //System.err.println( "discarding: " + prop.getName() + " from " + each.getAddress() + " for " + deprecated.asString() );
                        attrs.remove(prop.getName());
                    }
                }
            }
        }

        // group by last tuple
        Map<AddressTemplate, List<ResourceMetaData>> grouped = list.stream().collect(Collectors.groupingBy((e) -> {
            AddressTemplate address = e.getAddress();
            return address.lastSubTemplate();
        }));

        // assign ClassPlan's to a set of resources (1:n)
        for (AddressTemplate key : grouped.keySet()) {
            List<ResourceMetaData> members = grouped.get(key);
            if (members.size() > 1) {
                List<List<ResourceMetaData>> partitions = partition(members);

                for (List<ResourceMetaData> partition : partitions) {
                    classPlans.add(new ClassPlan(partition));
                }
            } else {
                classPlans.add(new ClassPlan(members));
            }
        }

        Collections.sort(classPlans);

        ClassPlan cur = null;
        Set<ClassPlan> dupes = new HashSet<>();
        for (ClassPlan each : classPlans) {
            if (cur != null) {
                if (cur.getFullyQualifiedClassName().equals(each.getFullyQualifiedClassName())) {
                    dupes.add(cur);
                    dupes.add(each);
                } else {
                    if (!dupes.isEmpty()) {
                        deduplicate(dupes);
                        dupes.clear();
                    }
                }
            }
            cur = each;
        }

        if (!dupes.isEmpty()) {
            deduplicate(dupes);
            dupes.clear();
        }

        // assign a set of resources to a ClassPlan (n:1)
        // used by ResourceClassFactory to determine the actual java type for a resource
        for (ClassPlan each : classPlans) {
            for (AddressTemplate address : each.getAddresses()) {
                this.index.put(address, each);
            }
        }

        // determine all potential enums
        List<EnumRequirement> enumRequirements = new ArrayList<>();
        for (ClassPlan each : classPlans) {
            enumRequirements.addAll(each.getEnumRequirements());
        }

        List<List<EnumRequirement>> enumPartitions = partitionEnumRequirements(enumRequirements);

        for (List<EnumRequirement> enumPartition : enumPartitions) {
            if (enumPartition.size() == 1) {
                EnumRequirement requirement = enumPartition.get(0);
                requirement.getOriginatingClassPlan().addEnumPlan(new EnumPlan(null, enumPartition));

            } else {
                this.enumPlans.add(new EnumPlan(subsystemPackage(subsystemClass), enumPartition));
            }
        }

        Collections.sort(enumPlans);

        EnumPlan curEnum = null;
        Set<EnumPlan> dupeEnums = new HashSet<>();
        for (EnumPlan each : enumPlans) {
            if (curEnum != null) {
                if (curEnum.getFullyQualifiedClassName().equals(each.getFullyQualifiedClassName())) {
                    dupeEnums.add(curEnum);
                    dupeEnums.add(each);
                } else {
                    if (!dupeEnums.isEmpty()) {
                        //deduplicate(dupes);
                        System.err.println("A ****** NEED TO DEDUPE ENUM: " + dupeEnums);
                        for (EnumPlan eachPlan : dupeEnums) {
                            System.err.println(" - " + eachPlan.getOriginatingClassPlans());
                        }
                        dupeEnums.clear();
                    }
                }
            }
            curEnum = each;
        }

        if (!dupeEnums.isEmpty()) {
            //deduplicate(dupes);
            System.err.println("B ****** NEED TO DEDUPE ENUM: " + dupeEnums);
            for (EnumPlan each : dupeEnums) {
                System.err.println(" - " + each.getOriginatingClassPlans());
            }

            dupeEnums.clear();
        }

    }

    static String subsystemPackage(ClassPlan subsystemClass) {
        return subsystemClass.getPackageName() + "." + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, subsystemClass.getOriginalClassName()).replace('-', '.');
    }

    static boolean deduplicate(Set<ClassPlan> dupes) {
        int i = 1;
        while (true) {
            for (ClassPlan dupe : dupes) {
                dupe.deduplicate(i);
            }

            if (!stillContainsDuplicates(dupes)) {
                break;
            }
            ++i;

            if (i > 10) {
                return false;
            }
        }

        return true;
    }

    static boolean stillContainsDuplicates(Set<ClassPlan> dupes) {
        Set<String> seen = new HashSet<>();

        for (ClassPlan dupe : dupes) {
            if (seen.contains(dupe.getFullyQualifiedClassName())) {
                return true;
            }

            seen.add(dupe.getFullyQualifiedClassName());
        }

        return false;


    }

    static List<List<ResourceMetaData>> partition(List<ResourceMetaData> list) {
        List<List<ResourceMetaData>> partitions = new ArrayList<>();

        for (ResourceMetaData prime : list) {
            List<ResourceMetaData> matched = findPartition(partitions, prime);
            if (matched == null) {
                matched = new ArrayList<>();
                partitions.add(matched);
            }
            matched.add(prime);
        }

        return partitions;
    }


    static List<ResourceMetaData> findPartition(List<List<ResourceMetaData>> partitions, ResourceMetaData prime) {

        for (List<ResourceMetaData> partition : partitions) {
            if (partition.isEmpty()) {
                continue;
            }

            ResourceMetaData comp = partition.get(0);

            ResourceDescription compDesc = comp.getDescription();
            ResourceDescription primeDesc = prime.getDescription();

            if (compDesc.getAttributes().size() != primeDesc.getAttributes().size()) {
                continue;
            }

            boolean match = compDesc.getAttributes().stream().allMatch((c) -> {
                boolean found = primeDesc.getAttributes().stream().anyMatch((p) -> {
                    if (!p.getName().equals(c.getName())) {
                        return false;
                    }

                    return p.getValue().getType().equals(c.getValue().getType());
                });

                return found;
            });

            if (!match) {
                continue;
            }

            match = compDesc.getChildrenTypes().equals(primeDesc.getChildrenTypes());

            if (!match) {
                continue;
            }

            match = compDesc.getSingletonChildrenTypes().equals(primeDesc.getSingletonChildrenTypes());

            if (!match) {
                continue;
            }


            return partition;
        }

        return null;
    }

    static List<List<EnumRequirement>> partitionEnumRequirements(List<EnumRequirement> list) {
        List<List<EnumRequirement>> partitions = new ArrayList<>();

        for (EnumRequirement prime : list) {
            List<EnumRequirement> matched = findEnumRequirementPartition(partitions, prime);
            if (matched == null) {
                matched = new ArrayList<>();
                partitions.add(matched);
            }
            matched.add(prime);
        }

        return partitions;
    }

    static List<EnumRequirement> findEnumRequirementPartition(List<List<EnumRequirement>> partitions, EnumRequirement prime) {

        for (List<EnumRequirement> partition : partitions) {
            if (partition.isEmpty()) {
                continue;
            }

            EnumRequirement comp = partition.get(0);
            if (prime.getName().equals(comp.getName()) && prime.getAllowedValues().equals(comp.getAllowedValues())) {
                return partition;
            }
        }

        return null;
    }

    static void collect(ResourceMetaData root, List<ResourceMetaData> list) {

        Set<String> childTypes = root.getDescription().getChildrenTypes();

        for (String childType : childTypes) {
            ResourceDescription child = root.getDescription().getChildDescription(childType);
            ResourceMetaData childMeta = new ResourceMetaData(root.getAddress().append(childType + "=*"), child);
            list.add(childMeta);
            collect(childMeta, list);
        }

        Set<String> singletonTypes = root.getDescription().getSingletonChildrenTypes();

        for (String childType : singletonTypes) {
            String[] parts = childType.split("=");
            ResourceDescription child = root.getDescription().getChildDescription(parts[0], parts[1]);
            child.setSingletonName(parts[1]);
            ResourceMetaData childMeta = new ResourceMetaData(root.getAddress().append(childType), child);
            list.add(childMeta);
            collect(childMeta, list);
        }

    }
}
