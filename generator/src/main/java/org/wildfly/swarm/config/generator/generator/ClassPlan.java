package org.wildfly.swarm.config.generator.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.CaseFormat;
import org.jboss.forge.roaster.model.JavaType;
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
    private final AddressTemplate addr;

    private String packageName;

    private String className;

    private final String originalClassName;

    private String addresses;

    private List<JavaType> sources = new ArrayList<>();

    private JavaClassSource subresourceClass;

    ClassPlan(ResourceMetaData meta) {
        this(Collections.singletonList(meta));
    }

    /**
     * Construct a class plan for set of identical resources.
     *
     * @param meta
     */
    ClassPlan(List<ResourceMetaData> meta) {

        // type assertion to make things more obvious
        ResourceMetaData prime = meta.get(0);
        String primeType = prime.getAddress().getResourceType();
        meta.forEach(resourceMetaData -> {
            String resourceType = resourceMetaData.getAddress().getResourceType();
            if (!resourceType.equals(primeType))
                throw new IllegalArgumentException("Illegal partition, resourceType's don't match: "+
                        primeType + " > "+ resourceType
                );
        });

        this.meta.addAll(meta);
        this.addr = prime.getAddress();
        this.packageName = determinePackageName(0);
        this.originalClassName = determineClassName(0);
        this.className = NameFixer.fixClassName(this.originalClassName);

        this.type = addr.getResourceType();
    }

    void setSubresourceClass(JavaClassSource cls) {
        this.subresourceClass = cls;
    }

    JavaClassSource getSubresourceClass() {
        return this.subresourceClass;
    }

    public AddressTemplate getAddr() {
        return addr;
    }

    String getResourceType() {
        return this.type;
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

    void addSource(JavaType source) {
        this.sources.add( source );
    }

    List<JavaType> getSources() {
        return this.sources;
    }

    void deduplicate(int round) {
        this.packageName = determinePackageName(round);
        this.className = determineClassName(round);
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
                if (i >= ((numTokens - uniqueRound))) {
                    segments.add(part.getResourceType());
                    if ( ! part.getResourceName().equals( "*" ) ) {
                        segments.add(part.getResourceName());
                    }
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

        String pkg = "org.wildfly.swarm.config." + String.join(".", segments.stream().map((e) -> {
            return CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_UNDERSCORE, e);
        }).collect(Collectors.toList()));

        return pkg;
    }

    String determineClassName(int uniqueRound) {
        AddressTemplate address = this.meta.get(0).getAddress();

        String type = address.getResourceType();
        String name = address.getResourceName();

        String clsName = null;

        if (address.tokenLength() == 1 && type.equals("subsystem")) {
            clsName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, name);
        } else if (name.equals("*")) {
            clsName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, type);
        } else {
            if (type.toLowerCase().startsWith(name.toLowerCase())) {
                clsName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, type);
            } else if (name.toLowerCase().endsWith(type.toLowerCase())) {
                clsName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, name);
            } else if ( name.contains("." ) ) {
                int dotLoc = name.indexOf( "." );
                this.packageName = this.packageName + "." + name.substring( 0, dotLoc );
                String rest = name.substring(dotLoc + 1 );
                if ( rest.chars().allMatch( c->Character.isUpperCase(c) || Character.isDigit(c)) ) {
                    clsName = rest;
                } else {
                    clsName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, rest );
                }
            } else {
                clsName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, name + "-" + type);
            }
        }

        return clsName;
    }

    String getPackageName() {
        return this.packageName;
    }

    String getClassName() {
        return this.className;
    }

    String getOriginalClassName() {
        return this.originalClassName;
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
