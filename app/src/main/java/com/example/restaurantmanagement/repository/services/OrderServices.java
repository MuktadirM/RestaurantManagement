package com.example.restaurantmanagement.repository.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.domain.models.food.Order;
import com.example.restaurantmanagement.domain.services.IOrderServices;
import com.example.restaurantmanagement.utils.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OrderServices implements IOrderServices {
    private MutableLiveData<Resource<List<Food>>> getAllCartItemsLiveData;
    private MutableLiveData<Resource<Boolean>> cartAddedLiveData;
    private MutableLiveData<Resource<Boolean>> cartItemRemoveLiveData;
    private final List<Food> cartFoodList;

    @Inject
    public OrderServices() {
        cartFoodList = new ArrayList<>();
    }

    @Override
    public Resource<LiveData<List<Order>>> getAll() {
        return null;
    }

    @Override
    public Resource<LiveData<Order>> addOne(Order entity) {
        return null;
    }

    @Override
    public Resource<LiveData<Order>> updateOne(Order entity) {
        return null;
    }

    @Override
    public Resource<LiveData<Order>> deleteOne(Order entity) {
        return null;
    }

    @Override
    public LiveData<Resource<List<Food>>> getAllCartItems() {
        if(getAllCartItemsLiveData == null){
            getAllCartItemsLiveData = new MutableLiveData<>();
        }
        getAllCartItemsLiveData.postValue(Resource.loading(null));
        getAllCartItemsLiveData.postValue(Resource.success(cartFoodList));
        return getAllCartItemsLiveData;
    }

    @Override
    public LiveData<Resource<Boolean>> addToCart(Food food) {
        if(cartAddedLiveData == null){
            cartAddedLiveData = new MutableLiveData<>();
        }
        cartAddedLiveData.postValue(Resource.loading(null));
        cartFoodList.add(food);
        getAllCartItemsLiveData.postValue(Resource.success(cartFoodList));
        cartAddedLiveData.postValue(Resource.success(true));
        return cartAddedLiveData;
    }

    @Override
    public LiveData<Resource<Boolean>> removeFromCart(Food food) {
        if(cartItemRemoveLiveData == null){
            cartItemRemoveLiveData = new MutableLiveData<>();
        }
        cartFoodList.remove(food);
        getAllCartItemsLiveData.postValue(Resource.success(cartFoodList));
        cartItemRemoveLiveData.postValue(Resource.success(true));
        return cartItemRemoveLiveData;
    }
}
