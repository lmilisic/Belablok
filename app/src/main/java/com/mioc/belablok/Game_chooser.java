package com.mioc.belablok;

import static com.mioc.belablok.App.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Game_chooser extends AppCompatActivity {
    static ArrayList<Partije> dataSet = new ArrayList<Partije>();
    static SharedPreferences partije;
    static VelikePartijeAdapter adapter;
    public static int color_text;
    public static int color_text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_chooser);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv1);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = recyclerView.getContext().getTheme();
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        int color = ContextCompat.getColor(recyclerView.getContext(), typedValue.resourceId);
        color_text = Color.parseColor("#"+Integer.toHexString(color).substring(2));
        TypedValue typedValue1 = new TypedValue();
        Resources.Theme theme1 = recyclerView.getContext().getTheme();
        theme1.resolveAttribute(android.R.attr.textColorPrimaryInverse, typedValue1, true);
        int color1 = ContextCompat.getColor(recyclerView.getContext(), typedValue1.resourceId);
        color_text1 = Color.parseColor("#"+Integer.toHexString(color1).substring(2));
        Log.d("color_text1", String.valueOf(color_text1));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new VelikePartijeAdapter(dataSet);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String igre = readFromFile(getApplicationContext());
                writeToFile(igre + "|" + "belot!PD94bWwgdmVyc2lvbj0nMS4wJyBlbmNvZGluZz0ndXRmLTgnIHN0YW5kYWxvbmU9J3llcycgPz4KPG1hcD4KICAgIDxpbnQgbmFtZT0iZGlqZWxpIiB2YWx1ZT0iLTEiIC8+CiAgICA8Ym9vbGVhbiBuYW1lPSJwb2JqZWRhYSIgdmFsdWU9ImZhbHNlIiAvPgogICAgPGludCBuYW1lPSJrcmFqIiB2YWx1ZT0iMTAwMSIgLz4KICAgIDxzdHJpbmcgbmFtZT0iSWdyZSI+PC9zdHJpbmc+CiAgICA8aW50IG5hbWU9InBvYmplZGVfbWkiIHZhbHVlPSIwIiAvPgogICAgPGludCBuYW1lPSJwb2JqZWRlX3ZpIiB2YWx1ZT0iMCIgLz4KICAgIDxib29sZWFuIG5hbWU9InBvYmplZG5payIgdmFsdWU9ImZhbHNlIiAvPgogICAgPGludCBuYW1lPSJkaWplbGlfcHJvc2xpIiB2YWx1ZT0iMCIgLz4KPC9tYXA+Cg==", getApplicationContext());
                igre = readFromFile(getApplicationContext());
                dataSet = new ArrayList<Partije>();
                for (String i : igre.split("\\|")) {
                    boolean b = !(i.trim().length() <= 1);
                    if (b) {
                        dataSet.add(new Partije(i));
                    }
                }
                adapter.localDataSet = dataSet;
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        final String PREFS_NAME = "MyGames";
        partije = getSharedPreferences(PREFS_NAME, 0);
        if (partije.getBoolean("my_first_time", true)) {
            partije.edit().putBoolean("my_first_time", false).commit();
            writeToFile("", getApplicationContext());
            adapter.localDataSet = dataSet;
            adapter.notifyDataSetChanged();
        }
        String igre = readFromFile(getApplicationContext());
        dataSet = new ArrayList<Partije>();
        Log.d("TAG", "onCreate: " + igre);
        for (String i : igre.split("\\|")) {
            Log.d("TAG", "onCreate: " + i);
            boolean b = !(i.trim().length() <= 1);
            if (b) {
                dataSet.add(new Partije(i));
            }
        }
        adapter.localDataSet = dataSet;
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed()
    {
    }
    public static void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("games.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public static String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("games.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}