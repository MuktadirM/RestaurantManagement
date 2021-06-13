package com.example.restaurantmanagement.views.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantmanagement.databinding.ActivityRegisterBinding;
import com.example.restaurantmanagement.domain.models.auth.Register;
import com.example.restaurantmanagement.domain.utils.UserType;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.auth.AuthViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class RegisterActivity extends DaggerAppCompatActivity {
    private ActivityRegisterBinding binding;
    private static final String TAG = "RegisterActivity";

    private AuthViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this,providerFactory).get(AuthViewModel.class);

        binding.register.setOnClickListener(v -> {
            Register register = new Register(
                    binding.email.getText().toString().trim(),
                    binding.password.getText().toString().trim(),
                    binding.name.getText().toString().trim(),
                    binding.phone.getText().toString().trim(),
                    UserType.User,
                    binding.address.getText().toString().trim(),
                    "https://firebasestorage.googleapis.com/v0/b/restaurant-management-99ad9.appspot.com/o/users%2F0c3b3adb1a7530892e55ef36d3be6cb8.png?alt=media&token=277a8955-3326-4fcb-bf88-2b125020854e",
                    binding.ic.getText().toString().trim(),
                    getGender(),
                    binding.confirmPassword.getText().toString().trim()
            );
            checkUserIsCompleted(register);
        });

        binding.backToLogin.setOnClickListener(v->{
            backToLogin();
        });
    }

    private void checkUserIsCompleted(Register register){
        viewModel.registerWithEmailPassword(register).observe(this, userAuthResource -> {
            if(userAuthResource !=null){
                switch (userAuthResource.status){
                    case LOADING:
                        Log.d(TAG, "onChanged: loading");
                        break;
                    case AUTHENTICATED:
                        Log.d(TAG, "onChanged: Auth success");
                        backToLogin();
                        break;
                    case ERROR:
                        setErrorMsgBottom(userAuthResource.message);
                        Log.d(TAG, "onChanged: Error");
                        break;
                    case NOT_AUTHENTICATED:
                        Log.d(TAG, "onChanged: Not Authenticated");
                        break;
                }
            }
        });
    }

    private String getGender(){
        if(binding.radioMale.isChecked()){
            return "Male";
        }
        return "Female";
    }

    private void setErrorMsgBottom(String msg){

    }

    private void backToLogin(){
        Intent intent = new Intent(RegisterActivity.this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}