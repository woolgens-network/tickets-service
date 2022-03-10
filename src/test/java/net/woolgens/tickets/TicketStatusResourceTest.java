package net.woolgens.tickets;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import net.woolgens.tickets.model.TicketCategory;
import net.woolgens.tickets.model.TicketStatus;
import net.woolgens.tickets.repository.TicketCategoryRepository;
import net.woolgens.tickets.repository.TicketStatusRepository;
import net.woolgens.tickets.resource.TicketCategoryResource;
import net.woolgens.tickets.resource.TicketStatusResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(TicketStatusResource.class)
public class TicketStatusResourceTest {

    @Inject
    TicketStatusRepository repository;

    TicketStatus status;

    @BeforeEach
    public void setup() {
        status = new TicketStatus();
        status.setId(UUID.randomUUID().toString());
        repository.persistOrUpdate(status);

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
                .when().get("/" + status.getId())
                .then()
                .statusCode(200)
                .body("id", is(status.getId()));
    }

    @Test
    public void tesPutEndpoint() {
        TicketStatus status = new TicketStatus();
        status.setId(UUID.randomUUID().toString());
        given().body(status).contentType(ContentType.JSON)
                .when().put("/")
                .then()
                .statusCode(200)
                .body("id", is(status.getId()));
    }


    @Test
    public void testDeleteEndpoint() {
        given()
                .when().delete("/" + status.getId())
                .then()
                .statusCode(200)
                .body(is("true"));
    }

}