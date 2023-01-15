package com.diffusion.training.lab2;

import java.io.IOException;

import com.diffusion.training.essentials.BasicDiffusionClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.callbacks.ErrorReason;
import com.pushtechnology.diffusion.client.features.Messaging;
import com.pushtechnology.diffusion.client.features.Messaging.RequestHandler;
import com.pushtechnology.diffusion.client.session.Session;
import com.pushtechnology.diffusion.datatype.InvalidDataException;
import com.pushtechnology.diffusion.datatype.json.JSON;

/**
 * A control client that responds to JSON requests send by the
 * {@link SendingJson} client.
 *
 * @author Push Technology Limited
 * @since 5.7
 */
public final class AddCheckServer extends BasicDiffusionClient {
    private static final Logger LOG = LoggerFactory.
            getLogger(AddCheckServer.class);

    /**
     * Constructor.
     *
     * @param url       The URL to connect to
     * @param principal The principal to connect as
     */
    public AddCheckServer(String url, String principal) {
        super(url, principal);
    }

    @Override
    public void onConnected(Session session) {
        final Messaging messaging = session.feature(Messaging.class);

        // Add a request handler for receiving messages sent to the server
        messaging.addRequestHandler(
                "json",
                JSON.class,
                JSON.class,
                new RequestHandler<JSON, JSON>() {
                    @Override
                    public void onRequest(JSON request, RequestContext context,
                                          Responder<JSON> responder) {
                        Addition add = null;

                        System.out.println("Received request {}" + request);
                        try {
                            add = Utils.parseJson(request, Addition.class);
                            System.out.println(" Server Receives Request " + add.toString());
                        } catch (JsonParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (JsonMappingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (InvalidDataException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                        // Respond to the client that sent a message
                        String response = ((add.getNum1() + add.getNum2()) == add.getSum()) ? "true" : "false";
                        responder.respond(

                                Diffusion.dataTypes().json().fromJsonString(response));
                    }

                    @Override
                    public void onClose() {
                        System.out.println("RequestHandler closed");
                    }

                    @Override
                    public void onError(ErrorReason errorReason) {
                        System.out.println("RequestHandler error: {}" + errorReason);
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
        final AddCheckServer client = new AddCheckServer("ws://localhost:8080", "admin");
        client.start("password");
        client.waitForStopped();
    }
}