package com.example.foodplanner_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import MyPlan.MealPlan;
import MyPlan.PlanAdapter;
import MyPlan.PlanPresenterImp;
import MyPlan.PlanView;
import MyPlan.onPlanClickListener;


public class MyPlaneFragment extends Fragment implements onPlanClickListener, PlanView {

    RecyclerView recycl;
    LinearLayoutManager layout;
    PlanPresenterImp presenterImp;
    PlanAdapter adapter;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_plane, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycl = view.findViewById(R.id.recyclerView);
        layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        presenterImp = new PlanPresenterImp(getActivity(),this,this);
        adapter = new PlanAdapter(getActivity(),new ArrayList<MealPlan>(),this,getViewLifecycleOwner().getLifecycle());
        recycl.setLayoutManager(layout);
        recycl.setAdapter(adapter);


    }

    @Override
    public void ShowData(List<MealPlan> mealPlanList) {
        if(!MainActivity2.isConnected) {
            adapter.setList(mealPlanList);
            adapter.notifyDataSetChanged();
        }else{
            adapter.setList(presenterImp.Showplanmeals());
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onplanclicklistener(MealPlan mealPlan) {
        Toast.makeText(getActivity(), "deleted", Toast.LENGTH_SHORT).show();
        presenterImp.RemoveFromPlan(mealPlan);
    }
}