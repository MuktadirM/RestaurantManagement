package com.example.restaurantmanagement.domain.models.food;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.restaurantmanagement.domain.models.core.DomainObject;

public class Food extends DomainObject implements Parcelable {
    private String title;
    private double price;
    private double calories;
    private String description;

    public Food() {
    }

    public Food(String key, String createdAt, String createdBy, String updatedAt, String title, double price, double calories, String description) {
        super(key, createdAt, createdBy, updatedAt);
        this.title = title;
        this.price = price;
        this.calories = calories;
        this.description = description;
    }

    protected Food(Parcel in) {
        title = in.readString();
        price = in.readDouble();
        calories = in.readDouble();
        description = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeDouble(price);
        dest.writeDouble(calories);
        dest.writeString(description);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
