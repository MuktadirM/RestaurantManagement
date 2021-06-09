package com.example.restaurantmanagement.views.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.restaurantmanagement.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
    }
}