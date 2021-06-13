package com.example.restaurantmanagement.viewModels.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.domain.models.food.Order;
import com.example.restaurantmanagement.domain.services.IFoodServices;
import com.example.restaurantmanagement.domain.services.IOrderServices;
import com.example.restaurantmanagement.utils.Resource;

import java.util.List;

import javax.inject.Inject;

public class OrderViewModel extends ViewModel {
    private final IFoodServices foodServices;
    private final IOrderServices orderServices;

    @Inject
    public OrderViewModel(IFoodServices foodServices, IOrderServices orderServices) {
        this.foodServices = foodServices;
        this.orderServices = orderServices;
    }

    public LiveData<Resource<List<Food>>> getAllMenus(){
        return foodServices.getAll();
    }

    public LiveData<Resource<List<Food>>> getAllCartItems(){
        return orderServices.getAllCartItems();
    }

    public LiveData<Resource<Boolean>> orderConfirm(){
        return orderServices.orderConfirm();
    }

    public LiveData<Resource<List<Order>>> getAllOrders(){
        return orderServices.getAll();
    }
    public LiveData<Resource<List<Order>>> getAllUserOrders(){
        return orderServices.getOrderByUser();
    }

    public LiveData<Resource<Boolean>> addToCart(Food food){
        return orderServices.addToCart(food);
    }
    public LiveData<Resource<Boolean>> removeFromCart(Food food){
        return orderServices.removeFromCart(food);
    }
}
