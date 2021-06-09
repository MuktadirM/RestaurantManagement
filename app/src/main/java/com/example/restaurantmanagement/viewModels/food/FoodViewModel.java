package com.example.restaurantmanagement.viewModels.food;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.restaurantmanagement.views.models.FoodModel;

import javax.inject.Inject;

public class FoodViewModel extends ViewModel {
    private MutableLiveData<FoodModel> foodItem = new MutableLiveData<>();

    @Inject
    public FoodViewModel() {

    }

    public void setFoodItem(FoodModel food){
        foodItem.setValue(food);
    }

    public LiveData<FoodModel> getFoodItem(){
        return foodItem;
    }


}
