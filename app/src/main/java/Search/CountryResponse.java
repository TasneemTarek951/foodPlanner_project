package Search;

import java.util.List;

import db.Country;

public class CountryResponse {
    private List<Country> meals;

    public List<Country> getMeals() {
        return meals;
    }

    public void setMeals(List<Country> meals) {
        this.meals = meals;
    }
}
