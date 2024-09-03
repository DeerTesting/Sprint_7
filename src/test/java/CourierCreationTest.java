import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.LoginToProfile;
import org.example.Profile;
import org.example.RandomString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class CourierCreationTest {
    Profile profile;
    String sameCourierBody = "Этот логин уже используется";

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        RandomString loginPassword = new RandomString();
        profile = new Profile(loginPassword.getRandomString(10),loginPassword.getRandomString(10) ,loginPassword.getRandomString(5));
    }

    @Test
    public void successCreation(){
        Response response = Steps.postRequest(profile);
        Steps.compareBody(response);

    }

    @Test
    public void creationOfTheSameCourier(){
        Steps.postRequest(profile);
        Response response = Steps.postRequest(profile);
        Steps.compareBodyNegative(response, sameCourierBody, 409);
    }

    @After
    public void tearDown(){
        LoginToProfile loginToProfile = new LoginToProfile(profile.getLogin(),profile.getPassword());

        int id = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginToProfile)
                .when()
                .post("/api/v1/courier/login").then().extract().path("id");

        given()
                .delete(String.format("/api/v1/courier/%s", id));
    }
}
