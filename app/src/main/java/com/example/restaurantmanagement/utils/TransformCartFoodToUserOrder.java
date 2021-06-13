package com.example.restaurantmanagement.utils;

import java.util.List;

public interface TransformCartFoodToUserOrder<T,Out> {
    public Out toDomainObject(List<T> entities);
    public List<Out> toDomainObjectList(List<T> entities);
}
