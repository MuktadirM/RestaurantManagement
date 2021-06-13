package com.example.restaurantmanagement.views.user.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentUserOrderProcessingBinding;
import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.order.OrderViewModel;
import com.example.restaurantmanagement.views.user.adapters.CheckoutArrayAdapter;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link UserOrderProcessingView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserOrderProcessingView extends DaggerFragment {
    private FragmentUserOrderProcessingBinding binding;
    private NavController navController;
    private OrderViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    public UserOrderProcessingView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserOrderProcessing.
     */
    // TODO: Rename and change types and number of parameters
    public static UserOrderProcessingView newInstance() {
        return new UserOrderProcessingView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(OrderViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserOrderProcessingBinding.inflate(inflater,container,false);

        binding.cancel.setOnClickListener(v->{
            navController.navigateUp();
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel.getAllCartItems().removeObservers(this);
        viewModel.getAllCartItems().observe(getViewLifecycleOwner(),cartFoodResource-> {
            if(cartFoodResource != null){
                switch (cartFoodResource.status){
                    case SUCCESS:
                        initListWithData(cartFoodResource.data);
                        break;
                    case ERROR:
                        Toast.makeText(getContext(), "Error found", Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                       // Snackbar.make(view,"Please wait...",Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        binding.confirm.setOnClickListener(v->{
            viewModel.orderConfirm().observe(getViewLifecycleOwner(),(orderRes)->{
                if(orderRes != null){
                    switch (orderRes.status){
                        case SUCCESS:
                            navController.navigate(R.id.action_userOrderProcessingView_to_nav_user_home);
                            break;
                        case ERROR:
                            Toast.makeText(v.getContext(),"Fail to complete order",Toast.LENGTH_SHORT).show();
                            break;
                        case LOADING:
                            break;
                    }
                }
            });
        });
    }

    public void initListWithData(List<Food> foodList){
        if(getContext() != null){
            CheckoutArrayAdapter adapter = new CheckoutArrayAdapter(getContext(),foodList);
            binding.lineItemListView.setAdapter(adapter);
        }
        binding.priceTotal.setText(calculate(foodList)+"RM");
    }

    private double calculate(List<Food> list){
        double price = 0;
        for (Food food: list) {
            price += food.getPrice();
        }
        return price;
    }
}