package org.wildfly.apigen.test.invocation;

import org.wildfly.swarm.config.runtime.Address;
import org.wildfly.swarm.config.runtime.ModelNodeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Address("/subsystem=foo-bar")
public class CollectionTypeExample {

    private String key;

    public CollectionTypeExample(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @ModelNodeBinding(detypedName = "mapAttribute")
    public Map<String,String> properties() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        map.put("c", "d");

        return map;
    }

    public void properties(Map<String,String> map) {

    }

    @ModelNodeBinding(detypedName = "listAttribute")
    public List<Integer> items() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        return list;
    }

    public void items(List<Integer> list) {

    }

}
