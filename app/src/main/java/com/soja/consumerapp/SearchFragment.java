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
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {

    RecyclerView foodItemList;
    FirebaseFirestore firestore;
    private FarmerAdapter.OnFarmerClickListener listener;

    public interface OnFarmerClickListener {
        void onFarmerClick(FarmerModel farmer);
    }
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
        firestore = FirebaseFirestore.getInstance();

        foodItemList = root.findViewById(R.id.foodItemList);
        List<FoodItemModel> foodList = new ArrayList<>();


        FoodItemAdapter adapter = new FoodItemAdapter(foodList, getContext(), foodItem-> {
                Toast.makeText(getContext(), "Clicked" + foodItem.getName(), Toast.LENGTH_SHORT).show();
        });

        firestore.collection("MarketPlace").get().addOnCompleteListener(task->{
            if(task.isSuccessful())
            {
                for(QueryDocumentSnapshot document : task.getResult())
                {
                    Map<String,Object> data = document.getData();
                    foodList.add(new FoodItemModel(data.get("name").toString(),"", data.get("pricePerKg").toString(),"it is sweet"));
                    adapter.notifyDataSetChanged();
                }
            }
        });

        foodItemList.setLayoutManager(new GridLayoutManager(getContext(),2));
        foodItemList.setAdapter(adapter);

        return root;
    }
}