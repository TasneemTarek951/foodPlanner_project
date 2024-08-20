package Home;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealSevice {

    @GET("search.php?f=a")
    public Call<MealResponse> getmeals();

    @GET("search.php")
    public Call<MealResponse> getmealdetail(@Query("s") String mealname);
}
