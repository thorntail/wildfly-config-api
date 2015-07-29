package org.wildfly.apigen.generator;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class ResourceRef {
    String sourceAddress;
    String targetPackage;

    public ResourceRef(String sourceAddress, String targetPackage) {
        this.sourceAddress = sourceAddress;
        this.targetPackage = targetPackage;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public String getTargetPackage() {
        return targetPackage;
    }
}
