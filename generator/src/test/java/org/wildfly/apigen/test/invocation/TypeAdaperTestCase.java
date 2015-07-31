package org.wildfly.apigen.test.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.junit.Assert;
import org.junit.Test;
import org.wildfly.apigen.invocation.EntityAdapter;

/**
 * @author Heiko Braun
 * @since 31/07/15
 */
public class TypeAdaperTestCase {

    @Test
    public void testMapTypeAdapter() throws Exception {
        EntityAdapter<CollectionTypeExample> entityAdapter = new EntityAdapter<>(CollectionTypeExample.class);
        ModelNode modelNode = entityAdapter.fromEntity(new CollectionTypeExample());

        ModelNode someAttribute = modelNode.get("mapAttribute");

        Assert.assertEquals(ModelType.OBJECT, someAttribute.getType());
        Assert.assertTrue(someAttribute.keys().contains("a"));
        Assert.assertEquals("b", someAttribute.get("a").asString());
    }

    @Test
    public void testListTypeAdapter() throws Exception {
        EntityAdapter<CollectionTypeExample> entityAdapter = new EntityAdapter<>(CollectionTypeExample.class);
        ModelNode modelNode = entityAdapter.fromEntity(new CollectionTypeExample());

        ModelNode someAttribute = modelNode.get("listAttribute");
        Assert.assertEquals(ModelType.LIST, someAttribute.getType());
        Assert.assertTrue(someAttribute.asList().size()==3);
    }
}
