package com.example.restaurantmanagement.repository.dts;

import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.domain.models.food.Order;
import com.example.restaurantmanagement.utils.TransformCartFoodToUserOrder;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class FromCartToFoodOrder implements TransformCartFoodToUserOrder<Food, Order> {
    private final String key;
    private final String userId;
    public FromCartToFoodOrder(String key, String userId) {
        this.key = key;
        this.userId = userId;
    }

    @Override
    public Order toDomainObject(List<Food> entities) {
        Order order;
        String timeStamp = Timestamp.now().toDate().toString();
        order = new Order(
                key,
                timeStamp,
                userId,
                timeStamp,
                entities
        );
        return order;
    }

    @Override
    public List<Order> toDomainObjectList(List<Food> entities) {
        return null;
    }
}
