package com.example.restaurantmanagement.repository.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantmanagement.domain.models.core.Profile;
import com.example.restaurantmanagement.domain.models.core.TableBooking;
import com.example.restaurantmanagement.domain.services.ITableBookingServices;
import com.example.restaurantmanagement.utils.Resource;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.restaurantmanagement.utils.Constrains.USER_PATH;
import static com.example.restaurantmanagement.utils.Constrains.USER_TABLE_BOOKINGS;

public class TableBookingServices implements ITableBookingServices {
    private MutableLiveData<Resource<List<TableBooking>>> liveDataTables;
    private MutableLiveData<Resource<TableBooking>> liveDataTable;

    @Inject
    FirebaseFirestore store;

    @Inject
    FirebaseAuth auth;

    @Inject
    public TableBookingServices() {
    }

    @Override
    public LiveData<Resource<List<TableBooking>>> getAll() {
        CollectionReference reference = store.collection(USER_TABLE_BOOKINGS);
        if(liveDataTables == null){
            liveDataTables = new MutableLiveData<>();
        }
        liveDataTables.postValue(Resource.loading(null));

        reference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<TableBooking> list = new ArrayList<>();
            if(queryDocumentSnapshots != null){
                for (DocumentSnapshot doc: queryDocumentSnapshots.getDocuments()) {
                    TableBooking tableBooking = doc.toObject(TableBooking.class);
                    list.add(tableBooking);
                }
                liveDataTables.postValue(Resource.success(list));
            }
        }).addOnFailureListener((failure)->{
            liveDataTables.postValue(Resource.error("Fail to load",null));
        });

        return liveDataTables;
    }

    @Override
    public LiveData<Resource<TableBooking>> addOne(TableBooking entity) {
        CollectionReference reference = store.collection(USER_TABLE_BOOKINGS);
        CollectionReference userRef = store.collection(USER_PATH);
        if(liveDataTable == null){
            liveDataTable = new MutableLiveData<>();
        }
        String timestamp = Timestamp.now().toDate().toString();
        liveDataTable.postValue(Resource.loading(null));
        String userId = auth.getUid();
        assert userId != null;
        entity.setCreatedAt(timestamp);
        entity.setUpdatedAt(timestamp);
        entity.setCreatedBy(userId);
        entity.setKey(userId);
        userRef.document(userId).get().addOnSuccessListener((documentSnapshot -> {
            if(documentSnapshot.exists()){
                Profile profile = documentSnapshot.toObject(Profile.class);
                entity.setProfile(profile);
                reference.document(userId).set(entity);

                liveDataTable.postValue(Resource.success(entity));
            }
        })).addOnFailureListener((failure)->{
           liveDataTable.postValue(Resource.error("Not a valid user",null));
        });

        return liveDataTable;
    }

    @Override
    public LiveData<Resource<TableBooking>> updateOne(TableBooking entity) {
        return null;
    }

    @Override
    public LiveData<Resource<TableBooking>> deleteOne(TableBooking entity) {
        return null;
    }

    @Override
    public LiveData<Resource<TableBooking>> getSingleBookedTable() {
        CollectionReference reference = store.collection(USER_TABLE_BOOKINGS);
        if(liveDataTable == null){
            liveDataTable = new MutableLiveData<>();
        }
        liveDataTable.postValue(Resource.loading(null));
        String userId = auth.getUid();
        assert userId != null;

        reference.document(userId).get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                TableBooking tableBooking = documentSnapshot.toObject(TableBooking.class);
                liveDataTable.postValue(Resource.success(tableBooking));
            }
        }).addOnFailureListener((failure)->{
            liveDataTable.postValue(Resource.error("Fail to load",null));
        });
        return liveDataTable;
    }
}
