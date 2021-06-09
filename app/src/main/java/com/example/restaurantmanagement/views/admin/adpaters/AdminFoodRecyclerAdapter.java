package com.example.restaurantmanagement.views.admin.adpaters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.databinding.SingleAdminMenuItemViewBinding;
import com.example.restaurantmanagement.views.models.FoodModel;

import org.jetbrains.annotations.NotNull;

public class AdminFoodRecyclerAdapter extends ListAdapter<FoodModel,AdminFoodRecyclerAdapter.ViewHolder> {
    MenuInterface itemOnClick;

    public AdminFoodRecyclerAdapter(MenuInterface itemOnClick) {
        super(FoodModel.itemCallback);
        this.itemOnClick = itemOnClick;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SingleAdminMenuItemViewBinding binding = SingleAdminMenuItemViewBinding.inflate(layoutInflater, parent, false);
        binding.setOnClickInterface(itemOnClick);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        FoodModel food = getItem(position);
        holder.binding.setFoodModel(food);
        holder.binding.executePendingBindings();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final SingleAdminMenuItemViewBinding binding;
        public ViewHolder(@NotNull SingleAdminMenuItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface MenuInterface {
        void onItemClick(FoodModel foodModel);
    }
}
