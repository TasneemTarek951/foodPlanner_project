package com.example.foodplanner_project;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Authentication.AuthenticationContract;
import Authentication.AuthenticationPresenter;
import db.Repository;


public class AuthenticationFragment extends Fragment implements AuthenticationContract.View {

    Button regist, log, guest, log_G;
    private ActivityResultLauncher<Intent> googleSignInLauncher;
    private static final String PREFS_NAME = "LoginData";
    private AuthenticationContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AuthenticationPresenter(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authentication, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        regist = view.findViewById(R.id.Register_btn);
        log = view.findViewById(R.id.Login_btn);
        guest = view.findViewById(R.id.Guest_btn);
        log_G = view.findViewById(R.id.Log_G_btn);

        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        presenter.handleGoogleSignInResult(result.getData());
                    }
                }
        );

        regist.setOnClickListener(v -> presenter.handleRegisterButtonClick());
        log.setOnClickListener(v -> presenter.handleLoginButtonClick());
        guest.setOnClickListener(v -> presenter.handleGuestButtonClick());
        log_G.setOnClickListener(v -> presenter.handleGoogleLoginButtonClick(googleSignInLauncher));
    }

    @Override
    public void showGoogleLoginSuccess(String username) {
        Toast.makeText(getActivity(), "Google Login Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity2.class);
        intent.putExtra(MainActivity.username, username);
        intent.putExtra(MainActivity.type, "google signin");
        startActivity(intent);
    }

    @Override
    public void showLoginFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToRegister() {
        Navigation.findNavController(getView()).navigate(R.id.action_authenticationFragment_to_registerFragment);
    }

    @Override
    public void navigateToLogin() {
        Navigation.findNavController(getView()).navigate(R.id.action_authenticationFragment_to_loginFragment);
    }

    @Override
    public void navigateAsGuest() {
        Intent intent = new Intent(getActivity(), MainActivity2.class);
        intent.putExtra(MainActivity.username, "Guest");
        intent.putExtra(MainActivity.type, "Guest");
        startActivity(intent);
    }
}
