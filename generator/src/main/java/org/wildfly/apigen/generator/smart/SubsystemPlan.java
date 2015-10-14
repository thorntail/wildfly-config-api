package org.wildfly.apigen.generator.smart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;
import org.wildfly.apigen.generator.ResourceMetaData;
import org.wildfly.apigen.model.ResourceDescription;
import org.wildfly.config.model.AddressTemplate;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.*;

/**
 * @author Bob McWhirter
 */
public class SubsystemPlan {

    private final ResourceMetaData meta;

    private final List<ClassPlan> classPlans = new ArrayList<>();

    private Map<AddressTemplate, ClassPlan> index = new HashMap<>();

    SubsystemPlan(ResourceMetaData meta) {
        this.meta = meta;
    }

    void plan() {
        classPlans.add(new ClassPlan(this.meta));

        List<ResourceMetaData> list = new ArrayList<>();
        collect(this.meta, list);

        for (ResourceMetaData each : list) {
            ModelNode attrs = each.getDescription().get(ATTRIBUTES);
            List<Property> props = attrs.asPropertyList();
            for (Property prop : props) {
                ModelNode deprecated = prop.getValue().get(DEPRECATED);
                if (deprecated.isDefined()) {
                    attrs.remove(prop.getName());
                }
            }
        }

        Map<AddressTemplate, List<ResourceMetaData>> grouped = list.stream().collect(Collectors.groupingBy((e) -> {
            AddressTemplate address = e.getAddress();
            return address.subTemplate(address.tokenLength() - 1, address.tokenLength());
        }));

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
            System.err.println(cur + " vs " + each);
            if (cur != null) {
                if (cur.getFullyQualifiedClassName().equals(each.getFullyQualifiedClassName())) {
                    System.err.println("duplicate: " + each.getAddresses());
                    dupes.add(cur);
                    dupes.add(each);
                } else {
                    System.err.println("not dupe: " + dupes.size());
                    if (!dupes.isEmpty()) {
                        deduplicate(dupes);
                        dupes.clear();
                    }
                }
            }
            cur = each;
            System.err.println(":: " + cur);
        }

        if (!dupes.isEmpty()) {
            deduplicate(dupes);
            dupes.clear();
        }

        for (ClassPlan each : classPlans) {
            for (AddressTemplate address : each.getAddresses()) {
                this.index.put(address, each);
            }
        }

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
