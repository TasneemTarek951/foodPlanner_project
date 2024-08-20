package com.example.foodplanner_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Search.Image;
import Search.SearchPresenterImp;
import Search.SearchResultAdapter;
import Search.SearchView;

public class SearchResultFragment extends Fragment implements SearchView {
    String endpoint;
    public static String type;
    RecyclerView allRecycler;
    SearchResultAdapter searchResultAdapter;
    SearchPresenterImp presenterImp;
    LinearLayoutManager layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        endpoint = SearchResultFragmentArgs.fromBundle(getArguments()).getEndpoint();
        type = SearchResultFragmentArgs.fromBundle(getArguments()).getType();

        allRecycler = view.findViewById(R.id.recyclerView);
        layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.VERTICAL);

        presenterImp = new SearchPresenterImp(this,getActivity(),endpoint);
        searchResultAdapter = new SearchResultAdapter(getActivity(),new ArrayList<>());
        allRecycler.setLayoutManager(layout);
        allRecycler.setAdapter(searchResultAdapter);

    }

    @Override
    public void ShowData(List<Image> images) {
        searchResultAdapter.SetList(images);
        searchResultAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowErrorMsg(String errorMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(errorMsg).setTitle("An error ocured");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}