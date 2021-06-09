package com.example.restaurantmanagement.domain.models.auth;


import com.example.restaurantmanagement.domain.utils.UserType;

public class Register extends Login {
    private  String name;
    private  String phone;
    private UserType type = UserType.User;
    private  String address;
    private  String image;
    private final String ic;
    private final String sex;
    private final String confirmPassword;

    public Register(String email, String password, String name, String ic, String sex, String confirmPassword) {
        super(email, password);
        this.name = name;
        this.ic = ic;
        this.sex = sex;
        this.confirmPassword = confirmPassword;
    }

    public Register(String email, String password, String name, String phone, String address, String image, String ic, String sex, String confirmPassword) {
        super(email, password);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.image = image;
        this.ic = ic;
        this.sex = sex;
        this.confirmPassword = confirmPassword;
    }

    public Register(String email, String password, String name, String phone, UserType type, String address, String image, String ic, String sex, String confirmPassword) {
        super(email, password);
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.address = address;
        this.image = image;
        this.ic = ic;
        this.sex = sex;
        this.confirmPassword = confirmPassword;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getName() {
        return name;
    }

    public String getIc() {
        return ic;
    }

    public String getSex() {
        return sex;
    }
}
