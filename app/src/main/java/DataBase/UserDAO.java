package DataBase;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM User_table WHERE email = :mail")
    LiveData<User> getUser(String mail);

    @Insert
    void insertUser(User user);

}
