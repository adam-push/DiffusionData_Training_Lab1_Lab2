package com.diffusion.training.lab2;

import java.util.concurrent.CompletableFuture;

import com.diffusion.training.essentials.BasicDiffusionClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pushtechnology.diffusion.client.features.Messaging;
import com.pushtechnology.diffusion.client.session.Session;
import com.pushtechnology.diffusion.datatype.json.JSON;

public class AddCheckRequestClient extends BasicDiffusionClient {

    public AddCheckRequestClient(String url, String principal) {
        super(url, principal);

    }

    @Override
    public void onConnected(Session session) {

        final JSON request;

        try {
            request = Utils.toJSON(new Addition(2, 2, 4));
        } catch (JsonProcessingException e) {
            System.out.println("Failed to transform RandomData to Content");
            return;
        }

        final Messaging messaging = session.feature(Messaging.class);

        // Send the request to the server
        final CompletableFuture<JSON> response =
                messaging.sendRequest(
                        "json/request",
                        request,
                        JSON.class,
                        JSON.class);

        response.whenComplete((result, error) -> {
            if (error != null) {
                error.printStackTrace();
                System.out.println("Request failed " + error.getMessage());
            } else {
                System.out.println("Received response {} " + result);
            }
        });
    }

    /**
     * Entry point for the example.
     *
     * @param args The command line arguments
     * @throws InterruptedException If the main thread was interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        final AddCheckRequestClient client =
                new AddCheckRequestClient("ws://localhost:8080", "admin");
        client.start("password");
        client.waitForStopped();
    }

}
