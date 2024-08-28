package Authentication;


import db.Repository;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.activity.result.ActivityResultLauncher;

import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationPresenter implements AuthenticationContract.Presenter {

    private AuthenticationContract.View view;
    private static final String PREFS_NAME = "LoginData";
    private Repository repo;
    private Context context;

    public AuthenticationPresenter(AuthenticationContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.repo = new Repository(context);
    }

    @Override
    public void handleRegisterButtonClick() {
        view.navigateToRegister();
    }

    @Override
    public void handleLoginButtonClick() {
        view.navigateToLogin();
    }

    @Override
    public void handleGuestButtonClick() {
        view.navigateAsGuest();
    }

    @Override
    public void handleGoogleLoginButtonClick(ActivityResultLauncher<Intent> googleSignInLauncher) {
        repo.signInWithGoogle(googleSignInLauncher);
    }

    @Override
    public void handleGoogleSignInResult(Intent data) {
        repo.handleGoogleSignInResult(data, new Repository.fireCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                SharedPreferences storage = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = storage.edit();
                editor.putString("Email", Repository.email);
                editor.apply();
                String username = extractTextBeforeNumber(Repository.email);
                view.showGoogleLoginSuccess(username);
            }

            @Override
            public void onFailure(String message) {
                view.showLoginFailure(message);
            }
        });
    }

    public static String extractTextBeforeNumber(String email) {
        // Regex pattern to match text before numbers
        String pattern = "^[a-zA-Z]+";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object
        Matcher m = r.matcher(email);
        if (m.find()) {
            return m.group(0);
        } else {
            return "";
        }
    }
}

