package Authentication;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplanner_project.RegisterFragment;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.Repository;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private Repository repo;
    private Context context;

    private static final String PREFS_NAME = "LoginData";

    public RegisterPresenter(RegisterContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.repo = new Repository(context);
    }

    @Override
    public void handleRegisterButtonClick(String email, String password, String confirmPassword) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            view.showEmptyFieldsError();
            return;
        }

        if (!isValidEmail(email)) {
            view.showInvalidEmailError();
            return;
        }

        if (!password.equals(confirmPassword)) {
            view.showPasswordMismatchError();
            return;
        }

        if (!validation(password)) {
            view.showInvalidPasswordError();
            return;
        }

        repo.signUp(email, password, new Repository.fireCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                SharedPreferences storage = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = storage.edit();
                editor.putString("Email", email);
                editor.putString("Password", password);
                editor.apply();

                String username = RegisterFragment.extractTextBeforeNumber(email);
                view.showRegistrationSuccess(username);
            }

            @Override
            public void onFailure(String message) {
                view.showRegistrationFailure(message);
            }
        });
    }

    @Override
    public void handleLoginButtonClick() {
        view.navigateToLogin();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validation(String pass) {
        String complexPasswordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return pass.matches(complexPasswordPattern);
    }
}

