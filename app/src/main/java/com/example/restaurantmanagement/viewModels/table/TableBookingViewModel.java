package com.example.restaurantmanagement.viewModels.table;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.restaurantmanagement.domain.models.core.TableBooking;
import com.example.restaurantmanagement.domain.services.ITableBookingServices;
import com.example.restaurantmanagement.utils.Resource;

import java.util.List;

import javax.inject.Inject;

public class TableBookingViewModel extends ViewModel {
    private final ITableBookingServices tableBookingServices;

    @Inject
    public TableBookingViewModel(ITableBookingServices tableBookingServices) {
        this.tableBookingServices = tableBookingServices;
    }

    public LiveData<Resource<List<TableBooking>>> getAll(){
        return tableBookingServices.getAll();
    }

    public LiveData<Resource<TableBooking>> addTableBooking(TableBooking booking){
        return tableBookingServices.addOne(booking);
    }

    public LiveData<Resource<TableBooking>> getBookedTable(){
        return tableBookingServices.getSingleBookedTable();
    }
}
