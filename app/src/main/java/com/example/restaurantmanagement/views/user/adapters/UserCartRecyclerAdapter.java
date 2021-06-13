package com.example.restaurantmanagement.views.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.databinding.SingleUserCartItemViewBinding;
import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.utils.ItemOnClick;
import com.example.restaurantmanagement.views.models.FoodModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserCartRecyclerAdapter extends RecyclerView.Adapter<UserCartRecyclerAdapter.ViewHolder>{
    private SingleUserCartItemViewBinding binding;
    private Context mContext;
    private List<FoodModel> foodList;
    private final ItemOnClick<FoodModel> itemOnClick;

    public UserCartRecyclerAdapter(Context mContext, List<FoodModel> foodList, ItemOnClick<FoodModel> itemOnClick) {
        this.mContext = mContext;
        this.foodList = foodList;
        this.itemOnClick = itemOnClick;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = SingleUserCartItemViewBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding,itemOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        FoodModel food = foodList.get(position);
        binding.setFood(food);
        holder.binding.removeFromCart.setOnClickListener(v -> holder.itemOnClick.ItemClicked(food,position));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void setFoodList(List<FoodModel> foodList){
        this.foodList = foodList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SingleUserCartItemViewBinding binding;
        private final ItemOnClick<FoodModel> itemOnClick;

        public ViewHolder(@NotNull SingleUserCartItemViewBinding binding, ItemOnClick<FoodModel> itemOnClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemOnClick = itemOnClick;
        }
    }
}
