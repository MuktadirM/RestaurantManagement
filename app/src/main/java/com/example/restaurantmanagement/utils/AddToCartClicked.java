package com.example.restaurantmanagement.utils;

import com.example.restaurantmanagement.domain.models.food.Food;

public interface AddToCartClicked {
    public void ItemToCartClicked(Food item, int position);
}
