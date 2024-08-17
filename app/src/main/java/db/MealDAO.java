package db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Home.Meal;

@Dao
public interface MealDAO {
    @Query("SELECT * FROM Meal_table")
    LiveData<List<Meal>> getAllmeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void InsertMeal(Meal meal);

    @Delete
    void DeleteMeal(Meal meal);
}
