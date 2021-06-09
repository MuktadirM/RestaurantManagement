package com.example.restaurantmanagement.di.admin;

import androidx.lifecycle.ViewModel;

import com.example.restaurantmanagement.di.ViewModelKey;
import com.example.restaurantmanagement.viewModels.food.FoodViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AdminViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(FoodViewModel.class)
    public abstract ViewModel bindOrderViewModel(FoodViewModel foodViewModel);
}
