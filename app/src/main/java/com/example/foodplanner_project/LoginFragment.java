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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginFragment extends Fragment {

    Button log_btn;
    Button regist;
    EditText passtext;
    EditText mailtext;
    String email;
    String Passsword;

    String Username;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log_btn = view.findViewById(R.id.Log_btn);
        regist = view.findViewById(R.id.register_btn);
        mailtext = view.findViewById(R.id.email_text);
        passtext = view.findViewById(R.id.password_text);

        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mailtext.getText().toString();
                Passsword = passtext.getText().toString();
                Username = extractTextBeforeNumber(email);

                SharedPreferences storage = requireContext().getSharedPreferences(RegisterFragment.str, Context.MODE_PRIVATE);
                String m = storage.getString(RegisterFragment.mail,"N/A");
                String p = storage.getString(RegisterFragment.pass,"N/A");
                if(!(m.equals("N/A") && p.equals("N/A"))){
                    if(m.equals(email) && p.equals(Passsword)){
                        Toast.makeText(getActivity(), "success login!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity2.class);
                        intent.putExtra(MainActivity.username,Username);
                        intent.putExtra(MainActivity.type,"Login");
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(), "invalid user!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "invalid user!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
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