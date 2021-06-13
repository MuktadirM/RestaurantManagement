package com.example.restaurantmanagement.views.user;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.restaurantmanagement.BaseActivity;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.databinding.ActivityUserHostBinding;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.order.OrderViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

public class UserHostActivity extends BaseActivity {
    private ActivityUserHostBinding binding;
    private Menu shoppingCartMenu;
    private NavController navController;
    private NavHostFragment navHostFragment;
    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView bottomNavigationView;
    private OrderViewModel viewModel;
    private int cartQuantity = 0;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserHostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this,providerFactory).get(OrderViewModel.class);

        bottomNavigationView = binding.bottomNav;
        navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(binding.userNavHostFragment.getId());
        navController = navHostFragment.getNavController();
        initBottomNav();
    }

    private void initBottomNav() {
        Set<Integer> mTopLevelDestinations = new HashSet<>();
        mTopLevelDestinations.add(R.id.nav_user_home);
        mTopLevelDestinations.add(R.id.nav_user_orders);
        mTopLevelDestinations.add(R.id.nav_user_more);

        appBarConfiguration = new AppBarConfiguration.Builder(mTopLevelDestinations).build();

        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(shoppingCartMenu != null){
                shoppingCartMenu.findItem(R.id.cart).setVisible(destination.getId() == R.id.nav_user_home);
            }
            if(destination.getId() == R.id.nav_user_home ||
                    destination.getId() == R.id.nav_user_orders || destination.getId() == R.id.nav_user_more){
                showBottomNavbar();
            }
           else {
                hideBottomNavbar();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_cart,menu);

        MenuItem cartMenu = menu.findItem(R.id.cart);
        View viewCart = cartMenu.getActionView();
        shoppingCartMenu = menu;

        TextView cartBadgeTextView = viewCart.findViewById(R.id.cart_badge_txt);
        viewModel.getAllCartItems().observe(this, listResource -> {
            if(listResource !=null){
                switch (listResource.status){
                    case SUCCESS:
                        assert listResource.data != null;
                        cartQuantity = listResource.data.size();
                        cartBadgeTextView.setText(String.valueOf(cartQuantity));
                        cartBadgeTextView.setVisibility(cartQuantity == 0 ? View.GONE : View.VISIBLE);
                        break;
                    case ERROR:
                        break;
                    case LOADING:
                        cartBadgeTextView.setVisibility(cartQuantity == 0 ? View.GONE : View.VISIBLE);
                        break;
                }
            }
        });

        viewCart.setOnClickListener(v -> NavHostFragment.findNavController(navHostFragment).navigate(R.id.action_nav_user_home_to_nav_user_cart_item));
       return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.user_nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void hideBottomNavbar(){
        binding.bottomNav.setVisibility(View.GONE);
    }
    private void showBottomNavbar(){
        binding.bottomNav.setVisibility(View.VISIBLE);
    }

}