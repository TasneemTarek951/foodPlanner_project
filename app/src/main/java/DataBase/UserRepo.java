package DataBase;

import android.content.Context;

import androidx.lifecycle.LiveData;

public class UserRepo {
    private Context context;
    private UserDAO dao;
    private LiveData<User> user;

    public UserRepo(Context con,String mail){
        context = con;
        AppDataBase db = AppDataBase.getinstance(context.getApplicationContext());
        dao = db.getUserDAO();
        user = dao.getUser(mail);

    }


    public LiveData<User> getUser(){
        return user;
    }

    public void insert(User user){
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insertUser(user);
            }
        }).start();
    }



}
