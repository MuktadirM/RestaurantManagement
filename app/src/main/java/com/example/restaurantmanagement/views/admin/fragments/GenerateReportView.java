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

import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.FragmentGenerateReportViewBinding;
import com.example.restaurantmanagement.databinding.FragmentOrderViewBinding;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.order.OrderViewModel;
import com.example.restaurantmanagement.views.admin.adpaters.AdminOrderRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link GenerateReportView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenerateReportView extends DaggerFragment {
    private FragmentGenerateReportViewBinding binding;

    private NavController navController;
    private OrderViewModel viewModel;
    private AdminOrderRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    public GenerateReportView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GenerateReportView.
     */
    // TODO: Rename and change types and number of parameters
    public static GenerateReportView newInstance() {
        return new GenerateReportView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(OrderViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGenerateReportViewBinding.inflate(inflater,container,false);
        initRecycler();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        viewModel.getAllOrders().removeObservers(this);
        viewModel.getAllOrders().observe(getViewLifecycleOwner(),(orderRes)->{
            if(orderRes != null){
                switch (orderRes.status){
                    case SUCCESS:
                        assert orderRes.data != null;
                        adapter.setOrderList(orderRes.data);
                        adapter.notifyDataSetChanged();
                        break;
                    case ERROR:
                        Toast.makeText(getContext(),"Data load error ",Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        break;
                }
            }
        });
    }

    private void initRecycler(){
        adapter = new AdminOrderRecyclerAdapter(getContext(),new ArrayList<>(),true);
        binding.orderItemsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.orderItemsRecycler.setAdapter(adapter);
    }
}