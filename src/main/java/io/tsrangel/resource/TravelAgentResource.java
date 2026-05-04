package io.tsrangel.resource;

import io.tsrangel.context.SecurityContext;
import io.tsrangel.service.aiServices.PackageExpert;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/travel-agent")
public class TravelAgentResource {
    @Inject
    PackageExpert assistant;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String ask(String question, @HeaderParam("X-User-Name") String userName) {
        if (userName != null && !userName.isEmpty()) {
            try {
                SecurityContext.setCurrentUser(userName);
                return assistant.chat(userName,question);
            } finally {
                SecurityContext.clearCurrentUser();
            }
        } else {
            return "Usuário precisa estar autenticado para usar o assistente de viagens.";
        }
    }
}
