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

import java.util.ArrayList;
import java.util.List;

import Home.HomePresenter;
import Home.HomePresenterImp;
import Home.HomeView;
import Home.Meal;
import Home.onMealClickListener;
import db.HomeAdapter;

public class HomeFragment extends Fragment implements onMealClickListener, HomeView {
    RecyclerView allRecycler;
    HomeAdapter homeAdapter;
    HomePresenter homePresenter;
    LinearLayoutManager layout;

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

        homePresenter = new HomePresenterImp(this,getActivity());
        homeAdapter = new HomeAdapter(getActivity(),new ArrayList<>(),this);
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

    }
}
