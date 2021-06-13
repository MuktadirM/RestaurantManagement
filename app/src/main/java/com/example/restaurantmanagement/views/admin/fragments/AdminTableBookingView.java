package com.example.restaurantmanagement.views.admin.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantmanagement.databinding.FragmentAdminTableBookingBinding;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminTableBookingView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminTableBookingView extends Fragment {
    private FragmentAdminTableBookingBinding binding;

    public AdminTableBookingView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminTableBooking.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminTableBookingView newInstance(String param1, String param2) {
        return new AdminTableBookingView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminTableBookingBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}