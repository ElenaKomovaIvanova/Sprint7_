import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Type;
import java.util.List;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

public class GetOrdersListTest {
    private static final String SCOOTER_URI = "https://qa-scooter.praktikum-services.ru/";

    private RequestSpecification requestSpecification;
    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    private ScooterServiceOrder client = new ScooterServiceOrder();

    @Test
    @DisplayName("getOrdersList")
    @Description("getOrdersList")
    public void getOrdersList () {

        RequestSpecification requestSpecification =
                new RequestSpecBuilder().setBaseUri(SCOOTER_URI)
                        .setContentType(ContentType.JSON)
                        .build();
        client.setRequestSpecification(requestSpecification);

        Order order1 = new Order("Елена", "Иванова", "Челябинск", 350,
                "8922222222", 5, "05.02.2023", "comment", new String[]{"1"});
        Order order2 = new Order("Марина", "Иванова", "Челябинск", 350,
                "8922222222", 5, "05.02.2023", "comment", new String[]{"1"});

        client.createOrder(order1);
        client.createOrder(order2);

        ValidatableResponse response = client.getOrderList();

        String[] resp = (response.extract().asString()).split("id");

        Assert.assertTrue(resp.length >= 2);
    }
}
