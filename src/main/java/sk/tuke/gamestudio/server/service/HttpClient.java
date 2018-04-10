package sk.tuke.gamestudio.server.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class HttpClient {

    private static final String URL = "http://localhost:1705/rest";

    protected Client client() {
        return ClientBuilder.newClient();
    }

    protected <T> T post(String endpoint, Entity<?> entity, Class<T> responseType) {
        return client().target(createUrl(endpoint))
                .request(MediaType.APPLICATION_JSON)
                .post(entity, responseType);
    }

    protected <T> T post(String endpoint, String path, Entity<?> entity, Class<T> responseType) {
        return client().target(createUrl(endpoint))
                .path("/" + path)
                .request(MediaType.APPLICATION_JSON)
                .post(entity, responseType);
    }

    protected <T> T get(String endpoint, GenericType<T> responseType) {
        return client().target(createUrl(endpoint))
                .request(MediaType.APPLICATION_JSON)
                .get(responseType);
    }

    protected <T> T get(String endpoint, String path, GenericType<T> responseType) {
        return client().target(createUrl(endpoint))
                .path("/" + path)
                .request(MediaType.APPLICATION_JSON)
                .get(responseType);
    }

    public static <T> Entity<T> jsonEntity(final T entity) {
        return Entity.entity(entity, MediaType.APPLICATION_JSON);
    }

    private String createUrl(String endpoint) {
        if (endpoint.startsWith("/")) {
            endpoint = endpoint.replaceFirst("/", "");
        }

        return URL + endpoint;
    }

}
