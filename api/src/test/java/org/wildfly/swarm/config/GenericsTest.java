package org.wildfly.swarm.config;

import java.util.function.Consumer;

import org.junit.Test;

/**
 * @author Bob McWhirter
 */
public class GenericsTest {

    public static class Cache<F extends Cache<F>> {

        public void something() {
            System.err.println( "Something!" );
        }

    }

    public static class Infinispan<T extends Infinispan<T>> {
        public T cache(Consumer<? super Cache> consumer) {
            consumer.accept( new Cache() );
            return (T) this;
        }
    }

    public static class InfinispanFraction extends Infinispan<InfinispanFraction> {

    }

    @Test
    public void test() {
        InfinispanFraction t = new InfinispanFraction();

        t.cache( (foo)->{
            foo.something();
        });

    }
}

