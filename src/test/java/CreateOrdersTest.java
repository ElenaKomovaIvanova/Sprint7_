import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CreateOrdersTest {
    private static final String CREATE_ORDER = "/api/v1/orders";
    private static final String SCOOTER_URI = "https://qa-scooter.praktikum-services.ru/";
    private RequestSpecification requestSpecification;
    private ScooterServiceOrder client = new ScooterServiceOrder();
    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public String firstName;
    public String lastName;
    public String address;
    public int metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public String[] color;

    public CreateOrdersTest( String firstName, String lastName,
                             String address, int metroStation, String phone, int rentTime, String deliveryDate,
                             String comment, String[] color) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Создание заказа.Параметры.Тестовые данные: {0} {1} {2} {3} {4} {5} {6} {7} {8}")
    public static Object[][] CreateOrderPar() {
        return new Object[][]{
                {"Елена", "Иванова", "Москва", 5, "89222222222", 4, "10.09.2023", "Комментарий ", new String[]{"BLACK"}},
                {"Елена", "Иванова", "Москва", 5, "89222222222", 4, "10.09.2023", "Комментарий ", new String[]{"CREY"}},
                {"Елена", "Иванова", "Москва", 5, "89222222222", 4, "10.09.2023", "Комментарий ", new String[]{"BLACK", "GREY"}},
                {"Елена", "Иванова", "Москва", 5, "89222222222", 4, "10.09.2023", "Комментарий ", new String[]{}},

        };
    }


    @Before
    public void client() {
        requestSpecification =
                new RequestSpecBuilder().setBaseUri(SCOOTER_URI)
                        .setContentType(ContentType.JSON)
                        .build();
        client.setRequestSpecification(requestSpecification);
    }

    @Test
    @DisplayName("createOrderCode201Test")
    @Description("createOrderCode201Test")
    public void createOrderCode201Test () {
        Order order = new Order(firstName,lastName,address, metroStation,
                phone, rentTime, deliveryDate,comment,color);
        ValidatableResponse response = client.createOrder(order);
        response.assertThat().statusCode(201);
    }

    @Test
    @DisplayName("createOrderContainsTrackTest")
    @Description("createOrderContainsTrackTest")
    public void createOrderContainsTrackTest () {

        Order order = new Order(firstName,lastName,address, metroStation,
                phone, rentTime, deliveryDate,comment,color);
        ValidatableResponse response = client.createOrder(order);
        assertNotNull(response.extract().body().jsonPath().get("track"));
    }
}
