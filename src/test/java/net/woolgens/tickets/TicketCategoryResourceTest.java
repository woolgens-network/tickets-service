package net.woolgens.tickets;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import net.woolgens.tickets.model.Ticket;
import net.woolgens.tickets.model.TicketCategory;
import net.woolgens.tickets.model.TicketUser;
import net.woolgens.tickets.repository.TicketCategoryRepository;
import net.woolgens.tickets.repository.TicketRepository;
import net.woolgens.tickets.resource.TicketCategoryResource;
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
@TestHTTPEndpoint(TicketCategoryResource.class)
public class TicketCategoryResourceTest {

    @Inject
    TicketCategoryRepository repository;

    TicketCategory category;

    @BeforeEach
    public void setup() {
        category = new TicketCategory();
        category.setId(UUID.randomUUID().toString());
        repository.persistOrUpdate(category);

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
    public void testGetEndpoint() {
        given()
                .when().get("/" + category.getId())
                .then()
                .statusCode(200)
                .body("id", is(category.getId()));
    }

    @Test
    public void tesPutEndpoint() {
        TicketCategory category = new TicketCategory();
        category.setId(UUID.randomUUID().toString());
        given().body(category).contentType(ContentType.JSON)
                .when().put("/")
                .then()
                .statusCode(200)
                .body("id", is(category.getId()));
    }


    @Test
    public void testDeleteEndpoint() {
        given()
                .when().delete("/" + category.getId())
                .then()
                .statusCode(200)
                .body(is("true"));
    }

}