package com.example.restaurantmanagement.utils;

public interface DataTransferObject <Model,Domain>{
    public Domain toDomain(Model entity);
    public Model toModel(Domain entity);
}
