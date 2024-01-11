package ingredients;
import config.*;
import static io.restassured.RestAssured.given;


public class RequestIngredients {
    public static Ingredients[] getIngredientsArray() {
        return getIngredientResponse().getIngredients();
    }
    public static IngredientsDataList getIngredientResponse() {
        return given().get(Config.GET_INGREDIENTS).as(IngredientsDataList.class);
    }
    public static Ingredients getFirstIngredientOnTheList() {
        return getIngredientsArray()[0];
    }
}
