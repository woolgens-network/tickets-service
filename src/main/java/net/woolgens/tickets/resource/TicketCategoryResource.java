package net.woolgens.tickets.resource;

import net.woolgens.tickets.model.TicketCategory;
import net.woolgens.tickets.repository.TicketCategoryRepository;
import net.woolgens.library.microservice.exception.ServiceException;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Optional;

@Path("/categories/")
public class TicketCategoryResource {

    @Inject
    TicketCategoryRepository repository;

    @GET
    public List<TicketCategory> getAll() {
        return repository.listAll();
    }


    @GET
    @Path("{id}")
    public TicketCategory get(@PathParam("id") String id) throws ServiceException {
        Optional<TicketCategory> optional = repository.findByIdOptional(id);
        if(!optional.isPresent()) {
            throw new ServiceException(404, "TicketCategory not found");
        }
        return optional.get();
    }

    @PUT
    @RolesAllowed("Admin")
    public TicketCategory put(TicketCategory changelog) {
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