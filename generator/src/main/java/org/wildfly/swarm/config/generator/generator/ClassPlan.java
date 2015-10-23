package org.wildfly.swarm.config.generator.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.CaseFormat;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.wildfly.swarm.config.generator.model.ResourceDescription;
import org.wildfly.swarm.config.runtime.model.AddressTemplate;

/**
 * @author Bob McWhirter
 */
public class ClassPlan implements Comparable<ClassPlan> {

    private final List<ResourceMetaData> meta = new ArrayList<>();

    private final String type;

    private String packageName;

    private String className;

    private String addresses;

    private JavaClassSource resourceSource;
    private JavaInterfaceSource configSource;

    private boolean templated = false;

    ClassPlan(ResourceMetaData meta) {
        this(Collections.singletonList(meta));
    }

    ClassPlan(List<ResourceMetaData> meta) {
        this.meta.addAll(meta);

        this.packageName = determinePackageName(0);
        this.className = determineClassName(0);

        AddressTemplate addr = this.meta.get(0).getAddress();
        this.type = addr.subTemplate( addr.tokenLength() -1, addr.tokenLength() ).getResourceType();
    }

    String getResourceType() {
        return this.type;
    }

    void setTemplated(boolean templated) {
        this.templated = templated;
    }

    boolean isTemplated() {
        return this.templated;
    }

    String getThisReturnType() {
        if ( this.templated ) {
            return "T";
        }
        return this.className;
    }

    boolean isSingleton() {
        return this.meta.get(0).getDescription().isSingleton();
    }

    String getSingletonName() {
        return this.meta.get(0).getDescription().getSingletonName();
    }

    ResourceDescription getDescription() {
        return this.meta.get(0).getDescription();
    }

    ResourceMetaData getMetaData() {
        return this.meta.get(0);
    }

    void setResourceClassSource(JavaClassSource source) {
        this.resourceSource = source;
    }

    JavaClassSource getResourceClassSource() {
        return this.resourceSource;
    }

    void setConfiguratorInterfaceSource(JavaInterfaceSource source) {
        this.configSource = source;
    }

    JavaInterfaceSource getConfiguratorInterfaceSource() {
        return this.configSource;
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
            return "org.wildfly.swarm.config";
        }

        return "org.wildfly.swarm.config." + String.join(".", segments.stream().map((e) -> {
            return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_UNDERSCORE, e);
        }).collect(Collectors.toList()));
    }

    String determineClassName(int uniqueRound) {
        AddressTemplate address = this.meta.get(0).getAddress();
        AddressTemplate last = address.subTemplate(address.tokenLength() - 1, address.tokenLength());

        String type = last.getResourceType();
        String name = last.getResourceName();

        String clsName = null;

        if (address.tokenLength() == 1 && type.equals("subsystem")) {
            clsName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, name);
        } else if (name.equals("*")) {
            clsName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, type);
        } else {
            if ( type.toLowerCase().startsWith( name.toLowerCase() ) ) {
                clsName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, type);
            } else if ( name.toLowerCase().endsWith( type.toLowerCase() ) ) {
                clsName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, name);
            } else {
                clsName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, name + "-" + type);
            }
        }

        return NameFixer.fix( clsName );
    }

    String getPackageName() {
        return this.packageName;
    }

    String getClassName() {
        return this.className;
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
