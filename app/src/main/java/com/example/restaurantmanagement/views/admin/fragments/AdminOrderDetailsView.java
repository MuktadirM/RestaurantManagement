package com.example.restaurantmanagement.views.admin.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantmanagement.databinding.FragmentAdminOrderDetailsViewBinding;
import com.example.restaurantmanagement.domain.models.food.Order;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link AdminOrderDetailsView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminOrderDetailsView extends DaggerFragment {
    private FragmentAdminOrderDetailsViewBinding binding;
    private Order order;

    public AdminOrderDetailsView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminOrderDetailsView.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminOrderDetailsView newInstance(String param1, String param2) {
        return new AdminOrderDetailsView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order = getArguments().getParcelable("order");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminOrderDetailsViewBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}