import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Orders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    Orders order;
    private String[] color;
    private int expectedCode;



    public OrderCreationTest(String[] color, int expectedCode) {
        this.color = color;
        this.expectedCode = expectedCode;
    }



    @Parameterized.Parameters
    public static Object[][] getColour(){
        return new Object[][]{
                {new String[] {"BLACK"}, 201},
                {new String[] {"GREY"}, 201},
                {new String[] {"BLACK","GREY"}, 201},
                {new String[] {}, 201},


        };
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";


    }

    @Test
    public void successOrderCreation(){
        order = new Orders("firstName", "lastName", "city, house, 3", "4", "+79993331155", 2, "2024.05.20", "comment", color);
        Response response = Steps.sendPostOrderCreation(order);
        Steps.checkSuccessBody(response, "track", expectedCode);

    }

    @After
    public void tearDown(){
        int track = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders").then().extract().path("track");

        given()
                .header("Content-type", "application/json")
                .and()
                .put(String.format("/api/v1/orders/cancel/%s", track));
    }
}
