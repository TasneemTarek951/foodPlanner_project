package Authentication;

public interface LoginContract {

    interface View {
        void showLoginSuccess(String username);
        void showLoginFailure(String message);
        void navigateToRegister();
    }

    interface Presenter {
        void handleLoginButtonClick(String email, String password);
        void handleRegisterButtonClick();
    }
}

