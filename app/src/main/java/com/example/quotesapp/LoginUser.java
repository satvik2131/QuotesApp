package com.example.quotesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginUser extends AppCompatActivity {
    private EditText userEmail;
    private EditText userPassword;
    private TextView loginError;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void loginUser(View view) {
        loginError = findViewById(R.id.LoginError);

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);

        userEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);
        userPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);

        Query query = ref.orderByChild("email").equalTo(userEmail.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            private String password;
            private SharedPreferences prefs;
            private SessionManager sessionManager = new SessionManager(LoginUser.this);

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    if(snapshot.exists()){
                        for(DataSnapshot user : snapshot.getChildren()){
                            //Database Password
                            password = user.child("password").getValue(String.class);

                            if(userPassword.getText().toString().equals(password)){
                                //When All Validation Passes

                                //Session
                                sessionManager.createSession(user.getKey());


                                Intent moveToHomepage = new Intent(LoginUser.this,Homepage.class);
                                startActivity(moveToHomepage);
                            }else{
                                throw new MyExceptions("Invalid Password");
                            }
                        }
                    }else{
                        throw new MyExceptions("No user found");
                    }
                }catch(MyExceptions error){
                    loginError.setText(error.getMessage());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginUser.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
