package com.example.vastu;

//package com.example.vastu.ui.core;

public class Users {
    public String firstName;
    public String lastName;
    public String emailID;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public Users(){
    }

    public Users(String firstName, String lastName, String emailID){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailID = emailID;
    }
}

