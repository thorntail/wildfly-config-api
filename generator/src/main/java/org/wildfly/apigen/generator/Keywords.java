package org.wildfly.apigen.generator;

/**
 * Java Language Keywords
 *
 * @author Heiko Braun
 * @since 30/07/15
 */
public class Keywords {

    private final static String[] KEYWORDS = new String[] {"abstract","continue","for","new","switch",
            "assert","default","goto","package","synchronized",
            "boolean","do","if","private","this",
            "break","double","implements","protected","throw",
            "byte","else","import","public","throws",
            "case","enum","instanceof","return","transient",
            "catch","extends","int","short","try",
            "char","final","interface","static","void",
            "class","finally","long","volatile",
            "const","float","native","super","while"};

     public static String escape(String token) {
         for (String keyword : KEYWORDS) {
             if(token.equals(keyword))
                 return "Attribute_"+token;
         }

         return token;
     }

}
