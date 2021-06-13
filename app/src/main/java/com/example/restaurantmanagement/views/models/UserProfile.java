package com.example.restaurantmanagement.views.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.domain.utils.UserType;

public class UserProfile extends Profile {

    public UserProfile() {
    }

    public UserProfile(String key, String createdAt, String createdBy, String updatedAt, String name, String ic, String email, UserType type, String sex, String verified, String phone, String address, String image) {
        super(key, createdAt, createdBy, updatedAt, name, ic, email, type, sex, verified, phone, address, image);
    }

    @BindingAdapter("android:userImage")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView)
                .load(imageUrl)
                .fitCenter()
                .into(imageView);
    }

}
