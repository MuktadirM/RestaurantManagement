package com.example.restaurantmanagement.views.user.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.restaurantmanagement.databinding.FragmentUserMenuItemDetailsViewBinding;
import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.utils.Resource;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.order.OrderViewModel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.example.restaurantmanagement.utils.Constrains.MENU_ITEM_DETAILS;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link UserMenuItemDetailsView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserMenuItemDetailsView extends DaggerFragment {
    private FragmentUserMenuItemDetailsViewBinding binding;
    private Food item;
    private NavController navController;

    private OrderViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    public UserMenuItemDetailsView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserMenuItemDetailsView.
     */
    // TODO: Rename and change types and number of parameters
    public static UserMenuItemDetailsView newInstance() {
        return new UserMenuItemDetailsView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = getArguments().getParcelable(MENU_ITEM_DETAILS);
        }

        viewModel = new ViewModelProvider(this, providerFactory).get(OrderViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserMenuItemDetailsViewBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        binding.setFood(item);

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addToCart(item).observe(getViewLifecycleOwner(), new Observer<Resource<Boolean>>() {
                    @Override
                    public void onChanged(Resource<Boolean> booleanResource) {
                        if(booleanResource != null){
                            switch (booleanResource.status){
                                case SUCCESS:
                                    navController.popBackStack();
                                    break;
                                case ERROR:
                                    break;
                                case LOADING:
                                    break;
                            }
                        }
                    }
                });
            }
        });
    }

}