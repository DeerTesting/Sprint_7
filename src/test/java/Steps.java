import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.LoginToProfile;
import org.example.Orders;
import org.example.Profile;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

public class Steps {



    @Step("Send post request to /api/v1/courier to create a courier")
    public static Response postRequest(Profile profile){

        return given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(profile)
                        .when()
                        .post("/api/v1/courier");
    }

    @Step("Compare positive response after creating a new profile")
    public static void compareBody(Response response){
        response.then().assertThat().body("ok", equalTo(true)).and().statusCode(201);
    }

    @Step("Compare negative body response")
    public static void compareBodyNegative(Response response, String bodyAnswer, int code){
        response.then().assertThat().body("message", equalTo(bodyAnswer)).and().statusCode(code);
    }

    @Step("Post to login api/v1/courier/login to login")
    public static Response sendPostLoginRequest(LoginToProfile loginData){

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginData)
                .when()
                .post("/api/v1/courier/login");

    }


    @Step("Check if request was successful")
    public static void checkSuccessBody(Response response, String id, int code){
        response.then().assertThat().body(id, notNullValue()).and().statusCode(code);
    }

    @Step("Send post to create order to /api/v1/orders")
    public static Response sendPostOrderCreation(Orders order){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Send get request to get list of orders")
    public static Response getOrderList(){
        return given()
                .get("/api/v1/orders");
    }

    @Step("List of orders is not empty")
    public static void orderListIsNotEmplty(Response response, String id, int code){
        response.then().assertThat().body(id,  not(emptyIterable())).and().statusCode(code);
    }

    @Step("Successful deletion of profile")
    public static Response successDelete(int id){
        return given()
                .delete(String.format("/api/v1/courier/%s", id));
    }

    @Step("Check id deletion was successful")
    public static void checkSuccessDeletion(Response response){
        response.then().assertThat().body("ok", equalTo(true)).and().statusCode(200);
    }

    @Step("Successful deletion of profile")
    public static Response unSuccessDelete(){
        return given()
                .delete("/api/v1/courier");
    }

    @Step("Check id deletion was successful")
    public static void checkUnsuccessDeletion(Response response, String errorText){
        response.then().assertThat().body("message", equalTo(errorText)).and().statusCode(400);
    }
}
