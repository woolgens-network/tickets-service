package net.woolgens.tickets;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import net.woolgens.tickets.model.Ticket;
import net.woolgens.tickets.model.TicketUser;
import net.woolgens.tickets.repository.TicketRepository;
import net.woolgens.tickets.resource.TicketResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(TicketResource.class)
public class TicketResourceTest {

    @Inject
    TicketRepository repository;

    Ticket ticket;

    @BeforeEach
    public void setup() {
        ticket = new Ticket();
        ticket.setId("Test");
        ticket.setTitle("Test");
        ticket.setIssuer(new TicketUser("Test", "Test"));
        ticket.setAssignee(new ArrayList<>());
        ticket.setCategory("Test");
        ticket.setStatus("Open");
        ticket.setTimestamp(System.currentTimeMillis());
        ticket.setOpen(true);
        ticket.setContent(new HashMap<>());
        ticket.setEntries(new ArrayList<>());
        repository.persistOrUpdate(ticket);



    }

    @Test
    public void testGetAllEndpoint() {
        given()
                .when().get("/")
                .then()
                .statusCode(200)
                .body("isEmpty()", is(false));
    }

    @Test
    public void testGetAllQueryEndpoint() {
        for (int i = 0; i < 3; i++) {
            Ticket ticket = new Ticket();
            ticket.setId(UUID.randomUUID().toString());
            ticket.setTitle("Test");
            ticket.setIssuer(new TicketUser("Test", "Test"));
            ticket.setAssignee(new ArrayList<>());
            ticket.setCategory("Test");
            ticket.setStatus("Open");
            ticket.setTimestamp(System.currentTimeMillis());
            ticket.setOpen(true);
            ticket.setContent(new HashMap<>());
            ticket.setEntries(new ArrayList<>());
            repository.persistOrUpdate(ticket);
        }
        given()
                .when().get("/?open=true")
                .then()
                .statusCode(200)
                .body("isEmpty()", is(false));
    }

    @Test
    public void testGetEndpoint() {
        given()
                .when().get("/" + ticket.getId())
                .then()
                .statusCode(200)
                .body("id", is(ticket.getId()));
    }

    @Test
    public void tesPutEndpoint() {
        Ticket ticket = new Ticket();
        ticket.setId(UUID.randomUUID().toString());
        given().body(ticket).contentType(ContentType.JSON)
                .when().put("/")
                .then()
                .statusCode(200)
                .body("id", is(ticket.getId()));
    }


    @Test
    public void tesDeleteEndpoint() {
        given()
                .when().delete("/" + ticket.getId())
                .then()
                .statusCode(200)
                .body(is("true"));
    }

}