package com.example.foodplanner_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Authentication.RegisterContract;
import Authentication.RegisterPresenter;
import db.Repository;


public class RegisterFragment extends Fragment implements RegisterContract.View {

    Button log_btn, regist;
    EditText mailtext, passtext, confirmtext;
    private RegisterContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegisterPresenter(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log_btn = view.findViewById(R.id.login_btn);
        mailtext = view.findViewById(R.id.mail_text);
        passtext = view.findViewById(R.id.pass_text);
        confirmtext = view.findViewById(R.id.confirm_text);
        regist = view.findViewById(R.id.regist_btn);

        regist.setOnClickListener(v -> {
            String email = mailtext.getText().toString();
            String password = passtext.getText().toString();
            String confirmPassword = confirmtext.getText().toString();
            presenter.handleRegisterButtonClick(email, password, confirmPassword);
        });

        log_btn.setOnClickListener(v -> presenter.handleLoginButtonClick());
    }

    @Override
    public void showRegistrationSuccess(String username) {
        Toast.makeText(getActivity(), "Saved successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity2.class);
        intent.putExtra(MainActivity.username, username);
        intent.putExtra(MainActivity.type, "Register");
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showRegistrationFailure(String message) {
        Toast.makeText(getActivity(), "Sign Up Failed: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin() {
        Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_loginFragment);
    }

    @Override
    public void showInvalidEmailError() {
        Toast.makeText(getActivity(), "Invalid email!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvalidPasswordError() {
        Toast.makeText(getActivity(), "Invalid password!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPasswordMismatchError() {
        Toast.makeText(getActivity(), "Password and confirm password must be the same!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyFieldsError() {
        Toast.makeText(getActivity(), "Please enter your mail & password!", Toast.LENGTH_SHORT).show();
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
