import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ScooterServiceOrder {
    private static final String CREATE_ORDER = "/api/v1/orders";
    private static final String GET_ORDERLIST = "/api/v1/orders";

    private RequestSpecification requestSpecification;

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(requestSpecification)
                .header("Content-type", "application/json")
                .body(order)
                .post(CREATE_ORDER)
                .then()
                .log()
                .all();
    }
    public ValidatableResponse getOrderList () {
        return given()
                .spec(requestSpecification)
                .header("Content-type", "application/json")
                .get(GET_ORDERLIST)
                .then()
                .log()
                .all();
    }
}
