package com.example.restaurantmanagement.utils;

import com.example.restaurantmanagement.domain.models.food.Food;

import java.util.ArrayList;
import java.util.List;

public class Constrains {
    public static String FOOD_DETAILS="FoodDetails";
    public static String FOOD_EDIT ="FoodDetailsEdit";
    public static String EDIT_PROFILE="EditProfile";
    public static String MENU_ITEM_DETAILS="food";
    public static String ORDER_DETAILS = "OrderDetails";

    public static String FOOD_PATH = "foods";
    public static String USER_PATH = "users";
    public static String USER_ORDER_PATH = "orders";
    public static String USER_CART_PATH = "carts";
    public static String USER_TABLE_BOOKINGS = "tables";


    public static List<Food> getFoodList(){
        ArrayList<Food> list = new ArrayList<>();
        Food food = new Food();
        food.setTitle("Nasi goreng ayam");
        food.setPrice(5.6);
        food.setCalories(455);
        list.add(food);
        list.add(food);
        list.add(food);
        list.add(food);
        list.add(food);
        list.add(food);
        list.add(food);
        return list;
    }
}
