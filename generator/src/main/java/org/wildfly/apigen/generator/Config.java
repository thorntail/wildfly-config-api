package org.wildfly.apigen.generator;


import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * See https://github.com/ralfstx/minimal-json
 *
 * @author Heiko Braun
 * @since 29/07/15
 */
public class Config {

    private JsonArray json;

    public Config(JsonArray json) {
        this.json = json;
    }

    public List<ResourceRef> getReferences() {

        List<ResourceRef> references = new ArrayList<>();
        json.forEach(ref -> {
            JsonObject atts = ref.asObject();
            references.add(
                    new ResourceRef(
                            atts.get("sourceAddress").asString(),
                            atts.get("targetPackage").asString()
                            )
            );
        });
        return references;
    }

    public static Config fromJson(String filename) throws Exception {
        return new Config(
                Json.parse(
                        new FileReader(filename)
                ).asObject().get("modelReferences").asArray()
        );
    }
}
