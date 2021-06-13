package com.example.restaurantmanagement.views.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentMenuViewBinding;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.food.FoodViewModel;
import com.example.restaurantmanagement.views.admin.adpaters.AdminFoodRecyclerAdapter;
import com.example.restaurantmanagement.views.models.FoodModel;
import com.example.restaurantmanagement.views.models.mapping.FoodModelMapping;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.example.restaurantmanagement.utils.Constrains.FOOD_DETAILS;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link MenuView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuView extends DaggerFragment implements AdminFoodRecyclerAdapter.MenuInterface {
    private FragmentMenuViewBinding binding;
    private AdminFoodRecyclerAdapter adapter;
    private FoodViewModel viewModel;

    private NavController navController;

    @Inject
    ViewModelProviderFactory providerFactory;

    public MenuView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MenuView.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuView newInstance() {
        return new MenuView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(FoodViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuViewBinding.inflate(inflater,container,false);

        adapter = new AdminFoodRecyclerAdapter(this);
        binding.adminMenuRecycler.setAdapter(adapter);

        binding.adminAddMenuFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nav_admin_home_to_addMenuView);
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onItemClick(FoodModel food) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(FOOD_DETAILS,food);
        navController.navigate(R.id.action_nav_admin_home_to_menuDetailsView,bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel.getAllMenus().removeObservers(this);
        viewModel.getAllMenus().observe(getViewLifecycleOwner(),(data)->{
            if(data != null){
                switch (data.status){
                    case SUCCESS:
                        FoodModelMapping modelMapping = new FoodModelMapping();
                        assert data.data != null;
                        adapter.submitList(modelMapping.toModelList(data.data));
                        break;
                    case ERROR:
                        Toast.makeText(getContext(),"Fail to Load Data",Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            }

        });
    }
}