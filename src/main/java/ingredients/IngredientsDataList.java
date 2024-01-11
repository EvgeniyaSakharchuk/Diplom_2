package ingredients;

public class IngredientsDataList {
    private boolean success;
    private Ingredients[] data;

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public Ingredients[] getIngredients() {
        return data;
    }
    public void setIngredients(Ingredients[] ingredients) {
        this.data = ingredients;
    }
}
