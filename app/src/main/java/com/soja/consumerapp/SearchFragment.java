package com.soja.consumerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    RecyclerView foodItemList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Add menu provider for handling options menu in fragment
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear(); // Remove previous menu items
                menuInflater.inflate(R.menu.search_menu, menu);

                MenuItem searchItem = menu.findItem(R.id.action_search);
                SearchView searchView = (SearchView) searchItem.getActionView();

                searchView.setQueryHint("Search for Fruits, Vegetables...");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        // Handle search submit (e.g., filter list)
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        // Handle text change (e.g., dynamic filtering)
                        return false;
                    }
                });
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, getViewLifecycleOwner());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_search, container, false);

        foodItemList = root.findViewById(R.id.foodItemList);
        List<FoodItemModel> foodList = new ArrayList<>();
        foodList.add(new FoodItemModel("Apple","", "100","it is sweet"));
        foodList.add(new FoodItemModel("Banana","", "120","it is sweet"));

        FoodItemAdapter adapter = new FoodItemAdapter(foodList);

        foodItemList.setLayoutManager(new GridLayoutManager(getContext(),2));
        foodItemList.setAdapter(adapter);

        return root;
    }
}