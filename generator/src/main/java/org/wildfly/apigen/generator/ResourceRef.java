package org.wildfly.apigen.generator;

import org.wildfly.apigen.model.AddressTemplate;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class ResourceRef {

    private AddressTemplate sourceAddress;
    private String targetPackage;

    public ResourceRef(String address, String targetPackage) {
        this(AddressTemplate.of(address), targetPackage);
    }

    public ResourceRef(AddressTemplate address, String targetPackage) {
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

        ResourceRef that = (ResourceRef) o;

        return sourceAddress.equals(that.sourceAddress);

    }

    @Override
    public int hashCode() {
        return sourceAddress.hashCode();
    }
}
