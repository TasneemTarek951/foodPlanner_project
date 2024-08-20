package db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import Home.Meal;
import MyPlan.MealPlan;
import MyPlan.PlanDAO;
import MyPlan.PlanDatabase;

public class Repository {
    private Context context;
    private MealDAO dao;
    private PlanDAO planDAO;
    private LiveData<List<Meal>> storedmeals;
    private LiveData<List<MealPlan>> mealsplan;

    public Repository(Context con){
        context = con;
        AppDataBase db = AppDataBase.getinstance(context.getApplicationContext());
        dao = db.getMealDAO();
        storedmeals = dao.getAllmeals();

        PlanDatabase database = PlanDatabase.getinstance(context.getApplicationContext());
        planDAO = database.getplanDAO();
        mealsplan = planDAO.getAllmealsplan();
    }

    public LiveData<List<Meal>> getStoredmeals(){
        return storedmeals;
    }

    public LiveData<List<MealPlan>> getMealsplan(){
        return mealsplan;
    }

    public void delete(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.DeleteMeal(meal);
            }
        }).start();
    }

    public void insert(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.InsertMeal(meal);
            }
        }).start();
    }

    public void deletefromplan(MealPlan mealPlan){
        new Thread(new Runnable() {
            @Override
            public void run() {
                planDAO.Deletemealplan(mealPlan);
            }
        }).start();
    }

    public void inserttoplan(MealPlan mealPlan){
        new Thread(new Runnable() {
            @Override
            public void run() {
                planDAO.Insertmealplan(mealPlan);
            }
        }).start();
    }

}
