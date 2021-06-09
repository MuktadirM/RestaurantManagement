package com.example.restaurantmanagement.views.models;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.example.restaurantmanagement.domain.models.food.Food;

public class FoodModel extends Food {

    public FoodModel() {
    }

    public FoodModel(String key, String createdAt, String createdBy, String updatedAt, String title, double price, double calories,String image, String description) {
        super(key, createdAt, createdBy, updatedAt, title, price, calories,image, description);
    }

    public static DiffUtil.ItemCallback<FoodModel> itemCallback = new DiffUtil.ItemCallback<FoodModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull FoodModel oldItem, @NonNull FoodModel newItem) {
            return oldItem.getKey().equals(newItem.getKey());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FoodModel oldItem, @NonNull FoodModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    @BindingAdapter("android:foodImage")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView)
                .load(imageUrl)
                .fitCenter()
                .into(imageView);
    }

}
