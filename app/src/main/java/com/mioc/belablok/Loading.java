package com.mioc.belablok;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class Loading extends AppCompatActivity {
    Runnable runnable;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(Loading.this, Game_chooser.class);
                Loading.this.startActivity(myIntent);
            }
        };
        handler.postDelayed(runnable, 1000);
    }
    @Override
    public void onBackPressed()
    {
    }
}