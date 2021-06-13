package com.example.restaurantmanagement.domain.services;

import androidx.lifecycle.LiveData;

import com.example.restaurantmanagement.domain.models.core.TableBooking;
import com.example.restaurantmanagement.utils.Resource;

public interface ITableBookingServices extends IGenericDataServices<TableBooking>{
    public LiveData<Resource<TableBooking>> getSingleBookedTable();
}
