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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import Favofite.FavoriteAdapter;
import Favofite.FavoritePresenterImp;
import Favofite.FavoriteView;
import Favofite.onFavoriteClickListener;
import Home.Meal;


public class FavoriteFragment extends Fragment implements onFavoriteClickListener, FavoriteView {

    RecyclerView recycl;
    LinearLayoutManager layout;
    FavoriteAdapter favoriteAdapter;
    FavoritePresenterImp presenterImp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycl = view.findViewById(R.id.recyclerView);
        layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);


        presenterImp = new FavoritePresenterImp(getActivity(),this,this);

        favoriteAdapter = new FavoriteAdapter(getActivity(),new ArrayList<Meal>(),this,getViewLifecycleOwner().getLifecycle());
        recycl.setLayoutManager(layout);
        recycl.setAdapter(favoriteAdapter);



    }

    @Override
    public void ShowData(List<Meal> meals) {
        favoriteAdapter.setList(meals);
        favoriteAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnfavClickListener(Meal meal) {
        Toast.makeText(getActivity(), "deleted", Toast.LENGTH_SHORT).show();
        presenterImp.removefromFavorite(meal);



    }
}