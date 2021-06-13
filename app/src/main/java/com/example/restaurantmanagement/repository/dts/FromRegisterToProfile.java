package com.example.restaurantmanagement.repository.dts;

import com.example.restaurantmanagement.domain.models.auth.Register;
import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.utils.DataTransferObject;
import com.google.firebase.Timestamp;

public class FromRegisterToProfile implements DataTransferObject<Register, Profile> {
    private final String key;

    public FromRegisterToProfile(String key) {
        this.key = key;
    }

    @Override
    public Profile toDomain(Register entity) {
        String timestamp = Timestamp.now().toDate().toString();
        return new Profile(
                key,
                timestamp,
                key,
                timestamp,
                entity.getName(),
                entity.getIc(),
                entity.getEmail(),
                entity.getType(),
                entity.getSex(),
                "Yes",
                entity.getPhone(),
                entity.getAddress(),
                "https://firebasestorage.googleapis.com/v0/b/restaurant-management-99ad9.appspot.com/o/users%2F0c3b3adb1a7530892e55ef36d3be6cb8.png?alt=media&token=277a8955-3326-4fcb-bf88-2b125020854e"
        );
    }

    @Override
    public Register toModel(Profile entity) {
        return null;
    }
}
