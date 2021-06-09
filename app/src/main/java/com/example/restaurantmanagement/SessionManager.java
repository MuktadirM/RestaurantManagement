package com.example.restaurantmanagement;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.domain.services.IAuthServices;
import com.example.restaurantmanagement.utils.AuthResource;

import javax.inject.Inject;

public class SessionManager {

    public static final String TAG="SessionManager";
    private final MediatorLiveData<AuthResource<Profile>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    @Inject
    IAuthServices services;

    public void loginWithEmailPassword(final LiveData<AuthResource<Profile>> source){
        if(cachedUser != null){
            cachedUser.setValue(AuthResource.loading((Profile) null));
            cachedUser.addSource(source, new Observer<AuthResource<Profile>>() {
                @Override
                public void onChanged(AuthResource<Profile> userAuthResource) {
                    cachedUser.setValue(userAuthResource);
                    cachedUser.removeSource(source);
                }
            });
        }
        else {
            Log.d(TAG, "loginWithEmailPassword: Previously login");
        }
    }

    public LiveData<AuthResource<Profile>> getAuthUser(){
        return cachedUser;
    }

    public void logout(){
        services.signOut();
        cachedUser.setValue(AuthResource.<Profile>logout());
    }
}
