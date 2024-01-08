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

import static org.junit.Assert.*;

public class UserCreateTest {
    UserAuth userAuth;
    UserRegister respUser;

    @Before
    @Step("Создание Пользователя")
    public void setUp() {
        RestAssured.baseURI = Config.BASE_URL;
        userAuth = UserAuth.usersRandomCreate();
    }

    @Test
    @DisplayName("Создание нового пользователя")
    @Description("Тест на успешное создание пользователя 200 ОК")
    public void userCanBeCreatedWithSuccess() {
        Response response = createUser(userAuth);
        assertEquals("Код Статус 200 ОК", 200, response.statusCode());
        respUser = response.body().as(UserRegister.class);
        assertTrue("Код Статус 200 ОК", respUser.isSuccess());
    }

    @Test
    @DisplayName("Cоздание пользователя с существующими данными")
    @Description("Проверка создания нового пользователя с существующим логином")
    public void userCanNotBeCreatedWithTheSameData() {
        Response response = createUser(userAuth);
        assertEquals("Код Статус 200 ОК", 200, response.statusCode());
        respUser = response.body().as(UserRegister.class);
        response = createUser(userAuth);
        UserRegister failRespUser = response.body().as(UserRegister.class);
        assertEquals("Неверный код ответа", 403, response.statusCode());
        assertFalse("Такой пользователь уже существует", failRespUser.isSuccess());
    }
    @Test
    @DisplayName("Создать пользователя без ввода имени")
    @Description("Проверка создания нового пользователя без заполнения поля имя")
    public void userCanNotBeCreatedWithoutNameField() {
        userAuth.setName(null);
        creationUserFailedField();
    }
    @Test
    @DisplayName("Создать пользователя без ввода пароля")
    @Description("Проверка создания нового пользователя без заполнения поля пароль")
    public void userCanNotBeCreatedWithoutPasswordField() {
        userAuth.setPassword(null);
        creationUserFailedField();
    }
    @Test
    @DisplayName("Создать пользователя без поля электронной почты")
    @Description("Проверка создания нового пользователя без поля электронной почты")
    public void userCanNotBeCreatedWithoutEmailField() {
        userAuth.setEmail(null);
        creationUserFailedField();
    }
    public void creationUserFailedField() {
        Response response = createUser(userAuth);
        respUser = response.body().as(UserRegister.class);
        assertEquals("Неверный код ответа", 403, response.statusCode());
        assertFalse("Не заполнено обязательное поле", respUser.isSuccess());
    }
    @Step("Отправка данных для создания нового пользователя")
    public Response createUser(UserAuth userAuthData) {
        return RequestCreateUser.createUser(userAuthData);
    }
    @After
    @Step("Удалить тестового пользователя")
    public void tearDown() {
        if (respUser.getAccessToken() == null) {
            return;
        }
        RequestCreateUser.deleteUser(respUser.getAccessToken());
    }
}
