package com.example.restaurantmanagement.views.admin.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentMenuDetailsViewBinding;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.food.FoodViewModel;
import com.example.restaurantmanagement.views.models.FoodModel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.example.restaurantmanagement.utils.Constrains.FOOD_DETAILS;
import static com.example.restaurantmanagement.utils.Constrains.FOOD_EDIT;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link MenuDetailsView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuDetailsView extends DaggerFragment {
    private FragmentMenuDetailsViewBinding binding;
    private FoodModel food;
    private FoodViewModel viewModel;

    private NavController navController;

    @Inject
    ViewModelProviderFactory providerFactory;
    public MenuDetailsView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MenuDetailsView.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuDetailsView newInstance() {
        return new MenuDetailsView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(FoodViewModel.class);

        if(getArguments() != null){
            food = getArguments().getParcelable(FOOD_DETAILS);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuDetailsViewBinding.inflate(inflater,container,false);

        binding.setFoodModel(food);
        binding.edit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(FOOD_EDIT,food);
            navController.navigate(R.id.action_menuDetailsView_to_editMenuView,bundle);
        });
        binding.delete.setOnClickListener(v -> {
            viewModel.deleteFood(food);
            navController.popBackStack();
        });

        binding.description.setMovementMethod(new ScrollingMovementMethod());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}