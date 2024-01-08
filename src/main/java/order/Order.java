package order;
import ingredients.*;

public class Order {
    private String[] ingredients;
    private String _id;
    public Order() {
    }

    public Order(Ingredients ingredient) {
        this.ingredients = new String[]{ingredient.get_id()};
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
