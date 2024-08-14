package DataBase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;
    public abstract UserDAO getUserDAO();
    public static synchronized AppDataBase getinstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"Userdb").build();

        }
        return instance;
    }

}
