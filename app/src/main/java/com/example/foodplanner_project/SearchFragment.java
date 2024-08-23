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

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Search.CategoryAdapter;
import Search.ImageNetwork;
import Search.ListCallback;
import Search.SearchAdapter;
import Search.ingredientAdapter;
import db.Category;
import db.Country;
import db.Ingredient;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    SearchAdapter searchAdapter;
    ingredientAdapter ingredientAdapter;
    CategoryAdapter categoryAdapter;
    public static String str1;
    List<Country> countries;
    List<Category> categories;
    List<Ingredient> ingredients;
    ImageNetwork imageNetwork;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChipGroup chipGroup = view.findViewById(R.id.chipGroup);
        Chip chipCountry = view.findViewById(R.id.chipCountry);
        Chip chipCategory = view.findViewById(R.id.chipCategory);
        Chip chipIngredient = view.findViewById(R.id.chipIngredient);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //searchAdapter = new SearchAdapter(defaultItems);
        //recyclerView.setAdapter(searchAdapter);
        imageNetwork = new ImageNetwork();
        imageNetwork.Countrynetworkcallback(new ListCallback<Country>() {
            @Override
            public void onSuccess(List<Country> list) {
                searchAdapter = new SearchAdapter(list);
            }

            @Override
            public void onFailure(Throwable t) {
                 t.printStackTrace();
            }
        });

        imageNetwork.Categorynetworkcallback(new ListCallback<Category>() {
            @Override
            public void onSuccess(List<Category> list) {
                categoryAdapter = new CategoryAdapter(list,getActivity());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });


        imageNetwork.Ingredientnetworkcallback(new ListCallback<Ingredient>() {
            @Override
            public void onSuccess(List<Ingredient> list) {
                ingredientAdapter = new ingredientAdapter(list,getActivity());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });





        chipCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str1 = "Country";
                recyclerView.setAdapter(searchAdapter);
            }
        });

        chipCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str1 = "Category";
                recyclerView.setAdapter(categoryAdapter);
            }
        });

        chipIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str1 = "Ingredient";
                recyclerView.setAdapter(ingredientAdapter);
            }
        });
    }


}