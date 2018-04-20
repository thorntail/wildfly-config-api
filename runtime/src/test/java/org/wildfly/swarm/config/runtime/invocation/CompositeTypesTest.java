package org.wildfly.swarm.config.runtime.invocation;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CompositeTypesTest {
    @Test
    public void listOfMaps() {
        List list = new ArrayList() {{
            add(new HashMap() {{
                put("foo", 1L);
                put("bar", 2L);
            }});
            add(new HashMap() {{
                put("baz", 3L);
                put("quux", 4L);
            }});
        }};

        ModelNode target = new ModelNode();
        new ListTypeAdapter().toDmr(target, list);

        assertEquals(ModelType.LIST, target.getType());

        {
            assertTrue(target.has(0));
            ModelNode element = target.get(0);
            assertEquals(ModelType.OBJECT, element.getType());

            assertTrue(element.has("foo"));
            assertEquals(ModelType.LONG, element.get("foo").getType());
            assertEquals(1L, element.get("foo").asLong());

            assertTrue(element.has("bar"));
            assertEquals(ModelType.LONG, element.get("bar").getType());
            assertEquals(2L, element.get("bar").asLong());

            assertFalse(element.has("baz"));
            assertFalse(element.has("quux"));
        }

        {
            assertTrue(target.has(1));
            ModelNode element = target.get(1);
            assertEquals(ModelType.OBJECT, element.getType());

            assertTrue(element.has("baz"));
            assertEquals(ModelType.LONG, element.get("baz").getType());
            assertEquals(3L, element.get("baz").asLong());

            assertTrue(element.has("quux"));
            assertEquals(ModelType.LONG, element.get("quux").getType());
            assertEquals(4L, element.get("quux").asLong());

            assertFalse(element.has("foo"));
            assertFalse(element.has("bar"));
        }

        assertFalse(target.has(2));
    }

    @Test
    public void mapOfLists() {
        Map map = new HashMap() {{
            put("foo", new ArrayList() {{
                add(1L);
                add("2");
            }});
            put("bar", new ArrayList() {{
                add("3");
                add(4L);
            }});
        }};

        ModelNode target = new ModelNode();
        new MapTypeAdapter().toDmr(target, map);

        assertEquals(ModelType.OBJECT, target.getType());

        {
            assertTrue(target.has("foo"));
            ModelNode element = target.get("foo");
            assertEquals(ModelType.LIST, element.getType());

            assertTrue(element.has(0));
            assertEquals(ModelType.LONG, element.get(0).getType());
            assertEquals(1L, element.get(0).asLong());

            assertTrue(element.has(1));
            assertEquals(ModelType.STRING, element.get(1).getType());
            assertEquals("2", element.get(1).asString());

            assertFalse(element.has(2));
        }

        {
            assertTrue(target.has("bar"));
            ModelNode element = target.get("bar");
            assertEquals(ModelType.LIST, element.getType());

            assertTrue(element.has(0));
            assertEquals(ModelType.STRING, element.get(0).getType());
            assertEquals("3", element.get(0).asString());

            assertTrue(element.has(1));
            assertEquals(ModelType.LONG, element.get(1).getType());
            assertEquals(4L, element.get(1).asLong());

            assertFalse(element.has(2));
        }

        assertFalse(target.has("baz"));
        assertFalse(target.has("quux"));
    }

    @Test
    public void listOfMapsOfLists() {
        List list = new ArrayList() {{
            add(new HashMap() {{
                put("foo", new ArrayList() {{
                    add(1L);
                    add("2");
                    add(true);
                }});
                put("bar", new ArrayList() {{
                    add("3");
                    add(4L);
                    add(false);
                }});
            }});
            add(new HashMap() {{
                put("baz", new ArrayList() {{
                    add(5L);
                    add("6");
                }});
                put("quux", new ArrayList() {{
                    add("7");
                    add(8L);
                }});
            }});
        }};

        ModelNode target = new ModelNode();
        new ListTypeAdapter().toDmr(target, list);

        assertEquals(ModelType.LIST, target.getType());

        {
            assertTrue(target.has(0));
            ModelNode element = target.get(0);
            assertEquals(ModelType.OBJECT, element.getType());

            {
                assertTrue(element.has("foo"));
                assertEquals(ModelType.LIST, element.get("foo").getType());
                ModelNode nestedList = element.get("foo");

                assertTrue(nestedList.has(0));
                assertEquals(ModelType.LONG, nestedList.get(0).getType());
                assertEquals(1L, nestedList.get(0).asLong());

                assertTrue(nestedList.has(1));
                assertEquals(ModelType.STRING, nestedList.get(1).getType());
                assertEquals("2", nestedList.get(1).asString());

                assertTrue(nestedList.has(2));
                assertEquals(ModelType.BOOLEAN, nestedList.get(2).getType());
                assertTrue(nestedList.get(2).asBoolean());

                assertFalse(nestedList.has(3));
            }

            {
                assertTrue(element.has("bar"));
                assertEquals(ModelType.LIST, element.get("bar").getType());
                ModelNode nestedList = element.get("bar");

                assertTrue(nestedList.has(0));
                assertEquals(ModelType.STRING, nestedList.get(0).getType());
                assertEquals("3", nestedList.get(0).asString());

                assertTrue(nestedList.has(1));
                assertEquals(ModelType.LONG, nestedList.get(1).getType());
                assertEquals(4L, nestedList.get(1).asLong());

                assertTrue(nestedList.has(2));
                assertEquals(ModelType.BOOLEAN, nestedList.get(2).getType());
                assertFalse(nestedList.get(2).asBoolean());

                assertFalse(nestedList.has(3));
            }

            assertFalse(element.has("baz"));
            assertFalse(element.has("quux"));
        }

        {
            assertTrue(target.has(1));
            ModelNode element = target.get(1);
            assertEquals(ModelType.OBJECT, element.getType());

            {
                assertTrue(element.has("baz"));
                assertEquals(ModelType.LIST, element.get("baz").getType());
                ModelNode nestedList = element.get("baz");

                assertTrue(nestedList.has(0));
                assertEquals(ModelType.LONG, nestedList.get(0).getType());
                assertEquals(5L, nestedList.get(0).asLong());

                assertTrue(nestedList.has(1));
                assertEquals(ModelType.STRING, nestedList.get(1).getType());
                assertEquals("6", nestedList.get(1).asString());

                assertFalse(nestedList.has(2));
            }

            {
                assertTrue(element.has("quux"));
                assertEquals(ModelType.LIST, element.get("quux").getType());
                ModelNode nestedList = element.get("quux");

                assertTrue(nestedList.has(0));
                assertEquals(ModelType.STRING, nestedList.get(0).getType());
                assertEquals("7", nestedList.get(0).asString());

                assertTrue(nestedList.has(1));
                assertEquals(ModelType.LONG, nestedList.get(1).getType());
                assertEquals(8L, nestedList.get(1).asLong());

                assertFalse(nestedList.has(2));
            }

            assertFalse(element.has("foo"));
            assertFalse(element.has("bar"));
        }

        assertFalse(target.has(2));
    }
}
