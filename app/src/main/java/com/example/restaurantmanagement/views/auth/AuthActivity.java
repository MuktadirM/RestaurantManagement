package com.example.restaurantmanagement.views.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantmanagement.databinding.ActivityAuthBinding;
import com.example.restaurantmanagement.domain.models.auth.Login;
import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.auth.AuthViewModel;
import com.example.restaurantmanagement.views.admin.AdminHostActivity;
import com.example.restaurantmanagement.views.user.UserHostActivity;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {
    private static final String TAG = "AuthActivity";
    private ActivityAuthBinding binding;

    private AuthViewModel viewModel;
    private Snackbar snackbar;
    private String userEmail="";
    private String userPassword="";

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this,providerFactory).get(AuthViewModel.class);

        if(viewModel.previouslyLogin()){
            binding.login.setEnabled(false);
        }

        binding.login.setOnClickListener(v->{
//            Intent intent = new Intent(this, UserHostActivity.class);
//            startActivity(intent);
//            finish();

            binding.loginMsg.setVisibility(View.GONE);
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();
            if(validatedEmail() && validatedPassword()){
                userEmail = email;
                userPassword = password;
                Login login = new Login(userEmail,userPassword);
                viewModel.loginWithEmailPassword(login);
            }
            else{
                validatedEmail();
                validatedPassword();
            }
        });

        binding.register.setOnClickListener(v->{
            Intent register = new Intent(this, RegisterActivity.class);
            startActivity(register);
            finish();
        });

        observeChangesInLogin();
    }

    private void observeChangesInLogin(){
        viewModel.observeAuthState().observe(this, userAuthResource -> {
            if(userAuthResource != null){
                switch (userAuthResource.status){
                    case LOADING:
                        Log.d(TAG, "onChanged: Loading called");
                        setErrorMsgBottom("Please wait......");
                        break;
                    case AUTHENTICATED:
                        snackbar.dismiss();
                        if (userAuthResource.data != null) {
                            loginSuccess(userAuthResource.data);
                        }
                        Log.d(TAG, "onChanged: Auth Success");
                        break;
                    case ERROR:
                        snackbar.dismiss();
                        Log.d(TAG, "onChanged: Auth Fail");
                        setLoginMessage(userAuthResource.message);
                        break;
                    case NOT_AUTHENTICATED:
                        snackbar.dismiss();
                        Log.d(TAG, "onChanged: "+userAuthResource.message);
                        setLoginMessage(userAuthResource.message);
                        break;
                }
            }
        });
    }

    private void loginSuccess(Profile data) {
        switch (data.getType()){
            case Admin:
                Intent intent = new Intent(AuthActivity.this,AdminHostActivity.class);
                startActivity(intent);
                finish();
                break;
            case User:
                Intent intentUser = new Intent(AuthActivity.this,UserHostActivity.class);
                startActivity(intentUser);
                finish();
                break;
        }
    }

    private void setLoginMessage(String message){
        binding.loginMsg.setVisibility(View.VISIBLE);
        binding.loginMsg.setText(message);
    }

    private void setErrorMsgBottom(String msg){
        snackbar = Snackbar.make(binding.getRoot(),msg,Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    private boolean validatedEmail(){
        if(binding.email.getText().toString().trim().isEmpty()){
            binding.emailLayout.setErrorEnabled(true);
            binding.emailLayout.setError("Email can't be empty");
            return false;
        }
        binding.emailLayout.setErrorEnabled(false);
        return true;
    }
    private boolean validatedPassword(){
        if(binding.password.getText().toString().trim().isEmpty()){
            binding.passwordLayout.setErrorEnabled(true);
            binding.passwordLayout.setError("Password can't be empty");
            return false;
        }
        binding.passwordLayout.setErrorEnabled(false);
        return true;
    }
}