package net.woolgens.tickets.resource;

import io.quarkus.mongodb.panache.PanacheQuery;
import net.woolgens.library.microservice.exception.ServiceException;
import net.woolgens.tickets.model.Ticket;
import net.woolgens.tickets.repository.TicketRepository;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/tickets/")
public class TicketResource {

    @Inject
    TicketRepository repository;

    @GET
    @RolesAllowed({"User", "Moderator", "Admin"})
    public List<Ticket> getAll(@Context SecurityContext context,
                               @QueryParam("assignee") String assignee,
                               @QueryParam("category") String category,
                               @QueryParam("issuer") String issuer,
                               @QueryParam("status") String status,
                               @QueryParam("open") String open) {
        String query = "";
        Map<String, Object> params = new HashMap<>();
        if(context.isUserInRole("Admin") || context.isUserInRole("Moderator")) {
            if(category != null) {
                params.put("category", category);
                query = addQuery(query, "category");
            }
            if(assignee != null) {
                params.put("assignee.uuid", assignee);
                query = addQuery(query, "assignee.uuid");
            }
            if(issuer != null) {
                params.put("issuer.uuid", issuer);
                query = addQuery(query, "issuer.uuid");
            }
        } else {
            params.put("issuer.uuid", context.getUserPrincipal() == null ? "Test" : context.getUserPrincipal().getName());
            query = addQuery(query, "issuer.uuid");
        }
        if(status != null) {
            params.put("status", status);
            query = addQuery(query, "status");
        }
        if(open != null) {
            params.put("open", Boolean.valueOf(open));
            query = addQuery(query, "open");
        }
        PanacheQuery<Ticket> result = repository.find("{" + query + "}", params);
        List<Ticket> list = result.list();
        return list;
    }

    private String addQuery(String query, String type) {
        String result = query.isEmpty() ? ("'" + type + "': :" + type) : query + ", " + ("'" + type + "': :" + type);
        return result;
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"User", "Moderator", "Admin"})
    public Ticket get(@PathParam("id") String id, @Context SecurityContext context) throws ServiceException {
        Optional<Ticket> optional = repository.findByIdOptional(id);
        if(!optional.isPresent()) {
            throw new ServiceException(404, "Ticket not found");
        }
        Ticket ticket = optional.get();
        if(context.getUserPrincipal() != null) {
            if(!context.isUserInRole("Admin") && !context.isUserInRole("Moderator")) {
                String requester = context.getUserPrincipal().getName();
                if(!ticket.getIssuer().getUuid().equals(requester)) {
                    throw new ServiceException(403, "Not allowed to view ticket");
                }
            }
        }

        return ticket;
    }

    @PUT
    @RolesAllowed({"User", "Moderator", "Admin"})
    public Ticket put(Ticket changelog) {
        repository.persistOrUpdate(changelog);
        return changelog;
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("Admin")
    public boolean delete(@PathParam("id") String id) {
        return repository.deleteById(id);
    }
}