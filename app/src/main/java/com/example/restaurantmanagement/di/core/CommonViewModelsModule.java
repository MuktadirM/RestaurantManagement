package com.example.restaurantmanagement.di.core;

import androidx.lifecycle.ViewModel;

import com.example.restaurantmanagement.di.ViewModelKey;
import com.example.restaurantmanagement.viewModels.order.OrderViewModel;
import com.example.restaurantmanagement.viewModels.table.TableBookingViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class CommonViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel.class)
    public abstract ViewModel bindOrderViewModel(OrderViewModel orderViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TableBookingViewModel.class)
    public abstract ViewModel bindTableBookingViewModel(TableBookingViewModel tableBookingViewModel);
}
