package com.example.foodplanner_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Arrays;
import java.util.List;

import Search.SearchAdapter;
import Search.onItemclickListener;

public class SearchFragment extends Fragment implements onItemclickListener {
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    String str1;



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

        List<String> defaultItems = Arrays.asList("", "", "");
        searchAdapter = new SearchAdapter(defaultItems,this);
        recyclerView.setAdapter(searchAdapter);

        chipCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str1 = "filter.php?a=";
                List<String> iphoneItems = Arrays.asList("American", "British", "Canadian","Chinese","Croatian","Dutch","Egyptian","French","Greek","Indian");
                searchAdapter.updateList(iphoneItems);
            }
        });

        chipCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str1 = "filter.php?c=";
                List<String> iphoneItems = Arrays.asList("Beef", "Breakfast", "Chicken","Dessert","Goat","Lamb","Miscellaneous","Pasta","Seafood","Vegetarian");
                searchAdapter.updateList(iphoneItems);
            }
        });

        chipIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str1 = "filter.php?i=";
                List<String> iphoneItems = Arrays.asList("Chicken", "Salmon", "Beef","Avocado","Asparagus","Bacon","Bread","Brandy","Breadcrumbs","Butter");
                searchAdapter.updateList(iphoneItems);
            }
        });
    }

    @Override
    public String onitemlistener(String str) {
        String endpoint = str1 + str;
        return endpoint;
    }

}