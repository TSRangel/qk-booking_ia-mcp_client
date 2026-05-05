package io.tsrangel.resource;

import io.tsrangel.service.aiServices.PackageExpert;
import io.tsrangel.service.aiServices.PackageExpertWithTemplate;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/travel-agent")
public class TravelAgentResource {
    @Inject
    PackageExpertWithTemplate assistant;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String ask(String question, @HeaderParam("X-User-Name") String userName) {
        return assistant.chat(userName, question, userName);
    }
}