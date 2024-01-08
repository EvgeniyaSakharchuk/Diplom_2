import config.Config;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.RequestCreateUser;
import user.UserAuth;
import user.UserRegister;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class UserChangeCredential {
    UserAuth userAuthData;
    UserRegister userUpdate;

    @Before
    @Step("Создание Пользователя")
    public void setUp() {
        RestAssured.baseURI = Config.BASE_URL;
        userAuthData = UserAuth.usersRandomCreate();
        userUpdate = RequestCreateUser.createUser(userAuthData).body().as(UserRegister.class);
    }

    @Test
    @DisplayName("Изменение почты и имени авторизованного пользователя")
    @Description("Проверка, что пользователь может успешно изменить свои данные для входа и авторизоваться")
    public void checkAuthNewUserData() {
        UserAuth newUserDataAuthorizationData = UserAuth.usersRandomCreate();
        newUserDataAuthorizationData.setPassword("qwerty991");
        Response response = RequestCreateUser.updateUserData(newUserDataAuthorizationData, userUpdate.getAccessToken());
        assertTrue("Ответ успешный со статутом 200 ОК", response.path("success"));
        assertTrue("С новыми данными можно залогиниться", RequestCreateUser.authUser(newUserDataAuthorizationData).path("success"));
    }

    @Test
    @DisplayName("Попытка изменения почты и имени неавторизованного пользователя")
    @Description("Проверить, что неавторизованный пользователь не может изменить свои данные")
    public void checkAuthUserCouldNotChangeOwnData() {
        UserAuth newUserDataAuthorizationData = UserAuth.usersRandomCreate();
        newUserDataAuthorizationData.setPassword("qwerty9911");
        Response response = given()
                .header("Content-Type", "application/json")
                .body(userAuthData).patch(Config.USER_AUTH);
        assertFalse("В ответе сообщение о неудачной авторизации", response.path("success"));
        assertEquals("Ошибка авторизации. Код 401", 401, response.statusCode());
    }

    @After
    @Step("Удалить тестового пользователя")
    public void tearDown() {
        RequestCreateUser.deleteUser(userUpdate.getAccessToken());
    }

}
