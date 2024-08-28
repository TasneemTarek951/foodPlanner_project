package db;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.widget.Toast;
import android.os.Handler;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.LiveData;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Home.Meal;
import MyPlan.MealPlan;
import MyPlan.PlanDAO;
import MyPlan.PlanDatabase;

public class Repository {
    private Context context;
    private MealDAO dao;
    private PlanDAO planDAO;
    private LiveData<List<Meal>> storedmeals;
    private LiveData<List<MealPlan>> mealsplan;

    List<Meal> meals;
    List<MealPlan> mealPlanList;

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private static final String PREFS_NAME = "LoginData";
    public static String email;

    private FirebaseFirestore firestore;


    private FirebaseUser user;


    public Repository(Context con){
        context = con;
        AppDataBase db = AppDataBase.getinstance(context.getApplicationContext());
        dao = db.getMealDAO();
        storedmeals = dao.getAllmeals();

        PlanDatabase database = PlanDatabase.getinstance(context.getApplicationContext());
        planDAO = database.getplanDAO();
        mealsplan = planDAO.getAllmealsplan();

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(context, gso);


        if (user != null) {
            firestore.collection("users").document(user.getUid())
                    .collection("favoritemeals")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Meal> mealList = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Meal meal = document.toObject(Meal.class);
                                mealList.add(meal);
                            }
                            meals = mealList;
                        } else {
                            new Handler(Looper.getMainLooper()).post(() ->
                                    Toast.makeText(context, "Error getting meals: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });

            firestore.collection("users").document(user.getUid())
                    .collection("mealPlans")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<MealPlan> mealPlanList = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                MealPlan mealPlan = document.toObject(MealPlan.class);
                                mealPlanList.add(mealPlan);
                            }
                            this.mealPlanList = mealPlanList;
                        } else {
                            new Handler(Looper.getMainLooper()).post(() ->
                                    Toast.makeText(context, "Error getting meal plans: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    });
        }



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
                                            Map<String, Object> userData = new HashMap<>();
                                            userData.put("email", email);
                                            userData.put("password", password);

                                            saveUserData(user, userData, new SaveCallback() {
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





    public LiveData<List<Meal>> getStoredmeals(){
        return storedmeals;
    }

    public LiveData<List<MealPlan>> getMealsplan(){
        return mealsplan;
    }

    public void delete(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.DeleteMeal(meal);
            }
        }).start();
    }

    public void insert(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.InsertMeal(meal);
            }
        }).start();
    }

    public void deletefromplan(MealPlan mealPlan){
        new Thread(new Runnable() {
            @Override
            public void run() {
                planDAO.Deletemealplan(mealPlan);
            }
        }).start();
    }

    public void inserttoplan(MealPlan mealPlan){
        new Thread(new Runnable() {
            @Override
            public void run() {
                planDAO.Insertmealplan(mealPlan);
            }
        }).start();
    }

    public void saveUserData(FirebaseUser user, Map<String, Object> data, final SaveCallback callback) {
        firestore.collection("users").document(user.getUid())
                .set(data)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }



    public void addtofav(Meal meal){
        if (user != null) {
            firestore.runTransaction(transaction -> {
                        DocumentReference mealRef = firestore.collection("users").document(user.getUid())
                                .collection("favoritemeals").document(meal.getStrMeal());

                        Meal existingMeal = transaction.get(mealRef).toObject(Meal.class);

                        if (existingMeal == null) {
                            transaction.set(mealRef, meal);
                        }

                        return null;
                    }).addOnSuccessListener(aVoid -> new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(context, "Meal added to favorites and saved to Firestore!", Toast.LENGTH_SHORT).show()))
                    .addOnFailureListener(e -> new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(context, "Error saving to Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show()));
        }
    }

    public void addtoplan(MealPlan mealPlan){
        if (user != null) {
            firestore.collection("users").document(user.getUid())
                    .collection("mealPlans").document(mealPlan.getStrMeal())
                    .set(mealPlan)
                    .addOnSuccessListener(aVoid -> new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(context, "Plan saved successfully to Firestore!", Toast.LENGTH_SHORT).show()))
                    .addOnFailureListener(e -> new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(context, "Error saving to Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show()));

        }
    }

    public void deletfromfav(Meal meal){
        if (user != null) {
            DocumentReference mealRef = firestore.collection("users").document(user.getUid())
                    .collection("favoritemeals").document(meal.getStrMeal());

            mealRef.delete()
                    .addOnSuccessListener(aVoid -> new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(context, "Meal deleted successfully from Firestore!", Toast.LENGTH_SHORT).show()))
                    .addOnFailureListener(e -> new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(context, "Error deleting meal: " + e.getMessage(), Toast.LENGTH_SHORT).show()));
        }
    }

    public List<Meal> Showfavmeals(){
        return meals;
    }

    public List<MealPlan> Showplanmeals(){
        return mealPlanList;
    }

    public void deletfromplan(MealPlan mealPlan){
        if (user != null) {
            DocumentReference mealPlanRef = firestore.collection("users").document(user.getUid())
                    .collection("mealPlans").document(mealPlan.getStrMeal());

            mealPlanRef.delete()
                    .addOnSuccessListener(aVoid -> new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(context,"Meal plan deleted successfully from Firestore!" , Toast.LENGTH_SHORT).show()))
                    .addOnFailureListener(e -> new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(context, "Error deleting meal from plan: " + e.getMessage(), Toast.LENGTH_SHORT).show()));
        }
    }


}

