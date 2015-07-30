package org.wildfly.apigen.generator;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.wildfly.apigen.model.AddressTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Heiko Braun
 * @since 30/07/15
 */
public class GeneratorScope {

    private Map<AddressTemplate, JavaClassSource> generatedClasses = new HashMap<>();

    public void addGenerated(AddressTemplate address, JavaClassSource javaClass) {
        generatedClasses.put(address, javaClass);
    }

    public JavaClassSource getGenerated(AddressTemplate address) {
        assert generatedClasses.containsKey(address) : "Invalid key to generation target: "+ address;

        return generatedClasses.get(address);
    }
}
