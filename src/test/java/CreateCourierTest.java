import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;


public class CreateCourierTest {
    private static final String SCOOTER_URI = "https://qa-scooter.praktikum-services.ru/";
    public static final Courier COURIER = new Courier("CourierOne", "first","Courier", 0);
    private ScooterServiceClient client = new ScooterServiceClient();

    @Before
    public void client() {
        RequestSpecification requestSpecification =
                new RequestSpecBuilder().setBaseUri(SCOOTER_URI)
                        .setContentType(ContentType.JSON)
                        .build();
        client.setRequestSpecification(requestSpecification);
    }


    @Test
    public void createCourierExpOkTest() {

        ValidatableResponse response = client.createCourier(COURIER);
        response.assertThat().body("ok", CoreMatchers.is(true));
    }

    @Test
    public void createCourierExp201Test() {

        ValidatableResponse response = client.createCourier(COURIER);
        response.assertThat().statusCode(201);

    }

    @Test
    public void createCourierDuplicatesCodTest() {

        ValidatableResponse response1 = client.createCourier(COURIER);
        ValidatableResponse response2 = client.createCourier(COURIER);
        response2.assertThat().statusCode(409);

    }

    @Test
    public void createCourierDuplicatesBodyTest() {

        ValidatableResponse response1 = client.createCourier(COURIER);
        ValidatableResponse response2 = client.createCourier(COURIER);

        assertEquals(response2.assertThat().extract().body().jsonPath().get("message")
                , "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    public void createCourierNoLoginTest() {

        Courier courier = new Courier("first","Courier", 0);
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(400);
    }

    @Test
    public void createCourierNoPasswordTest() {

        Courier courier = Courier.noPassword("CourierOne","Courier", 0);
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(400);

    }

    @Test
    public void createCourierNoPasswordNoLoginTest() {

        Courier courier = new Courier("Courier", 0);
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(400);

    }

    @Test
    public void createCourierAuthorizationTest() {

        ValidatableResponse response = client.createCourier(COURIER);
        assertEquals(200,client.login(Credentials.fromCourier(COURIER)).extract().statusCode());
    }


    @Test
    public void createCourierAuthorizationNoLoginTest() {

        ValidatableResponse response = client.createCourier(COURIER);
        Courier courier = new Courier("first","Courier", 0);
        assertEquals(400,client.login(Credentials.fromCourier(courier)).extract().statusCode());
    }

    @Test
    public void createCourierAuthorizationNoLoginMessageTest() {

        ValidatableResponse response = client.createCourier(COURIER);
        Courier courier = new Courier("first","Courier", 0);
        assertEquals("Недостаточно данных для входа",client.login(Credentials.fromCourier(courier)).
                extract().jsonPath().get("message"));
    }

    @Test
    public void createCourierAuthorizationNoPasswordTest() {

        ValidatableResponse response = client.createCourier(COURIER);
        Courier courier = Courier.noPassword("CourierOne","Courier", 0);
        assertEquals(400,client.login(Credentials.fromCourier(courier)).extract().statusCode());
    }

    @Test
    public void createCourierAuthorizationNoPasswordMessageTest() {

        ValidatableResponse response = client.createCourier(COURIER);
        Courier courier = Courier.noPassword("CourierOne","Courier", 0);
        assertEquals("Недостаточно данных для входа",client.login(Credentials.fromCourier(courier)).
                extract().jsonPath().get("message"));
    }

    @Test
    public void createCourierAuthorizationLoginErrorTest() {

        Courier courier = new Courier("CourierOne+добавили_текст", "first","Courier", 0);
        ValidatableResponse response = client.createCourier(COURIER);
        assertEquals(404,client.login(Credentials.fromCourier(courier)).extract().statusCode());
    }

    @Test
    public void createCourierAuthorizationLoginErrorMessageTest() {

        Courier courier = new Courier("CourierOne+добавили_текст", "first","Courier", 0);
        ValidatableResponse response = client.createCourier(COURIER);
        assertEquals("Учетная запись не найдена",client.login(Credentials.fromCourier(courier)).
                extract().jsonPath().get("message"));
    }

    @Test
    public void authorizationContainsIdTest() {

        ValidatableResponse response = client.createCourier(COURIER);
        assertNotNull(client.login(Credentials.fromCourier(COURIER)).
                extract().body().jsonPath().get("id"));
    }


    @After
    public void deleteCourier () {
        if (client.login(Credentials.fromCourier(COURIER))
                .extract().statusCode() == 200 ) {
            Integer id = client.login(Credentials.fromCourier(COURIER))
                    .extract().body().jsonPath().getInt("id");
            client.deleteCourierByID(new Id(id));
        }
    }
}





