package order;
import config.*;


import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class RequestOrder {
    public static Response getUserOrders(String authToken) {
        return given()
                .header("Authorization", authToken)
                .get(Config.ORDERS_BURGER);
    }

    public static Response createOrder(Order order, String authToken) {
        return given()
                .header("Authorization", authToken)
                .header("Content-Type", "application/json")
                .body(order)
                .post(Config.ORDERS_BURGER);
    }
}
