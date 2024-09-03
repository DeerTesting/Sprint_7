import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.LoginToProfile;
import org.example.Profile;
import org.example.RandomString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class DeleteCourierTest {

    Profile profile;
    Profile deletedProfile;
    LoginToProfile loginToProfile;
    LoginToProfile deletedLoginToProfile;
    int id;

    int deletedId;

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        RandomString loginPassword = new RandomString();
        profile = new Profile(loginPassword.getRandomString(10),loginPassword.getRandomString(10) ,loginPassword.getRandomString(5));
        Steps.postRequest(profile);
        loginToProfile = new LoginToProfile(profile.getLogin(), profile.getPassword());
        id = Steps.sendPostLoginRequest(loginToProfile).then().extract().path("id");

        deletedProfile = new Profile(loginPassword.getRandomString(10),loginPassword.getRandomString(10) ,loginPassword.getRandomString(5));
        Steps.postRequest(deletedProfile);
        deletedLoginToProfile = new LoginToProfile(deletedProfile.getLogin(), deletedProfile.getPassword());
        deletedId = Steps.sendPostLoginRequest(deletedLoginToProfile).then().extract().path("id");
        given()
                .delete(String.format("/api/v1/courier/%s", deletedId));

    }

    @Test
    public void successDelete(){
        Response response = Steps.successDelete(id);
        Steps.checkSuccessDeletion(response);
    }

    @Test
    public void noIdDelete(){
        Response response = Steps.unSuccessDelete();
        Steps.checkUnsuccessDeletion(response, "Недостаточно данных для удаления курьера");
    }

    @Test
    public void wrongIdDelete(){
        Response response = Steps.successDelete(deletedId);
        Steps.compareBodyNegative(response, "Курьера с таким id нет.", 404);
    }

    @After
    public void tearDown(){
        given()
                .delete(String.format("/api/v1/courier/%s", id));
    }
}
