package com.example.ados.Model;

public class Users
{
    private String name, email ,password,PhoneNumber,type;

    public Users(){

    }

    public Users(String name, String email, String password, String PhoneNumber,String type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.PhoneNumber = PhoneNumber;
        this.type = type;
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

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) { this.PhoneNumber = PhoneNumber;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}


}
