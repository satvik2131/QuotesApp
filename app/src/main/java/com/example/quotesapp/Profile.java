package com.example.quotesapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    private String fbTitle,fbDescription;
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private AdapterView av;
    private TextView username;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private RecyclerView myQuotes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SessionManager sm = new SessionManager(Profile.this);
        final String id = sm.getID();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        username = findViewById(R.id.name);

        ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.child(id).child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Get Recycler View
        myQuotes = findViewById(R.id.myQuotes);

        myQuotes.setLayoutManager(new LinearLayoutManager(Profile.this));
        myQuotes.setHasFixedSize(true);
        av = new AdapterView(Profile.this,title,description);


        ref.child("quotes").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    title.clear();
                    description.clear();


                    for (DataSnapshot snap : snapshot.getChildren()) {
                        fbTitle = snap.child("title").getValue(String.class);
                        fbDescription = snap.child("description").getValue(String.class);

                        title.add(fbTitle);
                        description.add(fbDescription);
                    }


                    myQuotes.setAdapter(av);
                }else{
                    Toast.makeText(Profile.this, "0", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this,"error",Toast.LENGTH_SHORT);
            }
        });
    }
}

