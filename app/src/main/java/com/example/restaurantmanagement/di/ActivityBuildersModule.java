package com.example.restaurantmanagement.di;

import com.example.restaurantmanagement.di.admin.AdminFragmentsBuildersModule;
import com.example.restaurantmanagement.di.admin.AdminViewModelsModule;
import com.example.restaurantmanagement.di.user.UserFragmentsBuildersModule;
import com.example.restaurantmanagement.di.user.UserViewModelsModule;
import com.example.restaurantmanagement.views.admin.AdminHostActivity;
import com.example.restaurantmanagement.views.auth.AuthActivity;
import com.example.restaurantmanagement.views.auth.RegisterActivity;
import com.example.restaurantmanagement.views.user.UserHostActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract AuthActivity contributeAuthActivity();

    @ContributesAndroidInjector
    abstract RegisterActivity contributeRegisterActivity();

    @ContributesAndroidInjector(modules = {AdminFragmentsBuildersModule.class, AdminViewModelsModule.class})
    abstract AdminHostActivity contributeAdminHostActivity();

    @ContributesAndroidInjector(modules = {UserFragmentsBuildersModule.class, UserViewModelsModule.class})
    abstract UserHostActivity contributeUserHostActivity();

}
