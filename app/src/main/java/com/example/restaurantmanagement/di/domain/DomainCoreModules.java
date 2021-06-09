package com.example.restaurantmanagement.di.domain;

import com.example.restaurantmanagement.domain.services.IAuthServices;
import com.example.restaurantmanagement.domain.services.IOrderServices;
import com.example.restaurantmanagement.repository.auth.AuthServices;
import com.example.restaurantmanagement.repository.services.OrderServices;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DomainCoreModules {

    @Binds
    abstract IAuthServices provideAuthServices(AuthServices authServices);

    @Binds
    @Singleton
    abstract IOrderServices providesOrderServices(OrderServices orderServices);
}
