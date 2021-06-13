package com.example.restaurantmanagement.viewModels.auth;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.restaurantmanagement.SessionManager;
import com.example.restaurantmanagement.domain.models.auth.Login;
import com.example.restaurantmanagement.domain.models.auth.Register;
import com.example.restaurantmanagement.domain.models.core.ImageUpload;
import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.domain.services.IAuthServices;
import com.example.restaurantmanagement.domain.utils.FileUploadHelper;
import com.example.restaurantmanagement.utils.AuthResource;
import com.example.restaurantmanagement.utils.Resource;

import javax.inject.Inject;

import static com.example.restaurantmanagement.utils.Constrains.USER_PATH;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";
    private final IAuthServices services;
    private final SessionManager sessionManager;

    @Inject
    public AuthViewModel(IAuthServices services, SessionManager sessionManager) {
        this.services = services;
        this.sessionManager = sessionManager;

        if(services.getLoggedInUser()){
            previouslyAuthorize();
        }

    }

    public void loginWithEmailPassword(Login login){
        sessionManager.loginWithEmailPassword(services.login(login));
    }

    public LiveData<AuthResource<Profile>> registerWithEmailPassword(Register register){
        return services.create(register);
    }

    public LiveData<AuthResource<Profile>> addUser(Register profile){
        return services.create(profile);
    }

    public LiveData<Resource<Profile>> getProfile(){
        return services.getUserData();
    }

    public LiveData<Resource<ImageUpload>> uploadImage(Uri uri, String fileExt){
        FileUploadHelper uploadHelper = new FileUploadHelper(uri,fileExt,USER_PATH,true);
        return services.imageUpload(uploadHelper);
    }

    public LiveData<Resource<Profile>> updateProfile(Profile profile) {
        return services.update(profile);
    }

    public LiveData<AuthResource<Profile>> observeAuthState(){
        return sessionManager.getAuthUser();
    }

    public void previouslyAuthorize(){
        sessionManager.loginWithEmailPassword(services.getLiveDataUser());
    }

    public boolean previouslyLogin() {
        return services.getLoggedInUser();
    }

    public void logout(){
        sessionManager.logout();
    }
}
