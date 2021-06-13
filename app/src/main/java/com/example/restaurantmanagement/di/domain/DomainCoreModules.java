package com.example.restaurantmanagement.di.domain;

import com.example.restaurantmanagement.domain.services.IAuthServices;
import com.example.restaurantmanagement.domain.services.IFileUploadServices;
import com.example.restaurantmanagement.domain.services.IFoodServices;
import com.example.restaurantmanagement.domain.services.IOrderServices;
import com.example.restaurantmanagement.domain.services.ITableBookingServices;
import com.example.restaurantmanagement.repository.auth.AuthServices;
import com.example.restaurantmanagement.repository.services.FileUploadServices;
import com.example.restaurantmanagement.repository.services.FoodServices;
import com.example.restaurantmanagement.repository.services.OrderServices;
import com.example.restaurantmanagement.repository.services.TableBookingServices;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DomainCoreModules {

    @Binds
    @Singleton
    abstract IFileUploadServices providesFileUploadServices(FileUploadServices fileUploadServices);

    @Binds
    @Singleton
    abstract IAuthServices provideAuthServices(AuthServices authServices);

    @Binds
    @Singleton
    abstract IOrderServices providesOrderServices(OrderServices orderServices);

    @Binds
    @Singleton
    abstract IFoodServices providesFoodServices(FoodServices foodServices);

    @Binds
    @Singleton
    abstract ITableBookingServices providesTableBookingServices(TableBookingServices tableBookingServices);
}
