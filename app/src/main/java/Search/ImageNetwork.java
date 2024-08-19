package Search;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageNetwork {
    public static final String base_url = "https://www.themealdb.com/api/json/v1/1/";
    ImageService imageService;
    String endpoint;

    public ImageNetwork(String end){
        endpoint = end;
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(base_url).build();
        imageService = retrofit.create(ImageService.class);
    }

    public void Makenetworkcallback(ImageNetworkCallback imageNetworkCallback){
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
}
