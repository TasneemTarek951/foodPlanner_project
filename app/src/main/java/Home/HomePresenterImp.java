package Home;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import MyPlan.MealPlan;
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
