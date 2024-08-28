package Authentication;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.Repository;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private Repository repo;
    private Context context;

    private static final String PREFS_NAME = "LoginData";

    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.repo = new Repository(context);
    }

    @Override
    public void handleLoginButtonClick(String email, String password) {
        if (!email.equals("N/A") && !password.equals("N/A")) {
            repo.login(email, password, new Repository.fireCallback() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    SharedPreferences storage = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = storage.edit();
                    editor.putString("Email", email);
                    editor.putString("Password", password);
                    editor.apply();

                    String username = extractTextBeforeNumber(email);
                    view.showLoginSuccess(username);
                }

                @Override
                public void onFailure(String message) {
                    view.showLoginFailure(message);
                }
            });
        } else {
            view.showLoginFailure("Invalid user!");
        }
    }

    @Override
    public void handleRegisterButtonClick() {
        view.navigateToRegister();
    }

    public static String extractTextBeforeNumber(String email) {
        String pattern = "^[a-zA-Z]+";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(email);
        if (m.find()) {
            return m.group(0);
        } else {
            return "";
        }
    }
}

