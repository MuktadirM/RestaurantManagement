package com.example.restaurantmanagement.views.user.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentUserTableBookingViewBinding;
import com.example.restaurantmanagement.domain.models.core.TableBooking;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.table.TableBookingViewModel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link UserTableBookingView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserTableBookingView extends DaggerFragment {
    private FragmentUserTableBookingViewBinding binding;
    private TableBookingViewModel viewModel;
    private NavController navController;

    @Inject
    ViewModelProviderFactory providerFactory;

    public UserTableBookingView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserTableBookingView.
     */
    // TODO: Rename and change types and number of parameters
    public static UserTableBookingView newInstance() {
        return new UserTableBookingView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(TableBookingViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserTableBookingViewBinding.inflate(inflater,container,false);

        binding.userAddTableFloatingBtn.setOnClickListener(v->{
            navController.navigate(R.id.action_userTableBookingView_to_userAddTableBookingView);
        });
        loadBookedTable(null);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        viewModel.getBookedTable().removeObservers(this);
        viewModel.getBookedTable().observe(getViewLifecycleOwner(),resData->{
            if(resData != null){
                switch (resData.status){
                    case SUCCESS:
                        loadBookedTable(resData.data);
                        break;
                    case ERROR:
                        Toast.makeText(getContext(),"Fail to retrieve",Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            }
        });
    }

    private void loadBookedTable(TableBooking data) {
        if(data == null){
            binding.noTable.setVisibility(View.VISIBLE);
            binding.tableInfo.setVisibility(View.GONE);
        }
        else {
            binding.noTable.setVisibility(View.GONE);
            binding.tableInfo.setVisibility(View.VISIBLE);
            binding.setTable(data);
        }
    }
}