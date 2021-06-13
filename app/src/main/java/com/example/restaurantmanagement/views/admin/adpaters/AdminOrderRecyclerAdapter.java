package com.example.restaurantmanagement.views.admin.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.databinding.SingleAdminOrderViewBinding;
import com.example.restaurantmanagement.domain.models.food.Order;
import com.example.restaurantmanagement.views.user.adapters.CheckoutArrayAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminOrderRecyclerAdapter extends RecyclerView.Adapter<AdminOrderRecyclerAdapter.ViewHolder>{
    private SingleAdminOrderViewBinding binding;
    private final Context context;
    private List<Order> orderList;
    private final Boolean isShowPrice;

    public AdminOrderRecyclerAdapter(Context context, List<Order> orderList,Boolean isShowPrice) {
        this.context = context;
        this.orderList = orderList;
        this.isShowPrice = isShowPrice;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = SingleAdminOrderViewBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
            Order order = orderList.get(position);
            holder.binding.setOrder(order);
            if(isShowPrice){
                holder.binding.priceTotal.setVisibility(View.VISIBLE);
            }
            CheckoutArrayAdapter arrayAdapter = new CheckoutArrayAdapter(context,order.getFoods());
            holder.binding.orderItemListView.setAdapter(arrayAdapter);
            if(arrayAdapter.getCount() > 0){
                View item = arrayAdapter.getView(0, null, holder.binding.orderItemListView);
                item.measure(0, 0);
                holder.binding.orderItemListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,80 * arrayAdapter.getCount()));
            }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SingleAdminOrderViewBinding binding;
        public ViewHolder(@NotNull SingleAdminOrderViewBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
