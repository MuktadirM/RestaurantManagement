package com.example.restaurantmanagement.domain.models.food;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.restaurantmanagement.domain.models.core.DomainObject;

import java.util.List;

public class Order extends DomainObject implements Parcelable {
    private List<Food> foods;
    private double total;

    public Order() {
    }

    public Order(String key, String createdAt, String createdBy, String updatedAt, List<Food> foods) {
        super(key, createdAt, createdBy, updatedAt);
        this.foods = foods;
    }

    protected Order(Parcel in) {
        foods = in.createTypedArrayList(Food.CREATOR);
        total = in.readDouble();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(foods);
        dest.writeDouble(total);
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public double getTotal() {
        double price = 0;
        if(foods.size()>0){
            for (int i=0; i<foods.size(); i++){
                price += foods.get(i).getPrice();
            }
        }
        total = price;
        return total;
    }
}
