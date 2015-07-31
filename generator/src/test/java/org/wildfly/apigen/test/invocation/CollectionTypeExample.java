package org.wildfly.apigen.test.invocation;

import org.wildfly.apigen.invocation.Address;
import org.wildfly.apigen.invocation.Binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Address("/subsystem=foo-bar")
public class CollectionTypeExample {

    @Binding(detypedName = "mapAttribute")
    public Map<String,String> getProperties() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        map.put("c", "d");

        return map;
    }

    public void setProperties(Map<String,String> map) {

    }

    @Binding(detypedName = "listAttribute")
    public List<Integer> getItems() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        return list;
    }

    public void setItems(List<Integer> list) {

    }

}
