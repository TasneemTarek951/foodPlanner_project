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

import db.FireService;


public class AuthenticationFragment extends Fragment {


    Button regist;
    Button log;
    Button guest;
    Button log_G;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    private static final String PREFS_NAME = "LoginData";

    private static final int RC_SIGN_IN = 1;

    private FireService fireService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authentication, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        regist = view.findViewById(R.id.Register_btn);
        log = view.findViewById(R.id.Login_btn);
        guest = view.findViewById(R.id.Guest_btn);
        log_G = view.findViewById(R.id.Log_G_btn);

        fireService = new FireService(getActivity());




        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        fireService.handleGoogleSignInResult(data, new FireService.fireCallback() {
                            @Override
                            public void onSuccess(FirebaseUser user) {
                                Toast.makeText(getActivity(), "Google Login Successful", Toast.LENGTH_SHORT).show();

                                SharedPreferences storage = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = storage.edit();
                                editor.putString("Email", FireService.email);
                                editor.apply();

                                Intent intent = new Intent(getActivity(), MainActivity2.class);
                                intent.putExtra(MainActivity.username, extractTextBeforeNumber(FireService.email));
                                intent.putExtra(MainActivity.type, "google signin");
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(String message) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        );






        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_authenticationFragment_to_registerFragment);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_authenticationFragment_to_loginFragment);
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                intent.putExtra(MainActivity.username, "Guest");
                intent.putExtra(MainActivity.type, "Guest");
                startActivity(intent);
            }
        });


        log_G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireService.signInWithGoogle(googleSignInLauncher);
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