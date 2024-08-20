package db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Home.Meal;
import MyPlan.MealPlan;
import MyPlan.PlanDAO;


@Database(entities = {Meal.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;
    public abstract MealDAO getMealDAO();
    public static synchronized AppDataBase getinstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"Mealdb").build();

        }
        return instance;
    }
}
