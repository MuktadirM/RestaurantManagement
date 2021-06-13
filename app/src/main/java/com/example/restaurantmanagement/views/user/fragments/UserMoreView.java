package com.example.restaurantmanagement.views.user.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.RequestManager;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentUserMoreViewBinding;
import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.auth.AuthViewModel;
import com.example.restaurantmanagement.views.auth.AuthActivity;
import com.example.restaurantmanagement.views.models.UserProfile;
import com.example.restaurantmanagement.views.models.mapping.ProfileToUserProfile;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link UserMoreView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserMoreView extends DaggerFragment {
    private AuthViewModel viewModel;
    private FragmentUserMoreViewBinding binding;
    private NavController navController;
    private UserProfile profile;

    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProviderFactory providerFactory;

    public UserMoreView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserMoreView.
     */
    // TODO: Rename and change types and number of parameters
    public static UserMoreView newInstance() {
        return new UserMoreView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserMoreViewBinding.inflate(inflater,container,false);

        binding.userEditProfileBtn.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putParcelable("profile",profile);
            navController.navigate(R.id.action_nav_user_more_to_nav_user_profile,bundle);
        });

        binding.bookTable.setOnClickListener(v->{
            navController.navigate(R.id.action_nav_user_more_to_userTableBookingView);
        });

        binding.logout.setOnClickListener(v->{
            viewModel.logout();
            Intent intent = new Intent(getActivity(), AuthActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        viewModel.getProfile().removeObservers(this);
        viewModel.getProfile().observe(getViewLifecycleOwner(),profileResource -> {
            if(profileResource != null){
                switch (profileResource.status){
                    case SUCCESS:
                        assert profileResource.data != null;
                        ProfileToUserProfile toUserProfile = new ProfileToUserProfile();
                        profile = toUserProfile.toDomain(profileResource.data);
                        loadProfileData(profile);
                        break;
                    case ERROR:
                        Toast.makeText(getContext(),"",Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            }
        });

    }

    private void loadProfileData(Profile data) {
        requestManager.load(data.getImage()).circleCrop().into(binding.userImage);
        binding.userName.setText(data.getName());
    }
}