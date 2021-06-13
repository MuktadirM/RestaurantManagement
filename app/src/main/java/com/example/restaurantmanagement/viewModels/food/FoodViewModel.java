package com.example.restaurantmanagement.viewModels.food;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.restaurantmanagement.domain.models.core.ImageUpload;
import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.domain.services.IFileUploadServices;
import com.example.restaurantmanagement.domain.services.IFoodServices;
import com.example.restaurantmanagement.domain.utils.FileUploadHelper;
import com.example.restaurantmanagement.utils.Resource;
import com.example.restaurantmanagement.views.models.FoodModel;

import java.util.List;

import javax.inject.Inject;

import static com.example.restaurantmanagement.utils.Constrains.FOOD_PATH;

public class FoodViewModel extends ViewModel {
    private final IFileUploadServices fileUploadServices;
    private final IFoodServices foodServices;
    private final MutableLiveData<Boolean> isSuccessFull = new MutableLiveData<>();
    private final MutableLiveData<FoodModel> foodItem = new MutableLiveData<>();

    @Inject
    public FoodViewModel(IFileUploadServices fileUploadServices, IFoodServices foodServices) {
        this.fileUploadServices = fileUploadServices;
        this.foodServices = foodServices;
    }

    public LiveData<Resource<ImageUpload>> uploadFoodImage(Uri uri,String fileExt){
       FileUploadHelper uploadHelper = new FileUploadHelper(uri,fileExt,FOOD_PATH,true);
       return fileUploadServices.uploadImage(uploadHelper);
    }

    public LiveData<Resource<List<Food>>> getAllMenus(){
        return foodServices.getAll();
    }
    public LiveData<Resource<Food>> addNewMenu(Food food){
        return foodServices.addOne(food);
    }

    public void setFoodItem(FoodModel food){
        foodItem.setValue(food);
    }

    public LiveData<FoodModel> getFoodItem(){
        return foodItem;
    }

}
