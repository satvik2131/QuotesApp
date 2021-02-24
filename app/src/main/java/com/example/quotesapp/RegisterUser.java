package com.example.quotesapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity {
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private EditText name , email, password ;
    private TextView showError;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    public void submitForm(View view) {
        name = findViewById(R.id.enter_name);
        email = findViewById(R.id.enter_email);
        password = findViewById(R.id.enter_password);
        showError = findViewById(R.id.showError);
        String key = ref.push().getKey();
        SessionManager sm = new SessionManager(RegisterUser.this);

        User user = new User(name.getText().toString(),email.getText().toString(),password.getText().toString(),key);
        //Validation for checking if the field is empty or not.
        try{
            if(user.email.isEmpty() || user.name.isEmpty() || user.password.isEmpty()){
                showError.setVisibility(view.VISIBLE);
                throw new MyExceptions("Please Enter Your Details");
            }

            //Validation for a correct email address
            //Regex for a validate email
            String Emailregex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
            String PassRegex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})";

            //Email Pattern Compilation
            Pattern m = Pattern.compile(Emailregex);
            Matcher emailResult = m.matcher(email.getText());

            //Password Pattern Compilation
            Pattern mp = Pattern.compile(PassRegex);
            Matcher passResult = m.matcher(password.getText());

            //Email and Password Validation Both Condition
            if(emailResult.matches() != true && passResult.matches() != true){
                throw new MyExceptions("Email and Password is in invalid format");
            }

            //Email Validation
            if(emailResult.matches() != true){ throw new MyExceptions("Email not in valid format");}

            //Password Validation
            if(passResult.matches() != true ){throw new MyExceptions("1.Minimum eight and maximum 10 characters\n 2.At least one uppercase letter\n one lowercase letter\n 3.One number and one special character");}

            ref.child("users").child(key).setValue(user);
            sm.createSession(key);
            Intent toHomepage = new Intent(this,Homepage.class);
            startActivity(toHomepage);

        }catch(MyExceptions message){
            showError.setVisibility(view.VISIBLE);
            showError.setText(message.getMessage());
        }

    }
}
