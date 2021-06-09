package com.example.restaurantmanagement.views.user.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
public class UserCartItemView extends DaggerFragment implements ItemOnClick<Food> {
    private FragmentUserCartItemViewBinding binding;
    private OrderViewModel viewModel;
    private List<Food> foodList;
    private UserCartRecyclerAdapter adapter;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getAllCartItems().removeObservers(this);
        viewModel.getAllCartItems().observe(getViewLifecycleOwner(), new Observer<Resource<List<Food>>>() {
            @Override
            public void onChanged(Resource<List<Food>> listResource) {
                if(listResource !=null){
                    switch (listResource.status){
                        case SUCCESS:
                            assert listResource.data != null;
                            foodList = listResource.data;
                            adapter.setFoodList(foodList);
                            adapter.notifyDataSetChanged();
                            break;
                        case ERROR:
                            break;
                        case LOADING:
                            Toast.makeText(getContext(),"Loading...",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void ItemClicked(Food item, int position) {
        viewModel.removeFromCart(item);
    }

    private void initRecycler(){
        adapter = new UserCartRecyclerAdapter(getContext(),foodList,this);
        binding.cartItemsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.cartItemsRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}