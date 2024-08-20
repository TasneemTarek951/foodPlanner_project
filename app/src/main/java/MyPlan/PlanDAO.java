package MyPlan;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlanDAO {
    @Query("SELECT * FROM plan_table")
    LiveData<List<MealPlan>> getAllmealsplan();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void Insertmealplan(MealPlan mealPlan);


    @Delete
    void Deletemealplan(MealPlan mealPlan);
}
