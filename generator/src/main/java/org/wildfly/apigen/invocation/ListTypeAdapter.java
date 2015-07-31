package org.wildfly.apigen.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import java.util.List;

/**
 * @author Heiko Braun
 * @since 31/07/15
 */
public class ListTypeAdapter {
    public void toDmr(ModelNode modelMode, String detypedName, List value) {

        if(value.isEmpty()) {
            modelMode.get(detypedName).setEmptyList();
        }
        else {
            value.forEach(
                    v -> {
                        ModelType listValueType = Types.resolveModelType(v.getClass());
                        SimpleTypeAdapter.addValueTo(modelMode.get(detypedName), listValueType, v);
                    }
            );
        }

    }
}
