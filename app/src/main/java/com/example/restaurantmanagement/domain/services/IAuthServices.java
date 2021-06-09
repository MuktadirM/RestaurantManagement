package com.example.restaurantmanagement.domain.services;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.example.restaurantmanagement.domain.models.auth.Login;
import com.example.restaurantmanagement.domain.models.auth.Register;
import com.example.restaurantmanagement.domain.models.core.ImageUpload;
import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.utils.AuthResource;
import com.example.restaurantmanagement.utils.Resource;
import com.google.firebase.auth.FirebaseUser;


public interface IAuthServices {
    public LiveData<AuthResource<Profile>> create(Register register);
    public LiveData<AuthResource<Profile>> login(Login login);
    public LiveData<Resource<Profile>> getUserData();
    public LiveData<Resource<Profile>> update(Profile profile);
    public LiveData<Resource<ImageUpload>> imageUpload(Uri uri, String fileExt);
    public LiveData<AuthResource<Profile>> getLoggedInUserData(FirebaseUser user);
    public LiveData<AuthResource<Profile>> getLiveDataUser();
    public boolean getLoggedInUser();
    public String getUid();
    public void signOut();
}
