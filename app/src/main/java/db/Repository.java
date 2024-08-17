package db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import Home.Meal;

public class Repository {
    private Context context;
    private MealDAO dao;
    private LiveData<List<Meal>> storedmeals;

    public Repository(Context con){
        context = con;
        AppDataBase db = AppDataBase.getinstance(context.getApplicationContext());
        dao = db.getMealDAO();
        storedmeals = dao.getAllmeals();
    }

    public LiveData<List<Meal>> getStoredmeals(){
        return storedmeals;
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

}
