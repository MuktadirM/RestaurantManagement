package com.example.restaurantmanagement.di.user;

import androidx.lifecycle.ViewModel;

import com.example.restaurantmanagement.di.ViewModelKey;
import com.example.restaurantmanagement.viewModels.order.OrderViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class UserViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel.class)
    public abstract ViewModel bindOrderViewModel(OrderViewModel orderViewModel);
}
