package org.wildfly.swarm.config.generator.generator;


import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

    public List<GeneratorTarget> getGeneratorTargets() {

        List<GeneratorTarget> references = new ArrayList<>();
        json.get("generatorTargets").asArray().forEach(ref -> {
            //JsonObject atts = ref.asObject();
            references.add(new GeneratorTarget(ref.asString()));
        });
        return references;
    }

    public String getModuleName() {
        return this.json.getString( "module", "unkonwn" );
    }

    public Path getModulePath() {
        StringTokenizer tokens = new StringTokenizer( getModuleName(), "." );

        Path path = null;

        while ( tokens.hasMoreTokens() ) {
            if ( path == null ) {
                path = Paths.get( tokens.nextToken() );
            } else {
                path = path.resolve( tokens.nextToken() );
            }
        }

        return path;
    }

    public Path getModulePath(String slot) {
        return getModulePath().resolve( Paths.get( slot, "module.xml" ) );
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
