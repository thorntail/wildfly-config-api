package org.wildfly.swarm.config.generator.model;

import org.wildfly.swarm.config.runtime.model.StatementContext;

import java.util.LinkedList;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class DefaultStatementContext implements StatementContext {
    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public String[] getTuple(String key) {
        return new String[0];
    }

    @Override
    public String resolve(String key) {
        return null;
    }

    @Override
    public String[] resolveTuple(String key) {
        return new String[0];
    }

    @Override
    public LinkedList<String> collect(String key) {
        return null;
    }

    @Override
    public LinkedList<String[]> collectTuples(String key) {
        return null;
    }
}
