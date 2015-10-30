package org.wildfly.config.model;

import org.wildfly.swarm.config.runtime.model.StatementContext;

import java.util.LinkedList;

/**
 * @author Heiko Braun
 * @date 4/17/13
 */
public class NoopContext implements StatementContext {

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public String[] getTuple(String key) {
        return null;
    }

    @Override
    public String resolve(String key) {
        return null;
    }

    @Override
    public String[] resolveTuple(String key) {
        return null;
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
