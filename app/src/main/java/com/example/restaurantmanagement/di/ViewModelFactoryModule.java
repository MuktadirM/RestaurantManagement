package com.example.restaurantmanagement.di;

import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelProviderFactory(ViewModelProviderFactory modelProviderFactory);
}
