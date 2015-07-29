package org.wildfly.apigen.generator;

/**
 * @author Heiko Braun
 * @since 29/07/15
 */
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Config: "+args[0]);
        System.out.println("Output: "+args[1]);

        Config config = Config.fromJson(args[0]);
    }
}
