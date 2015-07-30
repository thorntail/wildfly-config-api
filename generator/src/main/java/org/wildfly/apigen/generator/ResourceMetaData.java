package org.wildfly.apigen.generator;

import org.wildfly.apigen.model.AddressTemplate;
import org.wildfly.apigen.model.ResourceDescription;

/**
 * @author Heiko Braun
 * @since 30/07/15
 */
public class ResourceMetaData implements Comparable<ResourceMetaData> {
    AddressTemplate address;
    ResourceDescription desc;

    public ResourceMetaData(AddressTemplate address, ResourceDescription desc) {
        this.address = address;
        this.desc = desc;
    }

    public AddressTemplate getAddress() {
        return address;
    }

    public ResourceDescription getDescription() {
        return desc;
    }

    @Override
    public int compareTo(ResourceMetaData o) {
        return this.address.compareTo(o.address);
    }
}
