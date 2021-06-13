package com.example.restaurantmanagement.repository.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantmanagement.domain.models.auth.Login;
import com.example.restaurantmanagement.domain.models.auth.Register;
import com.example.restaurantmanagement.domain.models.core.ImageUpload;
import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.domain.services.IAuthServices;
import com.example.restaurantmanagement.domain.services.IFileUploadServices;
import com.example.restaurantmanagement.domain.utils.FileUploadHelper;
import com.example.restaurantmanagement.repository.dts.FromRegisterToProfile;
import com.example.restaurantmanagement.utils.AuthResource;
import com.example.restaurantmanagement.utils.Resource;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;

import javax.inject.Inject;

import static com.example.restaurantmanagement.utils.Constrains.USER_PATH;

public class AuthServices implements IAuthServices {
    private final IFileUploadServices fileUploadServices;
    private MutableLiveData<Resource<Profile>> profileInfo;
    private MutableLiveData<AuthResource<Profile>> accountInfo;

    @Inject
    FirebaseFirestore firestore;

    @Inject
    FirebaseStorage storage;

    FirebaseUser user;

    @Inject
    FirebaseAuth auth;

    @Inject
    public AuthServices(IFileUploadServices fileUploadServices) {
        this.fileUploadServices = fileUploadServices;
    }

    @Override
    public LiveData<AuthResource<Profile>> create(Register register) {
        accountInfo = new MutableLiveData<>();
        accountInfo.setValue(AuthResource.loading(null));
        boolean isValid = true;

        if(!register.getPassword().contentEquals(register.getConfirmPassword())){
            isValid = false;
        }
        if(isValid){
            auth.createUserWithEmailAndPassword(register.getEmail(),register.getPassword())
                    .addOnSuccessListener(authResult -> {
                        if(authResult.getUser() != null) {
                            user = authResult.getUser();
                            CollectionReference reference = firestore.collection(USER_PATH);
                            String userId = user.getUid();
                            FromRegisterToProfile profileDts = new FromRegisterToProfile(userId);
                            Profile profile = profileDts.toDomain(register);
                            reference.document(userId).set(profile);
                            accountInfo.setValue(AuthResource.authenticated(profile));
                        }
                    }).addOnFailureListener(e ->
                    accountInfo.setValue(AuthResource.error(Objects.requireNonNull(e.getMessage()),null)));

        }
        else {
            accountInfo.setValue(AuthResource.error("Password does not match",null));
        }

        return accountInfo;
    }

    @Override
    public LiveData<AuthResource<Profile>> login(Login login) {
        accountInfo = new MutableLiveData<>();
        auth.signInWithEmailAndPassword(login.getEmail(),login.getPassword())
                .addOnSuccessListener(
                authResult -> {

            if(authResult.getUser() != null){
                user = authResult.getUser();
                getLoggedInUserData(user);
            }

        }).addOnFailureListener(
                e -> accountInfo.setValue(AuthResource.error("User Name or password wrong",null)));

        return accountInfo;
    }

    @Override
    public LiveData<Resource<Profile>> getUserData() {
        if(profileInfo == null){
            profileInfo = new MutableLiveData<>();
        }
        CollectionReference reference = firestore.collection(USER_PATH);
        String userId = getUserUid();

        reference.document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                Profile profile = documentSnapshot.toObject(Profile.class);
                profileInfo.setValue(Resource.success(profile));
            }
        });

        return profileInfo;
    }

    public String getUserUid(){
        return Objects.requireNonNull(auth.getCurrentUser()).getUid();
    }

    @Override
    public LiveData<Resource<Profile>> update(Profile profile) {
        String userId = getUserUid();
        CollectionReference reference = firestore.collection(USER_PATH);
        if(profileInfo == null){
            profileInfo = new MutableLiveData<>();
        }
        profile.setUpdatedAt(Timestamp.now().toDate().toString());

        reference.document(userId).set(profile).addOnSuccessListener(
                aVoid -> profileInfo.setValue(Resource.success(profile)));

        return profileInfo;
    }

    @Override
    public LiveData<Resource<ImageUpload>> imageUpload(FileUploadHelper file) {
        return fileUploadServices.uploadImage(file);
    }

    @Override
    public LiveData<AuthResource<Profile>> getLoggedInUserData(FirebaseUser user) {
        if(accountInfo == null){
            accountInfo = new MutableLiveData<>();
        }
        CollectionReference reference = firestore.collection(USER_PATH);
        String userId = user.getUid();

        reference.document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                Profile profile = documentSnapshot.toObject(Profile.class);
                accountInfo.setValue(AuthResource.authenticated(profile));
            }
        });
        return accountInfo;
    }

    @Override
    public LiveData<AuthResource<Profile>> getLiveDataUser() {
        accountInfo = new MutableLiveData<>();
        return getLoggedInUserData(Objects.requireNonNull(auth.getCurrentUser()));
    }

    @Override
    public boolean getLoggedInUser() {
        return auth.getCurrentUser() != null;
    }

    @Override
    public String getUid() {
        return Objects.requireNonNull(auth.getCurrentUser()).getUid();
    }

    @Override
    public void signOut() {
        user = null;
        auth.signOut();
    }
}
