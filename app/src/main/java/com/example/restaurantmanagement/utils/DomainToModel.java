package com.example.restaurantmanagement.utils;

import java.util.List;

public interface DomainToModel <Domain,Model>{
    public Domain toDomain(Model entity);
    public Model toModel(Domain entity);
    public List<Domain> toDomainList(List<Model> entityList);
    public List<Model> toModelList(List<Domain> entityList);
}
