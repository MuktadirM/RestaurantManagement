package com.example.restaurantmanagement.repository.services;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.domain.models.food.Order;
import com.example.restaurantmanagement.domain.services.IOrderServices;
import com.example.restaurantmanagement.repository.dts.FromCartToFoodOrder;
import com.example.restaurantmanagement.utils.Resource;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static com.example.restaurantmanagement.utils.Constrains.USER_CART_PATH;
import static com.example.restaurantmanagement.utils.Constrains.USER_ORDER_PATH;

public class OrderServices implements IOrderServices {
    private MutableLiveData<Resource<List<Food>>> allCartItemsLiveData;
    private MutableLiveData<Resource<Boolean>> cartAddedLiveData;
    private MutableLiveData<Resource<Boolean>> cartItemRemoveLiveData;
    private MutableLiveData<Resource<Boolean>> orderConfirmLiveData;
    private MutableLiveData<Resource<Order>> orderLiveData;
    private MutableLiveData<Resource<List<Order>>> ordersLiveData;
    private MutableLiveData<Resource<List<Order>>> userOrdersLiveData;

    private List<Food> cartFoodList;

    @Inject
    FirebaseFirestore store;

    @Inject
    FirebaseAuth auth;

    @Inject
    public OrderServices() {
        cartFoodList = new ArrayList<>();
    }

    @Override
    public LiveData<Resource<List<Food>>> getAllCartItems() {
        CollectionReference reference = store.collection(USER_CART_PATH);
        String userId = auth.getUid();
        assert userId != null;

        if(allCartItemsLiveData == null){
            allCartItemsLiveData = new MutableLiveData<>();
        }
        allCartItemsLiveData.postValue(Resource.loading(null));

        reference.document(userId).collection(USER_CART_PATH).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    cartFoodList = new ArrayList<>();
                    for (DocumentSnapshot doc: queryDocumentSnapshots.getDocuments()) {
                        Food food = doc.toObject(Food.class);
                        cartFoodList.add(food);
                    }
                    allCartItemsLiveData.postValue(Resource.success(cartFoodList));
                }).addOnFailureListener((failure)->{
            allCartItemsLiveData.postValue(Resource.error("Fail to load",null));
        });

        return allCartItemsLiveData;
    }

    @Override
    public LiveData<Resource<Boolean>> addToCart(Food food) {
        if(cartAddedLiveData == null){
            cartAddedLiveData = new MutableLiveData<>();
        }

        CollectionReference reference = store.collection(USER_CART_PATH);
        String userId = auth.getUid();
        assert userId != null;
        String key = reference.document(userId).collection(USER_CART_PATH).document().getId();

        cartAddedLiveData.postValue(Resource.loading(null));
        String timeStamp = Timestamp.now().toDate().toString();

        food.setCreatedBy(key);
        food.setUpdatedAt(timeStamp);

        reference.document(userId).collection(USER_CART_PATH).document(key).set(food);

        cartFoodList.add(food);
        allCartItemsLiveData.postValue(Resource.success(cartFoodList));
        cartAddedLiveData.postValue(Resource.success(true));
        return cartAddedLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public LiveData<Resource<Boolean>> removeFromCart(Food food) {
        if(cartItemRemoveLiveData == null){
            cartItemRemoveLiveData = new MutableLiveData<>();
        }
        String userId = auth.getUid();
        assert userId != null;
        CollectionReference reference = store.collection(USER_CART_PATH).document(userId).collection(USER_CART_PATH);
        reference.document(food.getCreatedBy()).delete().addOnSuccessListener((success)->{
            cartFoodList.removeIf((fo -> fo.getCreatedBy().equals(food.getCreatedBy())));
            allCartItemsLiveData.postValue(Resource.success(cartFoodList));
            cartItemRemoveLiveData.postValue(Resource.success(true));
        }).addOnFailureListener((fail)->{
            cartItemRemoveLiveData.postValue(Resource.error("Failure",null));
        });
        return cartItemRemoveLiveData;
    }

    @Override
    public LiveData<Resource<List<Order>>> getOrderByUser() {
        CollectionReference reference = store.collection(USER_ORDER_PATH);
        String userId = auth.getUid();
        assert userId != null;

        if(userOrdersLiveData == null){
            userOrdersLiveData = new MutableLiveData<>();
        }

        userOrdersLiveData.postValue(Resource.loading(null));
        reference.whereEqualTo("createdBy",userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Order> orderList = new ArrayList<>();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        Order order = doc.toObject(Order.class);
                        orderList.add(order);
                    }
                    userOrdersLiveData.postValue(Resource.success(orderList));
                })
                .addOnFailureListener((fail)->{
                    userOrdersLiveData.postValue(Resource.error("Fail to get Data",null));
                });
        return userOrdersLiveData;
    }

    @Override
    public LiveData<Resource<Boolean>> orderConfirm() {
        if(orderConfirmLiveData == null){
            orderConfirmLiveData = new MutableLiveData<>();
        }
        CollectionReference reference = store.collection(USER_CART_PATH);
        String userId = auth.getUid();
        assert userId != null;
        CollectionReference orderRef = store.collection(USER_ORDER_PATH);
        String key = orderRef.document().getId();
        FromCartToFoodOrder toFoodUser = new FromCartToFoodOrder(key, userId);
        Order order = toFoodUser.toDomainObject(cartFoodList);

        orderRef.document(order.getKey()).set(order).addOnSuccessListener((success)->{
            for (Food food: order.getFoods()) {
                reference.document(userId).collection(USER_CART_PATH).document(food.getCreatedBy()).delete();
            }
            cartFoodList = new ArrayList<>();
            allCartItemsLiveData.postValue(Resource.success(cartFoodList));
            orderConfirmLiveData.postValue(Resource.success(true));
        }).addOnFailureListener((fail)->{
           orderConfirmLiveData.postValue(Resource.error("Fail to save",null));
        });

        return orderConfirmLiveData;
    }

    @Override
    public LiveData<Resource<List<Order>>> getAll() {
        if(ordersLiveData == null){
            ordersLiveData = new MutableLiveData<>();
        }
        ordersLiveData.postValue(Resource.loading(null));
        CollectionReference reference = store.collection(USER_ORDER_PATH);
        reference.get().addOnSuccessListener(queryDocumentSnapshots -> {
                List<Order> orderList = new ArrayList<>();
            for (DocumentSnapshot doc: queryDocumentSnapshots.getDocuments()) {
                    Order order = doc.toObject(Order.class);
                    orderList.add(order);
            }
            ordersLiveData.postValue(Resource.success(orderList));
        }).addOnFailureListener((fail)->{
            ordersLiveData.postValue(Resource.error("Could not load",null));
        });

        return ordersLiveData;
    }

    @Override
    public LiveData<Resource<Order>> addOne(Order entity) {
        if(orderLiveData == null){
            orderLiveData = new MutableLiveData<>();
        }

        orderLiveData.postValue(Resource.loading(null));

        CollectionReference reference = store.collection(USER_ORDER_PATH);
        String key = reference.getId();

        String userId = auth.getUid();
        assert userId != null;
        String timeStamp = Timestamp.now().toDate().toString();
        entity.setKey(key);
        entity.setCreatedAt(timeStamp);
        entity.setCreatedBy(userId);
        entity.setUpdatedAt(timeStamp);

        reference.document(key).set(entity).addOnSuccessListener((success)->{
            orderLiveData.postValue(Resource.success(entity));
        }).addOnFailureListener((fail)->{
            orderLiveData.postValue(Resource.error("Fail to save",null));
        });

    return orderLiveData;
    }

    @Override
    public LiveData<Resource<Order>> updateOne(Order entity) {
        return null;
    }

    @Override
    public LiveData<Resource<Order>> deleteOne(Order entity) {
        return null;
    }

    private DateTime dateTime(String date){
        try {
            return DateTime.parseFrom(ByteString.copyFromUtf8(date));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return DateTime.getDefaultInstance();
    }
}
