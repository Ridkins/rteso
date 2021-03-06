package com.example.ridkins.userplaceholder.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Rud on 8/5/17.
 */
public class UserModel implements Serializable {
    @SerializedName("website")
    private String website;
    @SerializedName("address")
    private Address address;
    @SerializedName("phone")
    private String phone;
    @SerializedName("name")
    private String name;
    @SerializedName("company")
    private Company company;
    @SerializedName("id")
    private Integer id;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return checkValue(website);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return checkValue(phone);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return checkValue(name);
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return checkValue(email);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return checkValue(username);
    }
    private String checkValue(String value){

        if(value == null)
            return "";
        return value;


    }
}