package com.example.restaurantmanagement.views.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.restaurantmanagement.databinding.ActivityAuthBinding;
import com.example.restaurantmanagement.views.admin.AdminHostActivity;
import com.example.restaurantmanagement.views.user.UserHostActivity;

public class AuthActivity extends AppCompatActivity {
    private ActivityAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent(this, AdminHostActivity.class);
        startActivity(intent);
        finish();
    }
}