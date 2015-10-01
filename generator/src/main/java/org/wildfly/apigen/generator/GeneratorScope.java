package org.wildfly.apigen.generator;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.wildfly.config.model.AddressTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Heiko Braun
 * @since 30/07/15
 */
public class GeneratorScope {

    private Map<AddressTemplate, JavaClassSource> generatedClasses = new HashMap<>();
    private Map<AddressTemplate, Set<String>> singletons = new HashMap<>();

    public void addGenerated(AddressTemplate address, JavaClassSource javaClass) {
        generatedClasses.put(address, javaClass);
    }

    public JavaClassSource getGenerated(AddressTemplate address) {
        assert generatedClasses.containsKey(address) : "Invalid key to generation target: "+ address;

        return generatedClasses.get(address);
    }

    public void addSingleton(AddressTemplate address, Set<String> names) {
        singletons.put(address, names);
    }

    public Optional<Set<String>> getSingletonMetaData(AddressTemplate address) {
        Set<String> names = singletons.get(address);
        return names == null || names.isEmpty() ? Optional.empty() : Optional.of(names);
    }

    public boolean contains(String className) {
        boolean contains = false;
        Iterator<Map.Entry<AddressTemplate, JavaClassSource>> it = generatedClasses.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<AddressTemplate, JavaClassSource> entry = it.next();
            if(entry.getValue().getName().equals(className))
            {
                contains = true;
                break;
            }
        }

        return contains;
    }
}
