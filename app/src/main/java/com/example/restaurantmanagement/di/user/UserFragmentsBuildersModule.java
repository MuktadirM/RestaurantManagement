package com.example.restaurantmanagement.di.user;

import com.example.restaurantmanagement.views.user.fragments.UserCartItemView;
import com.example.restaurantmanagement.views.user.fragments.UserMenuItemDetailsView;
import com.example.restaurantmanagement.views.user.fragments.UserOrderItemsView;
import com.example.restaurantmanagement.views.user.fragments.UserMenuView;
import com.example.restaurantmanagement.views.user.fragments.UserMoreView;
import com.example.restaurantmanagement.views.user.fragments.UserOrderHistoryView;
import com.example.restaurantmanagement.views.user.fragments.UserOrderProcessingView;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class UserFragmentsBuildersModule {

    @ContributesAndroidInjector
    public abstract UserMenuView contributesUserMenuView();

    @ContributesAndroidInjector
    public abstract UserOrderHistoryView contributesUserOrderHistoryView();

    @ContributesAndroidInjector
    public abstract UserOrderProcessingView contributesUserOrderProcessingView();

    @ContributesAndroidInjector
    public abstract UserMoreView contributesUserMoreView();

    @ContributesAndroidInjector
    public abstract UserOrderItemsView contributesUserOrderItemsView();

    @ContributesAndroidInjector
    public abstract UserCartItemView contributesUserCartItemView();

    @ContributesAndroidInjector
    public abstract UserMenuItemDetailsView contributesUserMenuItemDetailsView();

}
