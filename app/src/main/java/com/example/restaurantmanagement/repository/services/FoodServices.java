package com.example.restaurantmanagement.repository.services;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.domain.services.IFoodServices;
import com.example.restaurantmanagement.utils.Resource;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static com.example.restaurantmanagement.utils.Constrains.FOOD_PATH;

public class FoodServices implements IFoodServices {
    private MutableLiveData<Resource<List<Food>>> liveDataFoods;
    private MutableLiveData<Resource<Food>> liveDataFood;

    private List<Food> foods;

    @Inject
    FirebaseFirestore store;

    @Inject
    FirebaseAuth auth;

    @Inject
    public FoodServices() {
    }

    @Override
    public LiveData<Resource<List<Food>>> getAll() {
        if(liveDataFoods == null){
            liveDataFoods = new MutableLiveData<>();
        }
        liveDataFoods.setValue(Resource.loading(null));
        CollectionReference reference = store.collection(FOOD_PATH);

        reference.get().addOnSuccessListener((queryDocumentSnapshots -> {
            if(queryDocumentSnapshots != null){
                foods = new ArrayList<>();
                for (DocumentSnapshot doc: queryDocumentSnapshots.getDocuments()) {
                    Food food = doc.toObject(Food.class);
                    foods.add(food);
                }
                liveDataFoods.setValue(Resource.success(foods));
            }
        }));

        return liveDataFoods;
    }

    @Override
    public LiveData<Resource<Food>> addOne(Food entity) {
        if(liveDataFood == null){
            liveDataFood = new MutableLiveData<>();
        }

        if(liveDataFoods == null){
            liveDataFoods = new MutableLiveData<>();
        }

        liveDataFood.setValue(Resource.loading(null));
        CollectionReference reference = store.collection(FOOD_PATH);

        String timeStamp = Timestamp.now().toDate().toString();
        String key = reference.document().getId();

        entity.setKey(key);
        entity.setCreatedAt(timeStamp);
        entity.setUpdatedAt(timeStamp);
        entity.setCreatedBy(getUserUid());

        reference.document(key).set(entity).addOnSuccessListener((success) ->{
            liveDataFood.setValue(Resource.success(entity));
            foods.add(entity);
            liveDataFoods.setValue(Resource.success(foods));
        }).addOnFailureListener((failure)->{
            liveDataFood.setValue(Resource.error(Objects.requireNonNull(failure.getMessage()),null));
        });

        return liveDataFood;
    }

    private String getUserUid() {
        if(auth.getUid() != null){
            return auth.getUid();
        }
        return "ZaUddGnXgiXkEjixahT3mO1fzMw1";
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public LiveData<Resource<Food>> updateOne(Food entity) {
        CollectionReference reference = store.collection(FOOD_PATH);
        if(liveDataFood == null){
            liveDataFood = new MutableLiveData<>();
        }
        String timeStamp = Timestamp.now().toDate().toString();
        liveDataFood.setValue(Resource.loading(null));
        foods.removeIf(food -> food.getKey().contentEquals(entity.getKey()));
        entity.setUpdatedAt(timeStamp);
        foods.add(entity);
        reference.document(entity.getKey()).set(entity).addOnSuccessListener((success)->{
            liveDataFoods.postValue(Resource.success(foods));
            liveDataFood.postValue(Resource.success(entity));
        });
        return liveDataFood;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public LiveData<Resource<Food>> deleteOne(Food entity) {
        CollectionReference reference = store.collection(FOOD_PATH);
        if(liveDataFood == null){
            liveDataFood = new MutableLiveData<>();
        }
        liveDataFood.setValue(Resource.loading(null));
        reference.document(entity.getKey()).delete().addOnSuccessListener((success)->{
            liveDataFood.postValue(Resource.success(entity));
            foods.removeIf(food -> food.getKey().contentEquals(entity.getKey()));
        });
        return liveDataFood;
    }

}
