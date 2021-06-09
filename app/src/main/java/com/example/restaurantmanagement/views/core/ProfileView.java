package com.example.restaurantmanagement.views.core;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentProfileViewBinding;

import org.jetbrains.annotations.NotNull;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link ProfileView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileView extends DaggerFragment {
    private FragmentProfileViewBinding binding;
    private NavController navController;

    public ProfileView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileView.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileView newInstance() {
        return new ProfileView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileViewBinding.inflate(inflater,container,false);

        binding.userEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nav_admin_profile_to_nav_admin_edit_profile);
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}