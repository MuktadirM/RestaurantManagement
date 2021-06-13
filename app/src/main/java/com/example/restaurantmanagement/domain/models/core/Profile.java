package com.example.restaurantmanagement.domain.models.core;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.restaurantmanagement.domain.utils.UserType;

import java.util.Objects;

public class Profile extends DomainObject implements Parcelable {
    private String name;
    private String ic;
    private String email;
    private UserType type = UserType.User;
    private String sex;
    private String verified ="Yes";
    private String phone;
    private String address;
    private String image;

    public Profile() {
    }

    public Profile(String key, String createdAt, String createdBy, String updatedAt, String name, String ic, String email, UserType type, String sex, String verified, String phone, String address, String image) {
        super(key, createdAt, createdBy, updatedAt);
        this.name = name;
        this.ic = ic;
        this.email = email;
        this.type = type;
        this.sex = sex;
        this.verified = verified;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    protected Profile(Parcel in) {
        name = in.readString();
        ic = in.readString();
        email = in.readString();
        sex = in.readString();
        verified = in.readString();
        phone = in.readString();
        address = in.readString();
        image = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Profile profile = (Profile) o;
        return name.equals(profile.name) &&
                ic.equals(profile.ic) &&
                email.equals(profile.email) &&
                type == profile.type &&
                sex.equals(profile.sex) &&
                verified.equals(profile.verified) &&
                phone.equals(profile.phone) &&
                address.equals(profile.address) &&
                image.equals(profile.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, ic, email, type, sex, verified, phone, address, image);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(ic);
        dest.writeString(email);
        dest.writeString(sex);
        dest.writeString(verified);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(image);
    }

}
