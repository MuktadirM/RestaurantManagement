package com.example.restaurantmanagement.domain.models.core;

public class TableBooking extends DomainObject{
    private Profile profile;
    private String table;
    private String time;

    public TableBooking() {
    }

    public TableBooking(String key, String createdAt, String createdBy, String updatedAt, Profile profile, String table, String time) {
        super(key, createdAt, createdBy, updatedAt);
        this.profile = profile;
        this.table = table;
        this.time = time;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
