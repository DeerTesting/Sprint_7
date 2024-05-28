import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.LoginToProfile;
import org.example.Profile;
import org.example.RandomString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;
import static io.restassured.RestAssured.given;


public class LoginTest {

    Profile profile;

    Profile deletedProfile;
    LoginToProfile loginData;
    String noFieldAnswer = "Недостаточно данных для входа";
    String wrongData = "Учетная запись не найдена";



    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";


        RandomString loginPassword = new RandomString();
        profile = new Profile(loginPassword.getRandomString(10),loginPassword.getRandomString(10) ,loginPassword.getRandomString(5));
        Steps.postRequest(profile);

        deletedProfile =new Profile(loginPassword.getRandomString(10),loginPassword.getRandomString(10) ,loginPassword.getRandomString(5));
        Steps.postRequest(deletedProfile);

        int id = given()
                .header("Content-type", "application/json")
                .and()
                .body(deletedProfile)
                .when()
                .post("/api/v1/courier/login").then().extract().path("id");

        given()
                .delete(String.format("/api/v1/courier/%s", id))
                .then().assertThat().statusCode(200);
    }



    @Test
    public void loginTest(){
        loginData = new LoginToProfile(profile.getLogin(), profile.getPassword());
        Response response = Steps.sendPostLoginRequest(loginData);
        Steps.checkSuccessBody(response, "id", 200);
    }

    @Test
    public void noLogin(){
        loginData = new LoginToProfile(null, profile.getPassword());
        Response response = Steps.sendPostLoginRequest(loginData);
        Steps.compareBodyNegative(response, noFieldAnswer, 400);
    }

    @Test
    public void noPassword(){
        loginData = new LoginToProfile(profile.getLogin(), "");
        Response response = Steps.sendPostLoginRequest(loginData);
        Steps.compareBodyNegative(response, noFieldAnswer, 400);
    }

    @Test
    public void wrongLogin(){
        loginData = new LoginToProfile(profile.getLogin().toUpperCase() + new Random().nextInt(100), profile.getPassword());
        Response response = Steps.sendPostLoginRequest(loginData);
        Steps.compareBodyNegative(response, wrongData, 404);
    }

    @Test
    public void wrongPassword(){
        loginData = new LoginToProfile(profile.getLogin(), profile.getPassword() + new Random().nextInt(100));
        Response response = Steps.sendPostLoginRequest(loginData);
        Steps.compareBodyNegative(response, wrongData, 404);
    }

    @Test
    public void deletedClient(){
        loginData = new LoginToProfile(deletedProfile.getLogin(), deletedProfile.getPassword());
        Response response = Steps.sendPostLoginRequest(loginData);
        Steps.compareBodyNegative(response, wrongData, 404);
    }

    @After
    public void tearDown(){
        int id = given()
                .header("Content-type", "application/json")
                .and()
                .body(profile)
                .when()
                .post("/api/v1/courier/login").then().extract().path("id");

        given()
                .delete(String.format("/api/v1/courier/%s", id))
                .then().assertThat().statusCode(200);
    }





}
