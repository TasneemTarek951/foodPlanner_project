package Search;

import java.util.List;

public class ImageResponse {
    private List<Image> meals;

    public ImageResponse(List<Image> images){
        meals = images;
    }
    public List<Image> getImages(){
        return meals;
    }
    public void setImages(List<Image> images){
        meals = images;
    }
}
