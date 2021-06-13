package com.example.restaurantmanagement.domain.services;

import androidx.lifecycle.LiveData;

import com.example.restaurantmanagement.domain.models.core.ImageUpload;
import com.example.restaurantmanagement.domain.utils.FileUploadHelper;
import com.example.restaurantmanagement.utils.Resource;

public interface IFileUploadServices {
    public LiveData<Resource<ImageUpload>> uploadImage(FileUploadHelper file);
}
