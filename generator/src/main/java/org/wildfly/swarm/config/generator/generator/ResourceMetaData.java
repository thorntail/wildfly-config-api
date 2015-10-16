package org.wildfly.swarm.config.generator.generator;

import org.wildfly.swarm.config.runtime.model.AddressTemplate;
import org.wildfly.swarm.config.generator.model.ResourceDescription;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Heiko Braun
 * @since 30/07/15
 */
public class ResourceMetaData implements Comparable<ResourceMetaData> {

    public static String PKG = "package";

    private AddressTemplate address;
    private ResourceDescription desc;

    private Map<String, String> cfg = new HashMap<>();

    public ResourceMetaData(AddressTemplate address, ResourceDescription desc) {

        if (!desc.isDefined())
            throw new IllegalStateException("Undefined resource description");

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

    public String get(String key) {
        assert cfg.containsKey(key) : "Configuration missing for key :" + key;
        return cfg.get(key);
    }

    public void set(String key, String value) {
        cfg.put(key, value);
    }

    public Map<String, String> getAllCfg() {
        return cfg;
    }

    public void setAllCfg(Map<String, String> values) {
        cfg.putAll(values);
    }

    @Override
    public String toString() {
        return address.getTemplate();
    }

}
