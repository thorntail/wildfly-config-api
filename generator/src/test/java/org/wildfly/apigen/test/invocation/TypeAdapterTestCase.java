package org.wildfly.apigen.test.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.junit.Assert;
import org.junit.Test;
import org.wildfly.apigen.invocation.EntityAdapter;
import org.wildfly.swarm.config.mail.subsystem.mailSession.MailSession;

import java.util.Iterator;

/**
 * @author Heiko Braun
 * @since 31/07/15
 */
public class TypeAdapterTestCase {

    @Test
    public void testMapJavaType() throws Exception {
        EntityAdapter<CollectionTypeExample> entityAdapter = new EntityAdapter<>(CollectionTypeExample.class);
        ModelNode modelNode = entityAdapter.fromEntity(new CollectionTypeExample("Test"));

        ModelNode someAttribute = modelNode.get("mapAttribute");

        Assert.assertEquals(ModelType.OBJECT, someAttribute.getType());
        Assert.assertTrue(someAttribute.keys().contains("a"));
        Assert.assertEquals("b", someAttribute.get("a").asString());
    }

    @Test
    public void testListJavaType() throws Exception {
        EntityAdapter<CollectionTypeExample> entityAdapter = new EntityAdapter<>(CollectionTypeExample.class);
        ModelNode modelNode = entityAdapter.fromEntity(new CollectionTypeExample("Test"));

        ModelNode someAttribute = modelNode.get("listAttribute");
        Assert.assertEquals(ModelType.LIST, someAttribute.getType());
        Assert.assertTrue(someAttribute.asList().size() == 3);
    }

    @Test
    public void testSimpleJavaType() throws Exception {
        EntityAdapter<MailSession> entityAdapter = new EntityAdapter<>(MailSession.class);

        MailSession mailSession = new MailSession("TestMail");
        mailSession.from("john.doe");
        mailSession.debug(true);

        ModelNode modelNode = entityAdapter.fromEntity(mailSession);

        Assert.assertTrue(modelNode.keys().contains("from"));
        Assert.assertTrue(modelNode.keys().contains("debug"));

        Assert.assertEquals("john.doe", modelNode.get("from").asString());
        Assert.assertEquals(Boolean.TRUE, modelNode.get("debug").asBoolean());
    }


    @Test
    public void testSimpleDmrType() throws Exception {
        ModelNode modelNode = new ModelNode();
        modelNode.get("from").set("john.doe");
        modelNode.get("debug").set(true);

        EntityAdapter<MailSession> entityAdapter = new EntityAdapter<>(MailSession.class);
        MailSession mailSession = entityAdapter.fromDMR("key", modelNode);

        Assert.assertNotNull(mailSession);
        Assert.assertEquals("john.doe", mailSession.from());
        Assert.assertEquals(Boolean.TRUE, mailSession.debug());

    }

    @Test
    public void testListDmrType() throws Exception {
        ModelNode modelNode = new ModelNode();
        modelNode.get("listAttribute").add(1);
        modelNode.get("listAttribute").add(2);
        modelNode.get("listAttribute").add(3);

        EntityAdapter<CollectionTypeExample> entityAdapter = new EntityAdapter<>(CollectionTypeExample.class);
        CollectionTypeExample collectionTypes = entityAdapter.fromDMR("key", modelNode);

        Assert.assertNotNull(collectionTypes);

        Assert.assertNotNull(collectionTypes.items());
        Assert.assertTrue(collectionTypes.items().get(0) == 1);
        Assert.assertTrue(collectionTypes.items().get(1) == 2);

    }

    @Test
    public void testObjectDmrType() throws Exception {
        ModelNode modelNode = new ModelNode();

        modelNode.get("mapAttribute").add("a", "b");
        modelNode.get("mapAttribute").add("c", "d");

        EntityAdapter<CollectionTypeExample> entityAdapter = new EntityAdapter<>(CollectionTypeExample.class);
        CollectionTypeExample collectionTypes = entityAdapter.fromDMR("key", modelNode);

        Assert.assertNotNull(collectionTypes);

        Assert.assertNotNull(collectionTypes.properties());
        Assert.assertEquals(2, collectionTypes.properties().size());

        Iterator<String> iterator = collectionTypes.properties().keySet().iterator();
        Assert.assertEquals("a", iterator.next());
        Assert.assertEquals("c", iterator.next());

    }

}
