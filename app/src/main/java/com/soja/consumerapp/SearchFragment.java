package com.soja.consumerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {

    RecyclerView foodItemList;
    FirebaseFirestore firestore;
    private FarmerAdapter.OnFarmerClickListener listener;
    List<FoodItemModel> foodList = new ArrayList<>();
    FoodItemAdapter adapter;

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

                        firestore.collection("MarketPlace").get().addOnCompleteListener(task->{
                            if(task.isSuccessful())
                            {
                                foodList.clear();
                                for(QueryDocumentSnapshot document : task.getResult())
                                {
                                    for (String key : document.getData().keySet())
                                    {
                                        Object value = document.get(key);
                                        if (value instanceof List) { // Check if it's an array
                                            List<String> productData = (List<String>) value;

                                            if (productData.size() >= 4) {
                                                foodList.add(new FoodItemModel(
                                                        productData.get(0), // Name
                                                        productData.get(1), // Description
                                                        productData.get(2), // Price
                                                        productData.get(3),  // Image URL
                                                        document.getId()
                                                ));
                                            }
                                        }
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            else
                            {
                                Toast.makeText(getContext(), "check your internet", Toast.LENGTH_SHORT).show();
                            }
                        });
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

        adapter = new FoodItemAdapter(foodList, getContext(), foodItem-> {
            gotoDetailedView(foodItem);
        });

        firestore.collection("MarketPlace").get().addOnCompleteListener(task->{
            if(task.isSuccessful())
            {
                foodList.clear();
                for(QueryDocumentSnapshot document : task.getResult())
                {
                    for (String key : document.getData().keySet())
                    {
                        Object value = document.get(key);
                        if (value instanceof List) { // Check if it's an array
                            List<String> productData = (List<String>) value;

                            if (productData.size() >= 4) {
                                foodList.add(new FoodItemModel(
                                        productData.get(0), // Name
                                        productData.get(1), // Description
                                        productData.get(2), // Price
                                        productData.get(3),  // Image URL
                                        document.getId()
                                ));
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        foodItemList.setLayoutManager(new GridLayoutManager(getContext(),2));
        foodItemList.setAdapter(adapter);

        return root;
    }


    public void gotoDetailedView(FoodItemModel fm)
    {
        Bundle data = new Bundle();
        data.putSerializable("foodItem", (Serializable) fm);
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_search_to_detailed,data);

    }
}