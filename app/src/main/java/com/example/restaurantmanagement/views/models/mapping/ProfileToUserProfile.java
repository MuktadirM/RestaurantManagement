package com.example.restaurantmanagement.views.models.mapping;

import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.utils.DomainToModel;
import com.example.restaurantmanagement.views.models.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class ProfileToUserProfile implements DomainToModel<UserProfile, Profile> {

    @Override
    public UserProfile toDomain(Profile entity) {
        return new UserProfile(
                entity.getKey(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getName(),
                entity.getIc(),
                entity.getEmail(),
                entity.getType(),
                entity.getSex(),
                entity.getVerified(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getImage()
        );
    }

    @Override
    public Profile toModel(UserProfile entity) {
        return new Profile(
                entity.getKey(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedAt(),
                entity.getName(),
                entity.getIc(),
                entity.getEmail(),
                entity.getType(),
                entity.getSex(),
                entity.getVerified(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getImage()
        );
    }

    @Override
    public List<UserProfile> toDomainList(List<Profile> entityList) {
        List<UserProfile> userProfileList = new ArrayList<>();
        for (Profile profile: entityList) {
            userProfileList.add(toDomain(profile));
        }
        return userProfileList;
    }

    @Override
    public List<Profile> toModelList(List<UserProfile> entityList) {
        List<Profile> userProfileList = new ArrayList<>();
        for (UserProfile profile: entityList) {
            userProfileList.add(toDomain(profile));
        }
        return userProfileList;
    }
}
