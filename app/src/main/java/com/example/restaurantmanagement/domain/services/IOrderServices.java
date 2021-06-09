package com.example.restaurantmanagement.domain.services;

import androidx.lifecycle.LiveData;

import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.domain.models.food.Order;
import com.example.restaurantmanagement.utils.Resource;

import java.util.List;

public interface IOrderServices extends IGenericDataServices<Order>{
    public LiveData<Resource<List<Food>>> getAllCartItems();
    public LiveData<Resource<Boolean>> addToCart(Food food);
    public LiveData<Resource<Boolean>> removeFromCart(Food food);
}
