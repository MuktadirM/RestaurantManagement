package com.example.restaurantmanagement.views.admin.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.databinding.SingleAdminTableViewBinding;
import com.example.restaurantmanagement.domain.models.core.TableBooking;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminTableRecyclerAdapter extends RecyclerView.Adapter<AdminTableRecyclerAdapter.ViewHolder> {
    private SingleAdminTableViewBinding binding;

    private final Context context;
    private List<TableBooking> bookingList;

    public AdminTableRecyclerAdapter(Context context, List<TableBooking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = SingleAdminTableViewBinding.inflate(inflater,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        TableBooking booking = bookingList.get(position);
        binding.setTable(booking);
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public void setBookingList(List<TableBooking> bookingList) {
        this.bookingList = bookingList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SingleAdminTableViewBinding binding;
        public ViewHolder(SingleAdminTableViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
