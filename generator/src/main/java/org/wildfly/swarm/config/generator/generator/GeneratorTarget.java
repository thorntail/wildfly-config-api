package org.wildfly.swarm.config.generator.generator;

import org.wildfly.swarm.config.runtime.model.AddressTemplate;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class GeneratorTarget {

    private AddressTemplate sourceAddress;
    private String targetPackage;

    public GeneratorTarget(String address, String targetPackage) {
        this(AddressTemplate.of(address), targetPackage);
    }

    public GeneratorTarget(AddressTemplate address, String targetPackage) {
        this.sourceAddress = address;
        this.targetPackage = targetPackage;
    }

    public AddressTemplate getSourceAddress() {
        return sourceAddress;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneratorTarget that = (GeneratorTarget) o;

        return sourceAddress.equals(that.sourceAddress);

    }

    @Override
    public int hashCode() {
        return sourceAddress.hashCode();
    }
}
