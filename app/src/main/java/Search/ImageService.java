package Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImageService {
    @GET("{endpoint}")
    public Call<ImageResponse> getImages(@Path("endpoint") String endpoint);
}
