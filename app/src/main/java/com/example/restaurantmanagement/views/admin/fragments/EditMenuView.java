package com.example.restaurantmanagement.views.admin.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentEditMenuViewBinding;
import com.example.restaurantmanagement.views.models.FoodModel;

import dagger.android.support.DaggerFragment;

import static com.example.restaurantmanagement.utils.Constrains.FOOD_EDIT;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link EditMenuView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditMenuView extends DaggerFragment {
    private FragmentEditMenuViewBinding binding;
    private FoodModel foodModel;

    public EditMenuView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditMenuView.
     */
    // TODO: Rename and change types and number of parameters
    public static EditMenuView newInstance() {
        return new EditMenuView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            foodModel = getArguments().getParcelable(FOOD_EDIT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditMenuViewBinding.inflate(inflater,container,false);
        binding.setFood(foodModel);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}