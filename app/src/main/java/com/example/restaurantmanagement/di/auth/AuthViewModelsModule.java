package com.example.restaurantmanagement.di.auth;

import androidx.lifecycle.ViewModel;

import com.example.restaurantmanagement.di.ViewModelKey;
import com.example.restaurantmanagement.viewModels.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);
}
