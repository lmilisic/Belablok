package com.mioc.belablok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.NameList;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class live_matches extends AppCompatActivity {
    static ArrayList<Partije> dataSet = new ArrayList<Partije>();
    static ArrayList<LiveMatchObject> dataSet_match_details = new ArrayList<LiveMatchObject>();
    static LiveMatchesAdapter adapter;
    final Handler handler = new Handler();
    private void downloadData() {
        try {
            dataSet_match_details = new ArrayList<LiveMatchObject>();
            dataSet = new ArrayList<Partije>();
            URL url = new URL("https://evaluator.ddns.net/get_live_matches.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            int status = con.getResponseCode();
            Log.d("TAG", "run: " + String.valueOf(status));
            if (String.valueOf(status).equals("200")) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();
                JSONObject json = new JSONObject(String.valueOf(content));
                for (int i = 0; i < json.length(); i++) {
                    JSONObject live_match_json = new JSONObject(json.getString(String.valueOf(i)));
                    long datum_e = Integer.parseInt(live_match_json.getString("time_edited"));
                    Date date = new Date();
                    long unixTime = date.getTime()/1000;
                    Log.d("TAG", "downloadData: 1 "+unixTime);
                    Log.d("TAG", "downloadData: 2 "+datum_e);
                    if ((unixTime-datum_e)<=(5*60)) {
                        LiveMatchObject live_match = new LiveMatchObject(
                                Integer.parseInt(live_match_json.getString("id")),
                                live_match_json.getString("name"),
                                Integer.parseInt(live_match_json.getString("time_created")),
                                Integer.parseInt(live_match_json.getString("time_edited")),
                                live_match_json.getString("igra"));
                        dataSet.add(new Partije(live_match.getIgra()));
                        dataSet_match_details.add(live_match);
                    }
                }
            } else {
                Context context = getApplicationContext();
                CharSequence text = "Check your internet connection!";
                int duration = Toast.LENGTH_LONG;
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, text, duration).show();
                    }
                });
            }
        }
        catch (Exception e){
            Log.e("JSON", "downloadData: ", e);
            Context context = getApplicationContext();
            CharSequence text = "Check your internet connection!";
            int duration = Toast.LENGTH_LONG;
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, text, duration).show();
                }
            });
        }
        adapter.localDataSet = dataSet;
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_matches);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.live_matches);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LiveMatchesAdapter(dataSet);
        recyclerView.setAdapter(adapter);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        downloadData();
        executeTimedJob();
    }
    private void executeTimedJob(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                downloadData();
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }
}