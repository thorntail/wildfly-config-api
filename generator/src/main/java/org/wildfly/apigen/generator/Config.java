package org.wildfly.apigen.generator;


import com.eclipsesource.json.Json;
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

    private JsonObject json;

    public Config(JsonObject json) {
        this.json = json;
    }

    public List<ModelSegment> getReferences() {

        List<ModelSegment> references = new ArrayList<>();
        json.get("modelReferences").asArray().forEach(ref -> {
            JsonObject atts = ref.asObject();
            references.add(
                    new ModelSegment(
                            atts.get("sourceAddress").asString(),
                            atts.get("targetPackage").asString()
                    )
            );
        });
        return references;
    }

    public String getUser() {
        JsonObject server = json.get("server").asObject();
        return server.get("user").asString();
    }

    public String getPass() {
        JsonObject server = json.get("server").asObject();
                return server.get("pass").asString();
    }

    public int getPort() {
        JsonObject server = json.get("server").asObject();
        return server.get("port").asInt();
    }

    public String getHost() {
        JsonObject server = json.get("server").asObject();
        return server.get("host").asString();
    }

    public static Config fromJson(String filename) throws Exception {
        return new Config(
                Json.parse(
                        new FileReader(filename)
                ).asObject()
        );
    }

}
