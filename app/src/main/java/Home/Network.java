package Home;

import com.example.foodplanner_project.MealDetailsFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    public static final String base_url = "https://www.themealdb.com/api/json/v1/1/";
    MealSevice mealSevice;

    public Network(){
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(base_url).build();
        mealSevice = retrofit.create(MealSevice.class);
    }

    public void Makenetworkcallback(NetworkCallback networkCallback){
        mealSevice.getmeals().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful()){
                    networkCallback.onSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkCallback.onfailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void Makenetworkcallback_2(NetworkCallback networkCallback){
        mealSevice.getmealdetail(MealDetailsFragment.mealname).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful()){
                    networkCallback.onSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                networkCallback.onfailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

}
