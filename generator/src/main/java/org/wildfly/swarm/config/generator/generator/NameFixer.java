package org.wildfly.swarm.config.generator.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bob McWhirter
 */
public class NameFixer {

    public static String fixPropertyName(String name) {
        int numChars = name.length();
        for ( int i = 1 ; i < numChars ; ++i ) {
            // starting with the 2nd character, if it's only upper and numbers
            char c = name.charAt( i );
            if ( ! Character.isUpperCase( c ) && ! Character.isDigit( c ) ) {
                // if it's not, stop. just stop.
                return name;
            }
        }
        // then re-uppercase the first
        return name.substring(0,1).toUpperCase() + name.substring(1);
    }

    public interface Fix {
        String fix(String input);
    }

    public static class SimpleFix implements Fix {

        private final String key;

        public SimpleFix(String key) {
            this.key = key;
        }

        @Override
        public String fix(String input) {
            StringBuffer output = new StringBuffer();

            int cur = 0;

            while (cur < input.length()) {
                int loc = input.indexOf(this.key, cur);
                if (loc < 0) {
                    output.append(input.substring(cur));
                    break;
                } else {
                    output.append(input.substring(cur, loc));

                    int nextCharLoc = loc + this.key.length();
                    if ((nextCharLoc >= input.length() )
                            || (nextCharLoc < input.length() && ( Character.isUpperCase(input.charAt(nextCharLoc))) || Character.isDigit( input.charAt(nextCharLoc)))) {
                        output.append(this.key.toUpperCase());
                    } else {
                        output.append(this.key);
                    }
                    cur = loc + this.key.length();
                }
            }

            return output.toString();
        }
    }

    public static class CustomFix implements Fix {

        private final String key;

        private final String replacement;

        public CustomFix(String key, String replacement) {
            this.key = key;
            this.replacement = replacement;
        }

        @Override
        public String fix(String input) {
            return input.replace(this.key, this.replacement);
        }
    }

    private static List<Fix> CLASSNAME_FIXES = new ArrayList<Fix>();

    private static void simpleFix(String s) {
        CLASSNAME_FIXES.add(new SimpleFix(s));
    }

    private static void customFix(String s, String r) {
        CLASSNAME_FIXES.add(new CustomFix(s, r));
    }

    static {
        customFix("Activemq", "ActiveMQ");
        customFix("Jberet", "JBeret");
        simpleFix("Acl");
        simpleFix("Ajp");
        simpleFix("Ee");
        simpleFix("Ejb");
        simpleFix("Ha");
        simpleFix("Http");
        simpleFix("Io");
        simpleFix("Iiop");
        simpleFix("Imap");
        customFix("InVm", "InVM");
        simpleFix("Jaxrs");
        simpleFix("Jca");
        simpleFix("Jdbc");
        customFix("Jgroups", "JGroups");
        simpleFix("Jms");
        simpleFix("Jmx");
        simpleFix("Jpa");
        simpleFix("Jsf");
        simpleFix("Jsp");
        simpleFix("Jsse");
        simpleFix("Mdb");
        simpleFix("Msc");
        simpleFix("Pop3");
        simpleFix("Sasl");
        simpleFix("Smtp");
        simpleFix("Xa");
    }

    public static String fixClassName(String input) {
        for (Fix s : CLASSNAME_FIXES) {
            input = s.fix(input);
        }

        return input;
    }
}
