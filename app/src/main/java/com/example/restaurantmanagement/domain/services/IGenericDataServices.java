package com.example.restaurantmanagement.domain.services;

import androidx.lifecycle.LiveData;

import com.example.restaurantmanagement.utils.Resource;

import java.util.List;

public interface IGenericDataServices<T> {
    Resource<LiveData<List<T>>> getAll();
    Resource<LiveData<T>> addOne(T entity);
    Resource<LiveData<T>> updateOne(T entity);
    Resource<LiveData<T>> deleteOne(T entity);
}
