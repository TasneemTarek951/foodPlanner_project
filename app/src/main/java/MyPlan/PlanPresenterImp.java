package MyPlan;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import db.Repository;

public class PlanPresenterImp implements PlanPresenter{

    Context context;
    LifecycleOwner owner;
    Repository repo;
    PlanView planView;

    public PlanPresenterImp(Context con,LifecycleOwner own,PlanView view){
        context = con;
        owner = own;
        planView = view;
        repo = new Repository(context);

        Observer<List<MealPlan>> observer = new Observer<List<MealPlan>>() {
            @Override
            public void onChanged(List<MealPlan> mealPlanList) {
                view.ShowData(mealPlanList);
            }
        };
        LiveData<List<MealPlan>> observable = repo.getMealsplan();
        observable.observe(owner,observer);
    }

    public List<MealPlan> Showplanmeals(){
        return repo.mealPlanList;
    }
    @Override
    public void RemoveFromPlan(MealPlan mealPlan) {
        repo.deletefromplan(mealPlan);
        repo.deletfromplan(mealPlan);
    }
}
