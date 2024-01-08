import ingredients.Ingredients;
import config.Config;
import order.Order;
import order.RequestOrder;
import org.junit.After;
import user.UserAuth;
import org.junit.Before;
import user.RequestCreateUser;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import static ingredients.RequestIngredients.getFirstIngredientOnTheList;



public class OrderTest {
    Ingredients actualIngredient;
    String authUserToken;

    @Before
    @Step("Создание Пользователя")
    public void setUp() {
        RestAssured.baseURI = Config.BASE_URL;
        authUserToken = RequestCreateUser.createUser(UserAuth.usersRandomCreate()).path("accessToken");
        actualIngredient = getFirstIngredientOnTheList();
    }
    @Test
    @DisplayName("Создание заказа авторизованным пользователем")
    @Description("Создание нового заказа 200 ОК")
    public void createNewOrderByAuth() {
        Order order = new Order(actualIngredient);
        Response response = RequestOrder.createOrder(order, authUserToken);
        assertTrue("Успешное Создание заказа", response.path("success"));
    }
    @Test
    @DisplayName("Создание заказа неавторизованным пользователем")
    @Description("Создание заказа неавторизованным пользователем  200 OK")
    public void createNewOrderWithoutAuth() {
        Order order = new Order(actualIngredient);
        Response response = given()
                .header("Content-Type", "application/json")
                .body(order)
                .post(Config.ORDERS_BURGER);
        assertTrue("Успешное Создание заказа", response.path("success"));
    }
    @Test
    @DisplayName("Создание заказа c авторизацией без ингредиентов")
    @Description("Создание заказа с авторизацией,без ингредиентов")
    public void createNewOrderAuthUserNoIngredients() {
        Order order = new Order();
        Response response = RequestOrder.createOrder(order, authUserToken);
        assertFalse("Заказ не создан", response.path("success"));
        assertEquals("Заказ не создан Bad Request 400", 400, response.statusCode());
    }
    @Test
    @DisplayName("Создание заказа неавторизованным пользователем без ингредиентов")
    @Description("Создание заказа без ингредиентов Bad Request 400")
    public void createNewOrderWithoutAuthUserIngredientsInvalidHash() {
        actualIngredient.set_id(actualIngredient.get_id().replace("a", "0"));
        Order order = new Order(actualIngredient);
        Response response = RequestOrder.createOrder(order, authUserToken);
        assertFalse("Заказ не создан", response.path("success"));
        assertEquals("заказ без ингредиентов Bad Request 400", 400, response.statusCode());
    }
    @Test
    @DisplayName("Создание заказа авторизованным пользователем с неправильными ингредиентами")
    @Description("Создание заказа Internal Error 500")
    public void createNewOrderAuthUserWithWrongIngredients() {
        actualIngredient.set_id("Неправильный ингредиент");
        Order order = new Order(actualIngredient);
        Response response = RequestOrder.createOrder(order, authUserToken);
        assertEquals("Internal Error 500", 500, response.statusCode());
    }
    @After
    @Step("Удалить тестового пользователя")
    public void tearDown() {
        RequestCreateUser.deleteUser(authUserToken);
    }
}

