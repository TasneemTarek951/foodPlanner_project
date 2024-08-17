package Home;

import java.util.List;

public interface NetworkCallback {
    public void onSuccessResult(List<Meal> meals);
    public void onfailureResult(String errorMsg);
}
