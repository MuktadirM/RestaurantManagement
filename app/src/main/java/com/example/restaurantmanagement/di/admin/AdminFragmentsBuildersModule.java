package com.example.restaurantmanagement.di.admin;

import com.example.restaurantmanagement.views.admin.fragments.AddMenuView;
import com.example.restaurantmanagement.views.admin.fragments.AdminOrderDetailsView;
import com.example.restaurantmanagement.views.admin.fragments.EditMenuView;
import com.example.restaurantmanagement.views.admin.fragments.GenerateReportView;
import com.example.restaurantmanagement.views.admin.fragments.MenuDetailsView;
import com.example.restaurantmanagement.views.admin.fragments.MenuView;
import com.example.restaurantmanagement.views.admin.fragments.OrderView;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AdminFragmentsBuildersModule {

    @ContributesAndroidInjector
    public abstract OrderView contributesOrderView();

    @ContributesAndroidInjector
    public abstract AdminOrderDetailsView contributesAdminOrderDetailsView();

    @ContributesAndroidInjector
    public abstract MenuView contributesMenuView();

    @ContributesAndroidInjector
    public abstract MenuDetailsView contributesMenuDetailsView();

    @ContributesAndroidInjector
    public abstract AddMenuView contributesAddMenuView();

    @ContributesAndroidInjector
    public abstract EditMenuView contributesEditMenuView();

    @ContributesAndroidInjector
    public abstract GenerateReportView contributesGenerateReportView();

}
