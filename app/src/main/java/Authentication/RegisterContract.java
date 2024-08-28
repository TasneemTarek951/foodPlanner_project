package Authentication;

public interface RegisterContract {

    interface View {
        void showRegistrationSuccess(String username);
        void showRegistrationFailure(String message);
        void navigateToLogin();
        void showInvalidEmailError();
        void showInvalidPasswordError();
        void showPasswordMismatchError();
        void showEmptyFieldsError();
    }

    interface Presenter {
        void handleRegisterButtonClick(String email, String password, String confirmPassword);
        void handleLoginButtonClick();
    }
}

