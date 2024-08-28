package com.example.foodplanner_project;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import Authentication.LoginContract;
import Authentication.LoginPresenter;

public class LoginFragment extends Fragment implements LoginContract.View {

    Button log_btn, regist;
    EditText passtext, mailtext;
    private LoginContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log_btn = view.findViewById(R.id.Log_btn);
        regist = view.findViewById(R.id.register_btn);
        mailtext = view.findViewById(R.id.email_text);
        passtext = view.findViewById(R.id.password_text);

        log_btn.setOnClickListener(v -> {
            String email = mailtext.getText().toString();
            String password = passtext.getText().toString();
            presenter.handleLoginButtonClick(email, password);
        });

        regist.setOnClickListener(v -> presenter.handleRegisterButtonClick());
    }

    @Override
    public void showLoginSuccess(String username) {
        Toast.makeText(getActivity(), "Successful login!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity2.class);
        intent.putExtra(MainActivity.username, username);
        intent.putExtra(MainActivity.type, "Login");
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showLoginFailure(String message) {
        Toast.makeText(getActivity(), "Sign in Failed: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToRegister() {
        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registerFragment);
    }

}
