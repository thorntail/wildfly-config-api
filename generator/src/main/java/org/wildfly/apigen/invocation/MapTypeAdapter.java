package org.wildfly.apigen.invocation;

import org.jboss.dmr.ModelNode;

import java.util.Map;

/**
 * @author Heiko Braun
 * @since 31/07/15
 */
public class MapTypeAdapter {

    public void toDmr(ModelNode modelMode, String detypedName, Map<String,String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            modelMode.get(detypedName).get(entry.getKey()).set(entry.getValue());
        }
    }
}
