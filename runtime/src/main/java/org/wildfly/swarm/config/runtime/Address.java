package org.wildfly.swarm.config.runtime;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Depicts config classes that represent a single resource.
 *
 * @author Heiko Braun
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Address {

    /**
     * The resource address
     *
     * @return
     */
    String value();
}