package Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ImageService {
    @GET("filter.php")
    //www.themealdb.com/api/json/v1/1/filter.php?a=Canadian
    // a is the parameter that will be sent
    //@Query refers to the query param that we will replace it
    public Call<ImageResponse> getImages(@Query("a") String endpoint);

    @GET("filter.php")
    public Call<ImageResponse> getCategory(@Query("c") String endpoint);

    @GET("filter.php")
    public Call<ImageResponse> getIngredient(@Query("i") String endpoint);



}
