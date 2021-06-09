package com.example.restaurantmanagement.di.core;

import com.example.restaurantmanagement.views.admin.fragments.OrderView;
import com.example.restaurantmanagement.views.core.EditProfileView;
import com.example.restaurantmanagement.views.core.ProfileView;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CommonFragmentsBuildersModule {

    @ContributesAndroidInjector
    public abstract ProfileView contributesOrderView();

    @ContributesAndroidInjector
    public abstract EditProfileView contributesEditProfileView();

}
