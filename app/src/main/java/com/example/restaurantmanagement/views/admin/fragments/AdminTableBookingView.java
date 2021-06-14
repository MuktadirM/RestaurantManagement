package com.example.restaurantmanagement.views.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.restaurantmanagement.databinding.FragmentAdminTableBookingBinding;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.table.TableBookingViewModel;
import com.example.restaurantmanagement.views.admin.adpaters.AdminTableRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link AdminTableBookingView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminTableBookingView extends DaggerFragment {
    private FragmentAdminTableBookingBinding binding;
    private TableBookingViewModel viewModel;
    private NavController navController;

    private AdminTableRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

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
    public static AdminTableBookingView newInstance() {
        return new AdminTableBookingView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(TableBookingViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminTableBookingBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        initRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        viewModel.getAll().removeObservers(this);
        viewModel.getAll().observe(getViewLifecycleOwner(),resData->{
            if(resData != null){
                switch (resData.status){
                    case SUCCESS:
                        adapter.setBookingList(resData.data);
                        adapter.notifyDataSetChanged();
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

    private void initRecyclerView(){
        adapter = new AdminTableRecyclerAdapter(getContext(),new ArrayList<>());
        binding.tableBookingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.tableBookingRecycler.setAdapter(adapter);
    }
}