package com.example.nhatproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    List<FootballGame> list;
    FootballAdapter adapter;
    Button more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);

        floatingActionButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycleView);
        more = findViewById(R.id.more);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeViewActivity.this, ShowFragment.class);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("football");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<FootballGame>();
                for(DataSnapshot item : snapshot.getChildren()){
                    FootballGame footballGame = item.getValue(FootballGame.class);
                    list.add(footballGame);
                }
                adapter = new FootballAdapter(HomeViewActivity.this, list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeViewActivity.this,
                        AddGameActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tab_search,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText.trim();
                List<FootballGame> newList = new ArrayList<>();
                databaseReference = database.getReference().child("football");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list = new ArrayList<FootballGame>();
                        for(DataSnapshot item : snapshot.getChildren()){
                            FootballGame footballGame = item.getValue(FootballGame.class);
                            if(text == ""){
                                list.add(footballGame);
                            }
//                            list.add(footballGame);
                            else if(compareString(text, footballGame.getHomeTeam().toString()) || compareString(text, footballGame.getAwayTeam().toString())){
                                list.add(footballGame);
                            }
                        }
//                        FootballGame fg = new FootballGame("123","liber","sds","ffd","fd");
//                        newList.add(fg);
                        adapter = new FootballAdapter(HomeViewActivity.this, list);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public boolean compareString(String s1, String s2){
        int dem = s1.trim().length();
        String s3 = s2.substring(0,dem);
        if(s3.equalsIgnoreCase(s1)){
            return true;
        }
        else return false;
    }
}