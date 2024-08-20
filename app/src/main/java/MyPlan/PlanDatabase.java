package MyPlan;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MealPlan.class},version = 1,exportSchema = false)
public abstract class PlanDatabase extends RoomDatabase {
    private static PlanDatabase instance = null;
    public abstract PlanDAO getplanDAO();
    public static synchronized PlanDatabase getinstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),PlanDatabase.class,"plandb").build();
        }
        return instance;
    }
}
