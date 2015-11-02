package org.wildfly.swarm.config.generator.generator;

import org.jboss.forge.roaster.model.JavaType;

/**
 * @author Bob McWhirter
 */
public interface SourceFactory {
    JavaType create(ClassIndex index, ClassPlan plan);
}
