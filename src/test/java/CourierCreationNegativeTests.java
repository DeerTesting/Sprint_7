import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Profile;
import org.example.RandomString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CourierCreationNegativeTests {
    private static RandomString loginPassword = new RandomString();
    Profile profile;
    private String login;
    private String password;

    private String name;
    private String bodyAnswer;
    private int code;

    public CourierCreationNegativeTests(String login, String password, String name, String bodyAnswer, int code) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.bodyAnswer = bodyAnswer;
        this.code = code;
    }

    @Parameterized.Parameters
    public static Object[][] getCourierData() {
        return new Object[][]{
                {"", loginPassword.getRandomString(10), loginPassword.getRandomString(10), "Недостаточно данных для создания учетной записи", 400},
                {null, loginPassword.getRandomString(10), loginPassword.getRandomString(10), "Недостаточно данных для создания учетной записи", 400},
                {loginPassword.getRandomString(15), "", loginPassword.getRandomString(10), "Недостаточно данных для создания учетной записи", 400},
                {loginPassword.getRandomString(15), null, loginPassword.getRandomString(10), "Недостаточно данных для создания учетной записи", 400}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";


    }

    @Test
    public void UnsuccessfulCourierCreation() {
        profile = new Profile(login, password, name);

        Response response = Steps.postRequest(profile);
        Steps.compareBodyNegative(response, bodyAnswer, code);
    }

}
