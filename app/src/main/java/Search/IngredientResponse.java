package Search;

import java.util.List;

import db.Ingredient;

public class IngredientResponse {
    private List<Ingredient> meals;

    public List<Ingredient> getMeals() {
        return meals;
    }

    public void setMeals(List<Ingredient> meals) {
        this.meals = meals;
    }
}
