package Search;

import com.example.foodplanner_project.SearchResultFragment;

import java.util.List;

import db.Category;
import db.Country;
import db.Ingredient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageNetwork {
    public static final String base_url = "https://www.themealdb.com/api/json/v1/1/";
    ImageService imageService;
    String endpoint;
    List<Country> countries;
    List<Category> categories;
    List<Ingredient> ingredients;

    public ImageNetwork(){
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(base_url).build();
        imageService = retrofit.create(ImageService.class);
    }
    public ImageNetwork(String end){
        endpoint = end;
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(base_url).build();
        imageService = retrofit.create(ImageService.class);
    }

    public void Makenetworkcallback(ImageNetworkCallback imageNetworkCallback){
        if(SearchResultFragment.type.equals("Country")){
            imageService.getImages(endpoint).enqueue(new Callback<ImageResponse>() {

                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    if(response.isSuccessful()){
                        imageNetworkCallback.onSuccessResult(response.body().getImages());
                    }
                }

                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {
                    imageNetworkCallback.onfailureResult(t.getMessage());
                    t.printStackTrace();
                }
            });
        }
        if(SearchResultFragment.type.equals("Category")){
            imageService.getCategory(endpoint).enqueue(new Callback<ImageResponse>() {

                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    if(response.isSuccessful()){
                        imageNetworkCallback.onSuccessResult(response.body().getImages());
                    }
                }

                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {
                    imageNetworkCallback.onfailureResult(t.getMessage());
                    t.printStackTrace();
                }
            });
        }

        if(SearchResultFragment.type.equals("Ingredient")){
            imageService.getIngredient(endpoint).enqueue(new Callback<ImageResponse>() {

                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    if(response.isSuccessful()){
                        imageNetworkCallback.onSuccessResult(response.body().getImages());
                    }
                }

                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {
                    imageNetworkCallback.onfailureResult(t.getMessage());
                    t.printStackTrace();
                }
            });
        }
    }

    public void Countrynetworkcallback(ListCallback<Country> callback) {
        imageService.getcountries().enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getMeals());
                } else {
                    callback.onFailure(new Exception("Response not successful"));
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void Categorynetworkcallback(ListCallback<Category> callback) {
        imageService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getMeals());
                } else {
                    callback.onFailure(new Exception("Response not successful"));
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void Ingredientnetworkcallback(ListCallback<Ingredient> callback) {
        imageService.getingredients().enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getMeals());
                } else {
                    callback.onFailure(new Exception("Response not successful"));
                }
            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }



}
