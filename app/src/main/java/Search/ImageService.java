package Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImageService {
    @GET
    public Call<ImageResponse> getImages(String endpoint);
}
