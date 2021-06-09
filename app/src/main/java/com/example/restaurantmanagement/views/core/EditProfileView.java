package com.example.restaurantmanagement.views.core;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentEditProfileViewBinding;
import com.example.restaurantmanagement.domain.models.core.Profile;

import org.jetbrains.annotations.NotNull;

import dagger.android.support.DaggerFragment;

import static com.example.restaurantmanagement.utils.Constrains.EDIT_PROFILE;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link EditProfileView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileView extends DaggerFragment {
    private FragmentEditProfileViewBinding binding;
    private Profile profile;

    public EditProfileView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditProfileView.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileView newInstance() {
        return new EditProfileView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            profile = getArguments().getParcelable(EDIT_PROFILE);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileViewBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}