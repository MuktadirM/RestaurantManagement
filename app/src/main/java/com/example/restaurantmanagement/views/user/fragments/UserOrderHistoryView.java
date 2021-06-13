package com.example.restaurantmanagement.views.user.fragments;

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
import com.example.restaurantmanagement.databinding.FragmentUserOrderHistoryViewBinding;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.order.OrderViewModel;
import com.example.restaurantmanagement.views.user.adapters.UserOrderRecyclerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link UserOrderHistoryView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserOrderHistoryView extends DaggerFragment {
    private FragmentUserOrderHistoryViewBinding binding;

    private NavController navController;
    private OrderViewModel viewModel;
    private UserOrderRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    public UserOrderHistoryView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserOrderHistoryView.
     */
    // TODO: Rename and change types and number of parameters
    public static UserOrderHistoryView newInstance() {
        return new UserOrderHistoryView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(OrderViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserOrderHistoryViewBinding.inflate(inflater,container,false);
        initRecycler();
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        viewModel.getAllUserOrders().removeObservers(this);
        viewModel.getAllUserOrders().observe(getViewLifecycleOwner(),(orderRes)->{
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
        adapter = new UserOrderRecyclerAdapter(getContext(),new ArrayList<>());
        binding.orderItemsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.orderItemsRecycler.setAdapter(adapter);
    }
}