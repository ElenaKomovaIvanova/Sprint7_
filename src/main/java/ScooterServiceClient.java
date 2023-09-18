import groovy.transform.ToString;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ScooterServiceClient {

    private static final String CREATE_COURIER = "/api/v1/courier";
    private static final String COURIER_LOGIN = "/api/v1/courier/login";
    private static final String DELETE_COURIER = "/api/v1/courier/";

    private RequestSpecification requestSpecification;

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public ValidatableResponse createCourier(Courier courier) {
        return  given()
                .spec(requestSpecification)
                .header("Content-type", "application/json")
                .body(courier)
                .post(CREATE_COURIER)
                .then()
                .log()
                .all();
    }

    public ValidatableResponse login(Credentials credentials) {
        return  given()
                .spec(requestSpecification)
                .header("Content-type", "application/json")
                .body(credentials)
                .post(COURIER_LOGIN)
                .then()
                .log()
                .all();
    }

    public ValidatableResponse deleteCourierByID(Id id) {
        return  given()
                .spec(requestSpecification)
                .delete(DELETE_COURIER + Integer.toString(id.getId()))
                .then()
                .log()
                .all();
    }
}
