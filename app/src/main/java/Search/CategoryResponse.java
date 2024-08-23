package Search;

import java.util.List;

import db.Category;

public class CategoryResponse {
    private List<Category> categories;

    public List<Category> getMeals() {
        return categories;
    }

    public void setMeals(List<Category> categories) {
        this.categories = categories;
    }
}
