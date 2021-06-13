package com.example.restaurantmanagement.views.user.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentUserCartItemViewBinding;
import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.utils.ItemOnClick;
import com.example.restaurantmanagement.utils.Resource;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.order.OrderViewModel;
import com.example.restaurantmanagement.views.models.FoodModel;
import com.example.restaurantmanagement.views.models.mapping.FoodModelMapping;
import com.example.restaurantmanagement.views.user.adapters.UserCartRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link UserCartItemView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserCartItemView extends DaggerFragment implements ItemOnClick<FoodModel> {
    private FragmentUserCartItemViewBinding binding;
    private OrderViewModel viewModel;
    private List<FoodModel> foodList;
    private UserCartRecyclerAdapter adapter;

    private NavController navController;

    @Inject
    ViewModelProviderFactory providerFactory;

    public UserCartItemView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserCartItemView.
     */
    // TODO: Rename and change types and number of parameters
    public static UserCartItemView newInstance() {
        return new UserCartItemView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(OrderViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserCartItemViewBinding.inflate(inflater,container,false);
        initRecycler();
        // Inflate the layout for this fragment
        binding.checkoutBtn.setOnClickListener(v->{
            navController.navigate(R.id.action_nav_user_cart_item_to_userOrderProcessingView);
        });
        binding.cancel.setOnClickListener(v -> {
            navController.popBackStack();
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel.getAllCartItems().observe(getViewLifecycleOwner(), listResource -> {
            if(listResource !=null){
                switch (listResource.status){
                    case SUCCESS:
                        FoodModelMapping modelMapping = new FoodModelMapping();
                        assert listResource.data != null;
                        foodList = modelMapping.toModelList(listResource.data);
                        adapter.setFoodList(foodList);
                        adapter.notifyDataSetChanged();
                        binding.priceTotal.setText(calculate(listResource.data));
                        break;
                    case ERROR:
                        Toast.makeText(getContext(),"Fail",Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            }
        });
    }

    private String calculate(List<Food> list){
        double price = 0;
        for (Food food: list) {
            price += food.getPrice();
        }
        return "RM : "+price;
    }

    @Override
    public void ItemClicked(FoodModel item, int position) {
        viewModel.removeFromCart(item).observe(getViewLifecycleOwner(),(booleanResource -> {
            if(booleanResource != null){
                switch (booleanResource.status){
                    case SUCCESS:
                        break;
                    case ERROR:
                        Toast.makeText(getContext(),"Fail to process",Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            }
        }));

    }

    private void initRecycler(){
        adapter = new UserCartRecyclerAdapter(getContext(),new ArrayList<>(),this);
        binding.cartItemsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.cartItemsRecycler.setAdapter(adapter);
    }
}