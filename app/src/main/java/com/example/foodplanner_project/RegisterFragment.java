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

import db.FireService;


public class RegisterFragment extends Fragment {

    Button log_btn;
    Button regist;
    EditText mailtext;
    EditText passtext;
    EditText confirmtext;
    String email;
    String Passsword;
    String Confirm;
    private static final String PREFS_NAME = "LoginData";
    private FireService fireService;

    String Username;



    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        fireService = new FireService(getActivity());



        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mailtext.getText().toString();
                Passsword = passtext.getText().toString();
                Confirm = confirmtext.getText().toString();

                Username = extractTextBeforeNumber(email);


                if(!(email.equals("") && Passsword.equals("") && Confirm.equals(""))){
                    if(isValidEmail(email)){
                        if(Passsword.equals(Confirm)){
                            if(validation(Passsword)){
                                fireService.signUp(email, Passsword, new FireService.fireCallback() {
                                    @Override
                                    public void onSuccess(FirebaseUser user) {
                                        SharedPreferences storage = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = storage.edit();
                                        editor.putString("Email", email);
                                        editor.putString("Password", Passsword);
                                        editor.apply();
                                        Toast.makeText(getActivity(), "Saved successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), MainActivity2.class);
                                        intent.putExtra(MainActivity.username,Username);
                                        intent.putExtra(MainActivity.type,"Register");
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onFailure(String message) {
                                        Toast.makeText(getActivity(),"Sign Up Failed: " + message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Toast.makeText(getActivity(), "invalid password!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getActivity(), "password and confirm password must be the same!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "invalid email!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "please enter your mail & password!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
    }

    public static boolean isValidEmail(String email) {
        // Define the regular expression for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Compile the regex
        Pattern pattern = Pattern.compile(emailRegex);

        // Match the email with the regex
        Matcher matcher = pattern.matcher(email);

        // Return true if the email matches the regex, false otherwise
        return matcher.matches();
    }

    private boolean validation(String pass) {
        // Check if the name contains both letters and numbers
        String complexPasswordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return pass.matches(complexPasswordPattern);

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