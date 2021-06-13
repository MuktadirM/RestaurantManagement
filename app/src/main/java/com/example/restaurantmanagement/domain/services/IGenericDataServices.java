package com.example.restaurantmanagement.domain.services;

import androidx.lifecycle.LiveData;

import com.example.restaurantmanagement.utils.Resource;

import java.util.List;

public interface IGenericDataServices<T> {
    LiveData<Resource<List<T>>> getAll();
    LiveData<Resource<T>> addOne(T entity);
    LiveData<Resource<T>> updateOne(T entity);
    LiveData<Resource<T>> deleteOne(T entity);
}
