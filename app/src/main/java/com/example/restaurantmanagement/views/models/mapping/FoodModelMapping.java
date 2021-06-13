package com.example.restaurantmanagement.views.models.mapping;

import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.utils.DomainToModel;
import com.example.restaurantmanagement.views.models.FoodModel;

import java.util.ArrayList;
import java.util.List;

public class FoodModelMapping implements DomainToModel<Food, FoodModel> {

    public FoodModelMapping() {
    }

    @Override
    public Food toDomain(FoodModel entity) {
        return new Food(
                entity.getKey(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getTitle(),
                entity.getPrice(),
                entity.getCalories(),
                entity.getImage(),
                entity.getDescription()
        );
    }

    @Override
    public FoodModel toModel(Food entity) {
        return new FoodModel(
                entity.getKey(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getTitle(),
                entity.getPrice(),
                entity.getCalories(),
                entity.getImage(),
                entity.getDescription()
        );
    }

    @Override
    public List<Food> toDomainList(List<FoodModel> entityList) {
        List<Food> foods = new ArrayList<>();
        for (FoodModel food: entityList) {
            foods.add(toDomain(food));
        }
        return foods;
    }

    @Override
    public List<FoodModel> toModelList(List<Food> entityList) {
        List<FoodModel> foods = new ArrayList<>();
        for (Food food: entityList) {
            foods.add(toModel(food));
        }
        return foods;
    }
}
