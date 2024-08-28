package Search;

import android.content.Context;

import java.util.List;

import Home.HomePresenter;
import Home.HomeView;
import Home.Meal;
import Home.Network;
import Home.NetworkCallback;
import MyPlan.MealPlan;
import db.Repository;

public class MealDetailsPresenterImp implements NetworkCallback, HomePresenter {
    private HomeView homeView;
    Network network;
    Repository repo;
    Context context;

    public MealDetailsPresenterImp(HomeView view,Context con){
        homeView = view;
        context = con;
        network = new Network();
        network.Makenetworkcallback_2(this);
        repo = new Repository(context);
    }
    @Override
    public void addToFavorite(Meal meal) {
        repo.insert(meal);
        repo.addtofav(meal);
    }

    @Override
    public void addToPlan(MealPlan mealPlan) {
        repo.inserttoplan(mealPlan);
        repo.addtoplan(mealPlan);
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
