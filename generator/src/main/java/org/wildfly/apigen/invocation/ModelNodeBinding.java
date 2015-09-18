package org.wildfly.apigen.invocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Heiko Braun
 * @date 4/19/11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ModelNodeBinding {

    /**
     * The name of the DMR attribute
     *
     * @return
     */
    String detypedName();
}