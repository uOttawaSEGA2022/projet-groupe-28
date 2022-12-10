package com.example.mealer28;

public class ClientDetails {

    public String fullname, email, password, address, creditcard, cvv;

    public ClientDetails(){}

    public ClientDetails(String fullname, String email, String password, String address, String creditcard, String cvv){
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.creditcard = creditcard;
        this.cvv = cvv;
    }


}
