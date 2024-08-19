package Search;

import android.content.Context;

import androidx.navigation.Navigation;

import com.example.foodplanner_project.SearchFragmentDirections;
import com.example.foodplanner_project.SearchResultFragmentDirections;

import java.util.List;

public class SearchPresenterImp implements ImageNetworkCallback{
    private SearchView searchView;
    ImageNetwork network;
    Context context;
    String Mealname;

    public SearchPresenterImp(SearchView view,Context con,String name){
        searchView = view;
        context = con;
        Mealname = name;
        network = new ImageNetwork(Mealname);

    }
    @Override
    public void onSuccessResult(List<Image> images) {
        searchView.ShowData(images);
    }

    @Override
    public void onfailureResult(String errorMsg) {
        searchView.ShowErrorMsg(errorMsg);
    }


}
