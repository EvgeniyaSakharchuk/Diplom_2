
import config.Config;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import user.UserRegister;
import user.UserAuth;
import user.RequestCreateUser;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;

public class UserAuthTest {
    UserAuth userAuth;
    UserRegister userRegister;
    @Before
    @Step("Создание Пользователя")
    public void setUp() {
        RestAssured.baseURI = Config.BASE_URL;
        userAuth = UserAuth.usersRandomCreate();
        userRegister = RequestCreateUser.createUser(userAuth).body().as(UserRegister.class);
    }
    @Test
    @DisplayName("Войти под пользователем с действительными данными авторизации")
    @Description("Проверка успешной авторизации под пользователем")
    public void userCanSuccessfullyLogin() {
        Response response = RequestCreateUser.authUser(userAuth);
        assertTrue("Успешная авторизация", response.path("success"));
    }
    @Test
    @DisplayName("Войти под пользователем не заполняя пароль")
    @Description("Проверка авторизации пользователя без заполнения пароля")
    public void userLoginUnsuccessWithoutPasswordField() {
        userAuth.setPassword("xzzaqwer");
        Response response = RequestCreateUser.authUser(userAuth);
        assertFalse("Авторизоваться не удалось", response.path("success"));
    }
    @Test
    @DisplayName("Войти под пользователем не заполняя логин")
    @Description("Проверка авторизации пользователя без заполнения логина")
    public void userLoginUnsuccessWithoutLoginField() {
        userAuth.setEmail(RandomStringUtils.randomAlphabetic(10) + "@yandex.ru");
        Response response = RequestCreateUser.authUser(userAuth);
        assertFalse("Авторизоваться не удалось", response.path("success"));
    }
    @After
    @Step("Удалить тестового пользователя")
    public void tearDown() {
        RequestCreateUser.deleteUser(userRegister.getAccessToken());
    }
}
