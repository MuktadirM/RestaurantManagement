package com.example.restaurantmanagement.repository.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantmanagement.domain.models.core.ImageUpload;
import com.example.restaurantmanagement.domain.services.IFileUploadServices;
import com.example.restaurantmanagement.domain.utils.FileUploadHelper;
import com.example.restaurantmanagement.utils.Resource;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Inject;

public class FileUploadServices implements IFileUploadServices {
    private MutableLiveData<Resource<ImageUpload>> liveDataImage;

    @Inject
    FirebaseFirestore store;

    @Inject
    FirebaseStorage storage;

    @Inject
    FirebaseAuth auth;

    @Inject
    public FileUploadServices() {
    }

    @Override
    public LiveData<Resource<ImageUpload>> uploadImage(FileUploadHelper file) {
        if(liveDataImage ==null){
            liveDataImage = new MutableLiveData<>();
        }
        liveDataImage.setValue(Resource.loading(null));
        StorageReference reference = storage.getReference(createPath(file));
        reference.putFile(file.getUri())
                .addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            ImageUpload imageUpload = new ImageUpload(
                                    true,
                                    uri.toString()
                            );
                            liveDataImage.setValue(Resource.success(imageUpload));
                        })).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            // ...
            liveDataImage.setValue(Resource.error("Sorry can't upload the image"+exception.getMessage(),null));

        });
        return liveDataImage;
    }

    private String getUserUid() {
        if(auth.getUid() != null){
            return auth.getUid();
        }
        return "ZaUddGnXgiXkEjixahT3mO1fzMw1";
    }

    private String createPath(FileUploadHelper file){
        String path = "";
        String extra = String.valueOf(Timestamp.now().getSeconds()).concat(".").concat(file.getFileExt());
        if(file.isRelated()){
            String fileName = getUserUid().concat("_").concat(extra);
            path = file.getPath().concat("/").concat(getUserUid()).concat("/").concat(fileName);
        }
        else {
            path = file.getPath().concat("/").concat(extra);
        }
        return path;
    }


}
