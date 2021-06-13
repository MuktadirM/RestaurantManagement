package com.example.restaurantmanagement.views.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.domain.models.food.Food;

import java.util.List;

public class CheckoutArrayAdapter extends ArrayAdapter<Food> {
    public CheckoutArrayAdapter(@NonNull Context context, List<Food> list) {
        super(context, 0,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_user_checkout_item_view,
                    parent,false
            );
        }
        TextView title = convertView.findViewById(R.id.title);
        TextView price = convertView.findViewById(R.id.price);
        Food food = getItem(position);
        if(food != null){
            title.setText(food.getTitle());
            price.setText(food.getPrice()+"RM");
        }
        return convertView;
    }

}
