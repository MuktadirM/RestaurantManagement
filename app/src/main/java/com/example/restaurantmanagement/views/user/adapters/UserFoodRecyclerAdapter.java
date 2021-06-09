package com.example.restaurantmanagement.views.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.databinding.SingleUserMenuItemViewBinding;
import com.example.restaurantmanagement.domain.models.food.Food;
import com.example.restaurantmanagement.utils.AddToCartClicked;
import com.example.restaurantmanagement.utils.ItemOnClick;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserFoodRecyclerAdapter extends RecyclerView.Adapter<UserFoodRecyclerAdapter.ViewHolder>{
    private SingleUserMenuItemViewBinding binding;
    private Context mContext;
    private final List<Food> foodList;
    private final ItemOnClick<Food> itemOnClick;
    private final AddToCartClicked addToCartClicked;

    public UserFoodRecyclerAdapter(Context mContext, List<Food> foodList, ItemOnClick<Food> itemOnClick,AddToCartClicked addToCartClicked) {
        this.mContext = mContext;
        this.foodList = foodList;
        this.itemOnClick = itemOnClick;
        this.addToCartClicked = addToCartClicked;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = SingleUserMenuItemViewBinding.inflate(inflater,parent,false);

        return new ViewHolder(binding,itemOnClick,addToCartClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.binding.title.setText(food.getTitle());
        holder.binding.price.setText("Price :"+food.getPrice()+" RM");
        holder.binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartClicked.ItemToCartClicked(food,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final SingleUserMenuItemViewBinding binding;
        private final ItemOnClick<Food> itemOnClick;
        private final AddToCartClicked addToCartClicked;

        public ViewHolder(@NotNull SingleUserMenuItemViewBinding binding, ItemOnClick<Food> itemOnClick, AddToCartClicked addToCartClicked) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemOnClick = itemOnClick;
            this.addToCartClicked = addToCartClicked;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemOnClick.ItemClicked(foodList.get(getAbsoluteAdapterPosition()),getAbsoluteAdapterPosition());
        }
    }
}
