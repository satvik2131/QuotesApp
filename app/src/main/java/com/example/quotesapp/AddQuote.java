package com.example.quotesapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddQuote extends AppCompatActivity {
    private EditText title;
    private EditText description;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private long count;
    private String id;

    @SuppressLint("WrongConstant")
    SessionManager sm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addquote);

        //Initializations
        sm = new SessionManager(AddQuote.this);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
    }



    public void addQuote(View view) {
        id = sm.getID();

        ref.child("quotes").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Quote quote = new Quote(title.getText().toString(),description.getText().toString(),id);


                count = (snapshot.getChildrenCount() + 1);

                ref.child("quotes").child(id).child(String.valueOf(count)).setValue(quote);

                //  Redirection to Homepage
                Intent notesAdded = new Intent(AddQuote.this,Homepage.class);
                startActivity(notesAdded);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddQuote.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });



    }


}
