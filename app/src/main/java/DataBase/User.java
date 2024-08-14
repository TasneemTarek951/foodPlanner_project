package DataBase;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_table")
public class User {


    @PrimaryKey
    @NonNull
    private String email;

    private String password;

    public User(){}

    /*public User(String email,String password){
        this.email = email;
        this.password = password;
    }*/

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
