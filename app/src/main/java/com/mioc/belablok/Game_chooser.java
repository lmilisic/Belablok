package com.mioc.belablok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Game_chooser extends AppCompatActivity {
    ArrayList<Partije> dataSet = new ArrayList<Partije>();
    static SharedPreferences partije;
    static VelikePartijeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_chooser);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new VelikePartijeAdapter(dataSet);
        recyclerView.setAdapter(adapter);
        final String PREFS_NAME = "MyGames";
        partije = getSharedPreferences(PREFS_NAME, 0);
        if (partije.getBoolean("my_first_time", true)){
            partije.edit().putBoolean("my_first_time", false).commit();
            partije.edit().putString("games", "").commit();
            adapter.localDataSet = dataSet;
            adapter.notifyDataSetChanged();
        }
        String igre = Game_chooser.partije.getString("games", "");
        dataSet = new ArrayList<Partije>();
        Log.d("TAG", "onCreate: "+igre);
        for (String i : igre.split("\\|")){
            Log.d("TAG", "onCreate: "+i);
            boolean b = !(i.length() <= 1);
            if (b) {
                dataSet.add(new Partije(i));
            }
        }
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String igre = Game_chooser.partije.getString("games", "");
                partije.edit().putString("games", igre+"|"+"belot!PD94bWwgdmVyc2lvbj0nMS4wJyBlbmNvZGluZz0ndXRmLTgnIHN0YW5kYWxvbmU9J3llcycgPz4KPG1hcD4KICAgIDxpbnQgbmFtZT0iZGlqZWxpIiB2YWx1ZT0iLTEiIC8+CiAgICA8Ym9vbGVhbiBuYW1lPSJwb2JqZWRhYSIgdmFsdWU9ImZhbHNlIiAvPgogICAgPGludCBuYW1lPSJrcmFqIiB2YWx1ZT0iMTAwMSIgLz4KICAgIDxzdHJpbmcgbmFtZT0iSWdyZSI+PC9zdHJpbmc+CiAgICA8aW50IG5hbWU9InBvYmplZGVfbWkiIHZhbHVlPSIwIiAvPgogICAgPGludCBuYW1lPSJwb2JqZWRlX3ZpIiB2YWx1ZT0iMCIgLz4KICAgIDxib29sZWFuIG5hbWU9InBvYmplZG5payIgdmFsdWU9ImZhbHNlIiAvPgogICAgPGludCBuYW1lPSJkaWplbGlfcHJvc2xpIiB2YWx1ZT0iMCIgLz4KPC9tYXA+Cg==").commit();
                igre = Game_chooser.partije.getString("games", "");
                dataSet = new ArrayList<Partije>();
                for (String i : igre.split("\\|")){
                    boolean b = !(i.length() <= 1);
                    if (b) {
                        dataSet.add(new Partije(i));
                    }
                }
                adapter.localDataSet = dataSet;
                adapter.notifyDataSetChanged();
            }
        });
        adapter.localDataSet = dataSet;
        adapter.notifyDataSetChanged();
    }
}