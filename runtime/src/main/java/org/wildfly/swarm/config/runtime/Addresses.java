package org.wildfly.swarm.config.runtime;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Depicts config classes that represent multiple resources.
 *
 * @author Heiko Braun
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Addresses {

    /**
     * The resource addresses
     *
     * @return
     */
    String[] value();
}