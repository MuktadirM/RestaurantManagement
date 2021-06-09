package com.example.restaurantmanagement.repository.auth;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.example.restaurantmanagement.domain.models.auth.Login;
import com.example.restaurantmanagement.domain.models.auth.Register;
import com.example.restaurantmanagement.domain.models.core.ImageUpload;
import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.domain.services.IAuthServices;
import com.example.restaurantmanagement.utils.AuthResource;
import com.example.restaurantmanagement.utils.Resource;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

public class AuthServices implements IAuthServices {
    @Inject
    public AuthServices() {
    }

    @Override
    public LiveData<AuthResource<Profile>> create(Register register) {
        return null;
    }

    @Override
    public LiveData<AuthResource<Profile>> login(Login login) {
        return null;
    }

    @Override
    public LiveData<Resource<Profile>> getUserData() {
        return null;
    }

    @Override
    public LiveData<Resource<Profile>> update(Profile profile) {
        return null;
    }

    @Override
    public LiveData<Resource<ImageUpload>> imageUpload(Uri uri, String fileExt) {
        return null;
    }

    @Override
    public LiveData<AuthResource<Profile>> getLoggedInUserData(FirebaseUser user) {
        return null;
    }

    @Override
    public LiveData<AuthResource<Profile>> getLiveDataUser() {
        return null;
    }

    @Override
    public boolean getLoggedInUser() {
        return false;
    }

    @Override
    public String getUid() {
        return null;
    }

    @Override
    public void signOut() {

    }
}
