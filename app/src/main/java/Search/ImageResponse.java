package Search;

import java.util.List;

public class ImageResponse {
    private List<Image> imageList;

    public ImageResponse(List<Image> images){
        imageList = images;
    }
    public List<Image> getImages(){
        return imageList;
    }
    public void setImages(List<Image> images){
        imageList = images;
    }
}
