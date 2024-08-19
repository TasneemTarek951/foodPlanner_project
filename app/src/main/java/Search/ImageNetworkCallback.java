package Search;

import java.util.List;

public interface ImageNetworkCallback {
    public void onSuccessResult(List<Image> images);
    public void onfailureResult(String errorMsg);
}
