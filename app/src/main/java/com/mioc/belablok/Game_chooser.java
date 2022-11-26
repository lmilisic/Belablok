package com.mioc.belablok;

import static com.mioc.belablok.App.getContext;
import static com.mioc.belablok.VelikePartijeAdapter.getItemCount1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Game_chooser extends AppCompatActivity {
    static ArrayList<Partije> dataSet = new ArrayList<Partije>();
    static SharedPreferences partije;
    static VelikePartijeAdapter adapter;
    public static int color_text;
    public static int color_text1;
    private void downloadFile(String url) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL u = new URL(url);
                    URLConnection conn = u.openConnection();
                    int contentLength = conn.getContentLength();

                    DataInputStream stream = new DataInputStream(u.openStream());

                    byte[] buffer = new byte[contentLength];
                    stream.readFully(buffer);
                    stream.close();
                    final File dir = new File(getFilesDir(), "instalacija/");
                    dir.mkdirs();
                    final File file = new File(dir, "app-release.apk");
                    DataOutputStream fos = new DataOutputStream(new FileOutputStream(file));
                    fos.write(buffer);
                    fos.flush();
                    fos.close();
                    onReceive(getContext());
                } catch (FileNotFoundException e) {
                    return; // swallow a 404
                } catch (IOException e) {
                    return; // swallow a 404
                }
            }
        });

        thread.start();
    }
    Boolean dopustenje = false;
    public static Integer id_mog_live_matcha = -1;
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
                String imena = Game_chooser.readFromFile1(view.getContext()).trim();
                String[] imena1 = imena.split("\\n");
                StringBuilder output = new StringBuilder();
                for(int i = 0; i < adapter.localDataSet.size(); i++){
                    String s = imena1[i]+"\n";
                    output.append(s);
                }
                output.append("Bezimena partija\n");
                writeToFile1(output.toString(), getContext());
                String datumi = Game_chooser.readFromFile_datum_kreacije(view.getContext()).trim();
                String[] datumi1 = datumi.split("\\n");
                output = new StringBuilder();
                for(int i = 0; i < adapter.localDataSet.size(); i++){
                    String s = datumi1[i]+"\n";
                    output.append(s);
                }
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
                Date date = new Date();
                output.append(dateFormat.format(date)+"\n");
                writeToFile_datum_kreacije(output.toString(), getContext());
                datumi = Game_chooser.readFromFile_datum_promijenjeno(view.getContext()).trim();
                datumi1 = datumi.split("\\n");
                output = new StringBuilder();
                for(int i = 0; i < adapter.localDataSet.size(); i++){
                    String s = datumi1[i]+"\n";
                    output.append(s);
                }
                dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
                date = new Date();
                output.append(dateFormat.format(date)+"\n");
                writeToFile_datum_promijenjeno(output.toString(), getContext());
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
        new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = new URL("https://evaluator.ddns.net/v.txt");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
                        Log.d("content", "onCreate: " + content);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int versionCode = BuildConfig.VERSION_CODE;
                                    if (Integer.parseInt(String.valueOf(content))>versionCode){
                                        try {
                                            Context context = getApplicationContext();
                                            CharSequence text = "Update in progress!";
                                            int duration = Toast.LENGTH_LONG;
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(context, text, duration).show();
                                                }
                                            });
                                            ActivityCompat.requestPermissions(Game_chooser.this,
                                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                    1);
                                        }catch (Exception exception){
                                            Log.e("TAG", "onOptionsItemSelected: ", exception);
                                            Context context = getApplicationContext();
                                            CharSequence text = "Can't check for updates! Check your internet connection!";
                                            int duration = Toast.LENGTH_LONG;
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(context, text, duration).show();
                                                }
                                            });
                                        }
                                    }
                                } catch (Exception exception) {
                                    Log.e("TAG", "onOptionsItemSelected: ", exception);
                                    Context context = getApplicationContext();
                                    CharSequence text = "Can't check for updates! Check your internet connection!";
                                    int duration = Toast.LENGTH_LONG;
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(context, text, duration).show();
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = "Can't check for updates! Check your internet connection!";
                        int duration = Toast.LENGTH_LONG;
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(context, text, duration).show();
                            }
                        });
                    }
                } catch (Exception exception) {
                    Context context = getApplicationContext();
                    CharSequence text = "Can't check for updates! Check your internet connection!";
                    int duration = Toast.LENGTH_LONG;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, text, duration).show();
                        }
                    });
                    Log.e("TAG", "onCreate: ", exception);
                }
            }
        }).start();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    dopustenje = true;
                    if (dopustenje){
                        runOnUiThread(new Runnable() {
                            public void run() {
                                downloadFile("https://evaluator.ddns.net/app-release.apk");
                            }
                        });
                    }
                } else {
                    Toast.makeText(Game_chooser.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
    public void onReceive(Context context) {
        final File dir = new File(getFilesDir(), "instalacija/");
        dir.mkdirs();
        final File file = new File(dir, "app-release.apk");
        Log.d("TAG", "onReceive: "+file);
        Log.d("TAG", "onReceive: "+file.exists());
        if (file.exists()) {
            Intent install;
            Uri apkUri = FileProvider.getUriForFile(context, getApplicationContext().getPackageName() + ".fileprovider", file);
            Log.d("TAG", "onReceive: "+apkUri);
            install = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setData(apkUri);
            install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
            install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(install);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game_chooser, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.izbrisi_sve:
                for (int k = 0; k < getItemCount1(); k++) {
                    Integer position1 = getItemCount1() - 1 - k;
                    StringBuilder output = new StringBuilder();
                    writeToFile1(output.toString(), getContext());
                    output = new StringBuilder();
                    writeToFile_datum_kreacije(output.toString(), getContext());
                    output = new StringBuilder();
                    writeToFile_datum_promijenjeno(output.toString(), getContext());
                    ArrayList<Partije> dataSet = new ArrayList<Partije>();
                    adapter.localDataSet = dataSet;
                    Game_chooser.dataSet = dataSet;
                    adapter.notifyDataSetChanged();
                    Game_chooser.writeToFile("", getContext());
                }
            /*
            case R.id.live:
                Intent intent = new Intent();
                intent.setClass(getContext(), live_matches.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            */
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        id_mog_live_matcha = -1;
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
        File file = getBaseContext().getFileStreamPath("imena_partija.txt");
        boolean fileexists = file.exists();
        if (!fileexists){
            StringBuilder output = new StringBuilder();
            for(int i = 0; i < adapter.localDataSet.size(); i++){
                String s = "Bezimena partija\n";
                output.append(s);
            }
            writeToFile1(output.toString(), getContext());
        }
        file = getBaseContext().getFileStreamPath("datum_kreirano.txt");
        fileexists = file.exists();
        if (!fileexists){
            StringBuilder output = new StringBuilder();
            for(int i = 0; i < adapter.localDataSet.size(); i++){
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
                Date date = new Date();
                String s = dateFormat.format(date)+"\n";
                output.append(s);
            }
            writeToFile_datum_kreacije(output.toString(), getContext());
        }
        file = getBaseContext().getFileStreamPath("datum_promijenjeno.txt");
        fileexists = file.exists();
        if (!fileexists){
            StringBuilder output = new StringBuilder();
            for(int i = 0; i < adapter.localDataSet.size(); i++){
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
                Date date = new Date();
                String s = dateFormat.format(date)+"\n";
                output.append(s);
            }
            writeToFile_datum_promijenjeno(output.toString(), getContext());
        }
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
    private boolean isFileExists(String filename){

        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.exists();


    }

    public boolean deleteFile(String filename){

        File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
        return folder1.delete();


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
    public static void writeToFile1(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("imena_partija.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public static String readFromFile1(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("imena_partija.txt");

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
    public static String readFromFile_datum_kreacije(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("datum_kreirano.txt");

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
    public static void writeToFile_datum_kreacije(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("datum_kreirano.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public static String readFromFile_datum_promijenjeno(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("datum_promijenjeno.txt");

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
    public static void writeToFile_datum_promijenjeno(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("datum_promijenjeno.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}