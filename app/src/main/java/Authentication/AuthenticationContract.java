package Authentication;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;

public interface AuthenticationContract {
    interface View {
        void showGoogleLoginSuccess(String username);
        void showLoginFailure(String message);
        void navigateToRegister();
        void navigateToLogin();
        void navigateAsGuest();
    }

    interface Presenter {
        void handleRegisterButtonClick();
        void handleLoginButtonClick();
        void handleGuestButtonClick();
        void handleGoogleLoginButtonClick(ActivityResultLauncher<Intent> googleSignInLauncher);
        void handleGoogleSignInResult(Intent data);
    }
}
