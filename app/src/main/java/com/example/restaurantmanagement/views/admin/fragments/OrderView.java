package com.example.restaurantmanagement.views.admin.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.restaurantmanagement.R;
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
 * Use the {@link OrderView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderView extends DaggerFragment {
    private FragmentOrderViewBinding binding;

    private NavController navController;
    private OrderViewModel viewModel;
    private AdminOrderRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    public OrderView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrderView.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderView newInstance() {
        return new OrderView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(OrderViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderViewBinding.inflate(inflater,container,false);
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
        adapter = new AdminOrderRecyclerAdapter(getContext(),new ArrayList<>(),false);
        binding.orderItemsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.orderItemsRecycler.setAdapter(adapter);
    }
}