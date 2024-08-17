package Home;

import java.util.List;

public interface HomeView {
    public void ShowData(List<Meal> meals);
    public void ShowErrorMsg(String errorMsg);
}
