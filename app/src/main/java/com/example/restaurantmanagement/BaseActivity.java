package com.example.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.utils.AuthResource;
import com.example.restaurantmanagement.views.auth.AuthActivity;
import com.google.firebase.Timestamp;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    public String loginTime;
    public static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginTime = Timestamp.now().toDate().toString();

        observeUserLoginState();

    }

    public void observeUserLoginState(){
        sessionManager.getAuthUser().observe(this, userAuthResource -> {
            if(userAuthResource !=null){
                switch (userAuthResource.status){
                    case LOADING:
                        //loading
                        Log.d(TAG, "onChanged: Loading called");
                        break;
                    case AUTHENTICATED:
                        Log.d(TAG, "onChanged: Auth Success");
                        break;
                    case ERROR:
                        setMessage(userAuthResource.message);
                        break;
                    case NOT_AUTHENTICATED:
                        Log.d(TAG, "onChanged: Wrong pass or user name");
                        setMessage(userAuthResource.message);
                        navToLogin();
                        break;
                }
            }
        });
    }

    public void setMessage(String message){
    }

    private void navToLogin(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

}
