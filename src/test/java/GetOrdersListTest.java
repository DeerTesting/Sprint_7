import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class GetOrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void getOrdersList(){
        Response response = Steps.getOrderList();
        Steps.orderListIsNotEmplty(response, "orders", 200);
    }
}
