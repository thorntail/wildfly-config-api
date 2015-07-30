package org.wildfly.apigen.generator;

import org.wildfly.apigen.model.AddressTemplate;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class ModelSegment {

    private AddressTemplate sourceAddress;
    private String targetPackage;

    public ModelSegment(String address, String targetPackage) {
        this(AddressTemplate.of(address), targetPackage);
    }

    public ModelSegment(AddressTemplate address, String targetPackage) {
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

        ModelSegment that = (ModelSegment) o;

        return sourceAddress.equals(that.sourceAddress);

    }

    @Override
    public int hashCode() {
        return sourceAddress.hashCode();
    }
}
