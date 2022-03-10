package net.woolgens.tickets.resource;

import net.woolgens.library.microservice.exception.ServiceException;
import net.woolgens.tickets.model.TicketStatus;
import net.woolgens.tickets.model.TicketStatus;
import net.woolgens.tickets.repository.TicketStatusRepository;
import net.woolgens.tickets.repository.TicketStatusRepository;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Optional;

@Path("/status/")
public class TicketStatusResource {

    @Inject
    TicketStatusRepository repository;

    @GET
    public List<TicketStatus> getAll() {
        return repository.listAll();
    }


    @GET
    @Path("{id}")
    public TicketStatus get(@PathParam("id") String id) throws ServiceException {
        Optional<TicketStatus> optional = repository.findByIdOptional(id);
        if(!optional.isPresent()) {
            throw new ServiceException(404, "TicketStatus not found");
        }
        return optional.get();
    }

    @PUT
    @RolesAllowed("Admin")
    public TicketStatus put(TicketStatus changelog) {
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