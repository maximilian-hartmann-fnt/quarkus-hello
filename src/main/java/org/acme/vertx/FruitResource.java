package org.acme.vertx;

import java.net.URI;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.inject.Inject;
import javax.annotation.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.common.annotation.*;
import io.vertx.mutiny.pgclient.PgPool;


@Path("fruits")
public class FruitResource {


    @Inject
    io.vertx.mutiny.pgclient.PgPool client;


    @Inject
    @ConfigProperty(name= "myapp.schema.create", defaultValue = "true")
    boolean schemaCreate;

    
    @PostConstruct
    @Blocking
    void config() {
        if (schemaCreate) {
            initdb();
        }
    }

    @Blocking
    private void initdb() {
        client.query("DROP TABLE IF EXISTS fruits").execute()
                .flatMap(r -> client.query("CREATE TABLE fruits (id SERIAL PRIMARY KEY, name TEXT NOT NULL)").execute())
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Orange')").execute())
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Pear')").execute())
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Apple')").execute())
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Lychee')").execute())
                .await().indefinitely();
    }

    public FruitResource(PgPool client){
        this.client = client;
    }

    @GET
    public Multi<Fruit> get() {
        return Fruit.findAll(client);
    }


}