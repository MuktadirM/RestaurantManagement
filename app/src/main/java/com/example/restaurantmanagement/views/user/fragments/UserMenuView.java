package com.example.restaurantmanagement.views.user.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentUserMenuViewBinding;
import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.utils.AddToCartClicked;
import com.example.restaurantmanagement.utils.ItemOnClick;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.order.OrderViewModel;
import com.example.restaurantmanagement.views.user.adapters.UserFoodRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.example.restaurantmanagement.utils.Constrains.MENU_ITEM_DETAILS;
import static com.example.restaurantmanagement.utils.Constrains.getFoodList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserMenuView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserMenuView extends DaggerFragment implements ItemOnClick<Food> , AddToCartClicked {
    private FragmentUserMenuViewBinding binding;
    private OrderViewModel viewModel;
    private UserFoodRecyclerAdapter adapter;
    private NavController navController;

    @Inject
    ViewModelProviderFactory providerFactory;


    public UserMenuView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserMenuView.
     */
    // TODO: Rename and change types and number of parameters
    public static UserMenuView newInstance() {
        return new UserMenuView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(OrderViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserMenuViewBinding.inflate(inflater,container,false);
        initRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private void initRecyclerView(){
        adapter = new UserFoodRecyclerAdapter(getContext(),getFoodList(), this,this);
        binding.menusRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.menusRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void ItemClicked(Food item, int position) {
        Bundle args = new Bundle();
        args.putParcelable(MENU_ITEM_DETAILS,item);
        navController.navigate(R.id.action_nav_user_home_to_nav_user_menu_details,args);
    }

    @Override
    public void ItemToCartClicked(Food item, int position) {
        viewModel.addToCart(item);
    }


}