package com.example.ados;

public class ProfileDetails {

    private String Name;
    private String type;
    private String email;
    private String ConNum;


    public ProfileDetails(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConNum() {
        return ConNum;
    }

    public void setConNum(String conNum) {
        ConNum = conNum;
    }
}
