import config.*;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import order.*;
import org.junit.After;
import org.junit.Before;
import user.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;


public class OrderListTest {
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
    @DisplayName("Получение списка заказа авторизованным пользователем")
    @Description("Проверка на получение списка заказов с кодом 200")
    public void getUserOrdersUsersWithoutOrders() {
        Response response = RequestOrder.getUserOrders(userRegister.getAccessToken());
        OrderClient orderResponse = response.as(OrderClient.class);
        assertEquals("200 ОК",200, response.getStatusCode());
        assertNotNull("Получен список заказов", orderResponse.getOrders());
    }

    @Test
    @DisplayName("Получение списка заказа неавторизованным пользователем")
    @Description("Проверка получения ошибки списка заказов с кодом 401")
    public void getUserOrdersWithoutAuthUser() {
        Response response = RequestOrder.getUserOrders("");
        assertFalse("Список заказов не получен", response.path("success"));
        assertEquals("Ошибка авторизации. Код 401", 401, response.getStatusCode());
    }
    @After
    @Step("Удаление созданного пользователя")
    public void tearDown() {
        RequestCreateUser.deleteUser(userRegister.getAccessToken());
    }
}


