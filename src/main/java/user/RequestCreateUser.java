package user;
import config.Config;
import io.restassured.response.Response;


import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
public class RequestCreateUser {
    public static Response createUser(UserAuth userAuth) {
        return given()
                .header("Content-Type", "application/json")
                .body(userAuth)
                .post(Config.CREATE_NEW_USER);
    }

    public static Response authUser(UserAuth userAuth) {
        return given()
                .header("Content-Type", "application/json")
                .body(userAuth)
                .post(Config.LOGIN_USER);
    }

    public static Response updateUserData(UserAuth userAuth, String authToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", authToken)
                .body(userAuth)
                .patch(Config.USER_AUTH);
    }

    public static Response deleteUser(String authToken) {
        String AuthRoute = Config.USER_AUTH;
        return given()
                .header("Authorization", authToken)
                .delete(AuthRoute);
    }
}
