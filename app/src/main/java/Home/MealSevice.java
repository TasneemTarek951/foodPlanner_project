package Home;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealSevice {

    @GET("search.php?f=a")
    public Call<MealResponse> getmeals();
}
