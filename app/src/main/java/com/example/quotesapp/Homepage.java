package com.example.quotesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Homepage extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView quotesSection;
    private String title, description;
    private ArrayList<String> firebaseTitle = new ArrayList<>();
    private ArrayList<String> firebaseDescription = new ArrayList<>();
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private AdapterView av;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        //Appbar (Action Bar)
        toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Recycler View (Quotes representation)
        quotesSection = findViewById(R.id.QuotesSection);

        //Swipe for refresh
        refreshLayout = findViewById(R.id.swipeToRefresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setQuotesData();
                refreshLayout.setRefreshing(false);
            }
        });

        //Setting Quotes with recycler view
        setQuotesData();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        return true;
    }


    public void setQuotesData(){

        av = new AdapterView(Homepage.this,firebaseTitle,firebaseDescription);

        ref.child("quotes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    firebaseTitle.clear();
                    firebaseDescription.clear();

                    for(DataSnapshot snp : snapshot.getChildren()){
                        for(DataSnapshot finSnap : snp.getChildren()){

                            description = finSnap.child("description").getValue(String.class);
                            title = finSnap.child("title").getValue(String.class);

                            firebaseTitle.add(title);
                            firebaseDescription.add(description);

                        }
                    }

                }

                //        Setting layout
                quotesSection.setLayoutManager(new LinearLayoutManager(Homepage.this));
                quotesSection.setHasFixedSize(true);
                quotesSection.setAdapter(av);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Homepage.this,"error",Toast.LENGTH_SHORT);
            }

        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.menu_logout : logOut();
            break;
            case R.id.menu_profile: getProfile();
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getProfile() {
        Intent profile = new Intent(this,Profile.class);
        startActivity(profile);
    }

    public void logOut(){
        SessionManager sm = new SessionManager(Homepage.this);
        sm.logout();
        Intent getMeOut = new Intent(this,MainActivity.class);
        startActivity(getMeOut);
    }

    public void goToAddQuote(View view) {
        Intent goToAddQuote = new Intent(this,AddQuote.class);
        startActivity(goToAddQuote);
    }

}




