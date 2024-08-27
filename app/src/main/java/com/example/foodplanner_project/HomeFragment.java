package com.example.foodplanner_project;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import Home.HomePresenter;
import Home.HomePresenterImp;
import Home.HomeView;
import Home.Meal;
import Home.onMealClickListener;
import MyPlan.MealPlan;
import db.HomeAdapter;

public class HomeFragment extends Fragment implements onMealClickListener, HomeView {
    RecyclerView allRecycler;
    HomeAdapter homeAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager layout;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allRecycler = view.findViewById(R.id.recyclerView);
        layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        homePresenter = new HomePresenterImp(this,getActivity());
        homeAdapter = new HomeAdapter(getActivity(),new ArrayList<>(),this,getViewLifecycleOwner().getLifecycle());
        allRecycler.setLayoutManager(layout);
        allRecycler.setAdapter(homeAdapter);
    }

    @Override
    public void ShowData(List<Meal> meals) {
        homeAdapter.SetList(meals);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowErrorMsg(String errorMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(errorMsg).setTitle("An error ocured");
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void OnmealclickListener(Meal meal) {
            Toast.makeText(getActivity(), "Adding item!", Toast.LENGTH_SHORT).show();
            homePresenter.addToFavorite(meal);


            if (user != null) {
                firestore.runTransaction(transaction -> {
                            DocumentReference mealRef = firestore.collection("users").document(user.getUid())
                                    .collection("favoritemeals").document(meal.getStrMeal());

                            Meal existingMeal = transaction.get(mealRef).toObject(Meal.class);

                            if (existingMeal == null) {
                                transaction.set(mealRef, meal);
                            }

                            return null;
                        }).addOnSuccessListener(aVoid -> getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Meal added to favorites and saved to Firestore!", Toast.LENGTH_SHORT).show()))
                        .addOnFailureListener(e -> getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Error saving to Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show()));
            }



    }

    @Override
    public void clickListener(MealPlan mealPlan) {
            Toast.makeText(getActivity(), "Adding item to your plan!", Toast.LENGTH_SHORT).show();

            homePresenter.addToPlan(mealPlan);

            if (user != null) {
                firestore.collection("users").document(user.getUid())
                        .collection("mealPlans").document(mealPlan.getStrMeal())
                        .set(mealPlan)
                        .addOnSuccessListener(aVoid -> getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Plan saved successfully to Firestore!", Toast.LENGTH_SHORT).show()))
                        .addOnFailureListener(e -> getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Error saving to Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show()));

            }
    }
}
