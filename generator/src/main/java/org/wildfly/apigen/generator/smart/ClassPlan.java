package org.wildfly.apigen.generator.smart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.CaseFormat;
import org.wildfly.apigen.generator.ResourceMetaData;
import org.wildfly.config.model.AddressTemplate;

/**
 * @author Bob McWhirter
 */
public class ClassPlan implements Comparable<ClassPlan> {

    private final List<ResourceMetaData> meta = new ArrayList<>();

    private String packageName;

    private String className;

    private String addresses;

    ClassPlan(ResourceMetaData meta) {
        this(Collections.singletonList(meta));
    }

    ClassPlan(List<ResourceMetaData> meta) {
        this.meta.addAll(meta);

        this.packageName = determinePackageName(0);
        this.className = determineClassName(0);
    }

    void deduplicate(int round) {
        this.packageName = determinePackageName(round);
    }

    public String toString() {
        return getFullyQualifiedClassName();
    }

    String determinePackageName(int uniqueRound) {
        if (this.meta.size() == 1) {
            AddressTemplate address = this.meta.get(0).getAddress();

            int numTokens = address.tokenLength() - 1;

            List<String> segments = determineSegments(address, uniqueRound);

            return packagize(segments);
        }

        List<List<String>> allSegments = new ArrayList<>();

        for (ResourceMetaData resourceMetaData : this.meta) {
            allSegments.add(determineSegments(resourceMetaData.getAddress(), uniqueRound));
        }

        List<String> commonSegments = new ArrayList<>();

        int i = 0;
        OUTER:
        while (true) {

            String cur = null;

            INNER:
            for (List<String> each : allSegments) {
                if (!(each.size() > i)) {
                    break OUTER;
                }

                if (cur == null) {
                    cur = each.get(i);
                } else {
                    if (!cur.equals(each.get(i))) {
                        break OUTER;
                    }
                }
            }

            commonSegments.add(cur);
            cur = null;
            ++i;
        }


        return packagize(commonSegments);
    }

    private List<String> determineSegments(AddressTemplate address, int uniqueRound) {

        int numTokens = address.tokenLength() - 1;

        List<String> segments = new ArrayList<>();

        for (int i = 0; i < numTokens; ++i) {
            AddressTemplate part = address.subTemplate(i, i + 1);
            if (i == 0) {
                segments.add(part.getResourceName());
            } else {
                if (i >= ((numTokens - uniqueRound) )) {
                    System.err.println( "uniq! " + part );
                    segments.add(part.getResourceType());
                    segments.add(part.getResourceName());
                } else {
                    segments.add(part.getResourceType());
                }
            }
        }

        return segments;
    }

    private String packagize(List<String> segments) {
        if (segments.isEmpty()) {
            return "org.wildfly.swarm.configuration";
        }

        return "org.wildfly.swarm.configuration." + String.join(".", segments.stream().map((e) -> {
            return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_UNDERSCORE, e);
        }).collect(Collectors.toList()));
    }

    String determineClassName(int uniqueRound) {
        AddressTemplate address = this.meta.get(0).getAddress();
        AddressTemplate last = address.subTemplate(address.tokenLength() - 1, address.tokenLength());

        String type = last.getResourceType();
        String name = last.getResourceName();

        if (name.equals("*")) {
            return CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, type);
        } else {
            return CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, name + "-" + type);
        }
    }

    String getFullyQualifiedClassName() {
        return this.packageName + "." + this.className;
    }

    @Override
    public int compareTo(ClassPlan o) {
        return getFullyQualifiedClassName().compareTo(o.getFullyQualifiedClassName());
    }

    public List<AddressTemplate> getAddresses() {
        return this.meta.stream().map((e) -> {
            return e.getAddress();
        }).collect(Collectors.toList());
    }
}
