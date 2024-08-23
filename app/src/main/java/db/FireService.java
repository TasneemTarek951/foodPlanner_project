package db;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.activity.result.ActivityResultLauncher;

import com.example.foodplanner_project.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FireService {

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private static final String PREFS_NAME = "LoginData";

    public static String email;

    public FireService(Activity activity){
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public interface fireCallback{
        void onSuccess(FirebaseUser user);
        void onFailure(String message);
    }

    public void login(String email, String password, final fireCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void signUp(String email, String password, final fireCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            user.updateProfile(new UserProfileChangeRequest.Builder()
                                            .build())
                                    .addOnCompleteListener(profileTask -> {
                                        if (profileTask.isSuccessful()) {
                                            DataService dataService = new DataService(user.getUid());
                                            Map<String, Object> userData = new HashMap<>();
                                            userData.put("email", email);
                                            userData.put("password", password);

                                            dataService.saveUserData(user, userData, new DataService.SaveCallback() {
                                                @Override
                                                public void onSuccess() {
                                                    callback.onSuccess(user);
                                                }

                                                @Override
                                                public void onFailure(String message) {
                                                    callback.onFailure(message);
                                                }
                                            });
                                        } else {
                                            callback.onFailure("Profile update failed");
                                        }
                                    });
                        } else {
                            callback.onFailure("User creation failed");
                        }
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void signInWithGoogle(ActivityResultLauncher<Intent> launcher) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        //activity.startActivityForResult(signInIntent, RC_SIGN_IN);
        launcher.launch(signInIntent);
    }

    public void handleGoogleSignInResult(Intent data, final fireCallback callback) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null) {
                email = account.getEmail();

                firebaseAuthWithGoogle(account, callback);
            }
        } catch (ApiException e) {
            callback.onFailure(e.getMessage());
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct, final fireCallback callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            saveUserToFirestore(user, email);
                        }
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    private void saveUserToFirestore(FirebaseUser user, String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);

        db.collection("users").document(user.getUid())
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("User data saved successfully.");
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error saving user data: " + e.getMessage());
                });
    }

    public void signOut(Activity activity, final fireCallback callback) {
        firebaseAuth.signOut();

        googleSignInClient.signOut().addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                SharedPreferences sharedPreferences = activity.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                callback.onSuccess(null);
            } else {
                callback.onFailure("Sign out failed");
            }
        });
    }
}
