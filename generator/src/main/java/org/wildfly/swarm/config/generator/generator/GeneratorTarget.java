package org.wildfly.swarm.config.generator.generator;

import org.wildfly.swarm.config.runtime.model.AddressTemplate;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class GeneratorTarget {

    private AddressTemplate sourceAddress;

    public GeneratorTarget(String address) {
        this(AddressTemplate.of(address) );
    }

    public GeneratorTarget(AddressTemplate address) {
        this.sourceAddress = address;
    }

    public AddressTemplate getSourceAddress() {
        return sourceAddress;
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
