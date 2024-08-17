package Home;

import android.content.Context;

import java.util.List;

import db.Repository;

public class HomePresenterImp implements NetworkCallback,HomePresenter {
    private HomeView homeView;
    Network network;
    Repository repo;
    Context context;


    public HomePresenterImp(HomeView view,Context con){
        homeView = view;
        context = con;
        network = new Network();
        network.Makenetworkcallback(this);
        repo = new Repository(context);
    }

    @Override
    public void addToFavorite(Meal meal) {
          repo.insert(meal);
    }

    @Override
    public void onSuccessResult(List<Meal> meals) {
        homeView.ShowData(meals);
    }

    @Override
    public void onfailureResult(String errorMsg) {
        homeView.ShowErrorMsg(errorMsg);
    }
}
