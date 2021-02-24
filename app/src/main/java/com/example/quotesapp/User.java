package com.example.quotesapp;

import android.text.Editable;

public class User {
    public String name , email , password , id;
    public User(String name , String email, String password, String id){
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public User(Editable text, Editable text1, Editable text2) {
    }
}
