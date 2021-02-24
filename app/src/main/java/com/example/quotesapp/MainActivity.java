package com.example.quotesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();

        SessionManager sessionManager = new SessionManager(MainActivity.this);
        if((sessionManager.checkLogin())==true){
            Intent goToHomepage = new Intent(this,Homepage.class);
            startActivity(goToHomepage);
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this,LoginUser.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this,RegisterUser.class);
        startActivity(intent);
    }
}