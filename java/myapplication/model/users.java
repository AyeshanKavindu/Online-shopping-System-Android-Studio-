package com.example.myapplication.model;

public class users
{
    private String name,email,password,address,number;


    public users()
    {

    }

    public users(String name, String email, String password, String address, String number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.number = number;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
