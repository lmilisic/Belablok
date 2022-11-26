package com.mioc.belablok;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.core.graphics.TypefaceCompatUtil.getTempFile;
import static com.mioc.belablok.App.getContext;
import static com.mioc.belablok.FirstFragment.igradapter;
import static com.mioc.belablok.FirstFragment.live_indicator;
import static com.mioc.belablok.FirstFragment.qr;
import static com.mioc.belablok.Game_chooser.adapter;
import static com.mioc.belablok.Game_chooser.dataSet;
import static com.mioc.belablok.Game_chooser.id_mog_live_matcha;
import static com.mioc.belablok.Game_chooser.readFromFile;
import static com.mioc.belablok.Game_chooser.writeToFile;
import static com.mioc.belablok.Game_chooser.writeToFile1;
import static com.mioc.belablok.Game_chooser.writeToFile_datum_kreacije;
import static com.mioc.belablok.Game_chooser.writeToFile_datum_promijenjeno;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransitionImpl;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.mioc.belablok.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_RESTART_INTENT = "";
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public static TinyDB tinydb = new TinyDB(App.getContext());
    static Integer newString = null;
    final Handler handler = new Handler();
    public static Boolean live_bool = false;

    public static void save(ArrayList<Igre> igre, Boolean pobjedaa, Boolean pobjednik, Integer dijeli_prosli, Integer dijeli, Integer kraj, Integer pobjede_mi, Integer pobjede_vi){
        tinydb.putListObject("Igre", igre);
        tinydb.putBoolean("pobjedaa", pobjedaa);
        tinydb.putBoolean("pobjednik", pobjednik);
        tinydb.putInt("dijeli_prosli", dijeli_prosli);
        tinydb.putInt("dijeli", dijeli);
        tinydb.putInt("kraj", kraj);
        tinydb.putInt("pobjede_mi", pobjede_mi);
        tinydb.putInt("pobjede_vi", pobjede_vi);
    }
    public static void load(){
        if (!live_bool) {
            FirstFragment.igre = tinydb.getListObject("Igre", Igre.class);
            FirstFragment.pobjedaa = tinydb.getBoolean("pobjedaa");
            FirstFragment.pobjednik = tinydb.getBoolean("pobjednik");
            FirstFragment.dijeli_prosli = tinydb.getInt("dijeli_prosli");
            FirstFragment.dijeli = tinydb.getInt("dijeli");
            FirstFragment.kraj = tinydb.getInt("kraj");
            FirstFragment.pobjede_mi = tinydb.getInt("pobjede_mi");
            FirstFragment.pobjede_vi = tinydb.getInt("pobjede_vi");
        }else{
            Partije partija = LiveMatchesAdapter.localDataSet.get(newString);
            ArrayList<String> lista = new ArrayList<String>(Arrays.asList(TextUtils.split(partija.igre, "‚‗‚")));
            Gson gson = new Gson();
            ArrayList<String> objStrings = lista;
            ArrayList<Igre> objects =  new ArrayList<Igre>();
            for(String jObjString : objStrings){
                Object value  = gson.fromJson(jObjString,  Igre.class);
                objects.add((Igre) value);
            }
            FirstFragment.igre = objects;
            FirstFragment.pobjedaa = Boolean.valueOf(partija.pobjedaa);
            FirstFragment.pobjednik = Boolean.valueOf(partija.pobjednik);
            FirstFragment.dijeli_prosli = Integer.valueOf(partija.dijeli_prosli);
            FirstFragment.dijeli = Integer.valueOf(partija.dijeli);
            FirstFragment.kraj = Integer.valueOf(partija.kraj);
            FirstFragment.pobjede_mi = Integer.valueOf(partija.mi_pobjede);
            FirstFragment.pobjede_vi = Integer.valueOf(partija.vi_pobjede);
        }
    }
    public static void switch_team(){
        FirstFragment.igre = tinydb.getListObject("Igre", Igre.class);
        ArrayList<Igre> igre  = new ArrayList<>();
        for (int i = 0; i < FirstFragment.igre.size(); i++){
            Igre prev = FirstFragment.igre.get(i);
            Boolean[] new_pali = prev.getPali().clone();
            Boolean[] new_zvali = prev.getZvali().clone();
            Integer new_mi_suma_counter = prev.getMi_suma_counter();
            Integer new_vi_suma_counter = prev.getVi_suma_counter();
            new_zvali[1] = !new_zvali[1];
            Boolean[] new_stiglja = prev.getStiglja().clone();
            new_stiglja[1] = !new_stiglja[1];
            igre.add(new Igre(new_pali,
                    new_zvali,
                    new_stiglja,
                    prev.getBroj_zvanje_20_vi(),
                    prev.getBroj_zvanje_20_mi(),
                    prev.getBroj_zvanje_50_vi(),
                    prev.getBroj_zvanje_50_mi(),
                    prev.getBroj_zvanje_100_vi(),
                    prev.getBroj_zvanje_100_mi(),
                    prev.getBroj_zvanje_150_vi(),
                    prev.getBroj_zvanje_150_mi(),
                    prev.getBroj_zvanje_200_vi(),
                    prev.getBroj_zvanje_200_mi(),
                    prev.getVi_zvanje(),
                    prev.getMi_zvanje(),
                    prev.getVi_bodovi(),
                    prev.getMi_bodovi(),
                    prev.getVi_suma(),
                    prev.getMi_suma(),
                    new_mi_suma_counter,
                    new_vi_suma_counter));
        }
        FirstFragment.pobjedaa = tinydb.getBoolean("pobjedaa");
        FirstFragment.pobjednik = !tinydb.getBoolean("pobjednik");
        FirstFragment.pobjede_mi = tinydb.getInt("pobjede_vi");
        FirstFragment.pobjede_vi = tinydb.getInt("pobjede_mi");
        FirstFragment.igre = igre;
        tinydb.putListObject("Igre", igre);
        tinydb.putBoolean("pobjedaa", FirstFragment.pobjedaa);
        tinydb.putBoolean("pobjednik", FirstFragment.pobjednik);
        tinydb.putInt("pobjede_mi", FirstFragment.pobjede_mi);
        tinydb.putInt("pobjede_vi", FirstFragment.pobjede_vi);
        igradapter.notifyDataSetChanged();
        FirstFragment.Resume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        newString = extras.getInt(Intent.EXTRA_TEXT);
        String live = extras.getString("isLive");
        Log.d("TAG", "onCreate: "+live);
        if (live.equals("true")){
            live_bool = true;
        }else{
            live_bool = false;
        }
        Log.e("TAG", "onCreate: "+String.valueOf(newString));
        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("my_first_time", true)) {
            Log.d("Comments", "First time");
            save(new ArrayList<Igre>(), false, false, 0, -1, 1001, 0, 0);
            settings.edit().putBoolean("my_first_time", false).commit();
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");
    }
    public static class ParameterStringBuilder {
        public static String getParamsString(Map<String, String> params)
                throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();

            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }

            String resultString = result.toString();
            return resultString.length() > 0
                    ? resultString.substring(0, resultString.length() - 1)
                    : resultString;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!live_bool){
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.izbrisi:
                save(new ArrayList<Igre>(), false, false, 0, -1, 1001, 0, 0);
                String datumi = Game_chooser.readFromFile_datum_promijenjeno(getContext()).trim();
                String[] datumi1 = datumi.split("\\n");
                StringBuilder output = new StringBuilder();
                for(int i = 0; i < adapter.localDataSet.size(); i++){
                    String s = "01/01/1970 00:00\n";
                    if (i==MainActivity.newString){
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
                        Date date = new Date();
                        s = dateFormat.format(date)+"\n";
                    }else {
                        s = datumi1[i]+"\n";
                    }
                    output.append(s);
                }
                writeToFile_datum_promijenjeno(output.toString(), getContext());
                FirstFragment.recyclerView.setVisibility(View.VISIBLE);
                FirstFragment.sv.setVisibility(View.GONE);
                FirstFragment.pobjedaa = false;
                FirstFragment.dijeli = -1;
                FirstFragment.igre.clear();
                FirstFragment.pobjednik = false;
                FirstFragment.dijeli_prosli = 0;
                FirstFragment.pobjede_mi = 0;
                FirstFragment.pobjede_vi = 0;
                igradapter.notifyDataSetChanged();
                FirstFragment.Resume();
                return true;
            case R.id.export_game:
                ImageView imageCode = FirstFragment.imageView4;
                String myText = qr;
                MultiFormatWriter mWriter = new MultiFormatWriter();
                new Thread(new Runnable() {
                    public void run() {
                        try{
                            Map<String, String> parameters = new HashMap<>();
                            parameters.put("game", "belot!"+myText);
                            URL url = new URL("https://evaluator.ddns.net/add_game.php");
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("POST");
                            con.setDoOutput(true);
                            DataOutputStream out = new DataOutputStream(con.getOutputStream());
                            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                            out.flush();
                            out.close();
                            int status = con.getResponseCode();
                            Log.d("TAG", "run: "+String.valueOf(status));
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            BitMatrix mMatrix = mWriter.encode("belot!" + String.valueOf(content), BarcodeFormat.QR_CODE, 1000, 1000);
                                            BarcodeEncoder mEncoder = new BarcodeEncoder();
                                            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
                                            imageCode.setImageBitmap(mBitmap);
                                            imageCode.setVisibility(View.VISIBLE);
                                        } catch (Exception exception) {
                                            Log.e("TAG", "onOptionsItemSelected: ", exception);
                                            Context context = getApplicationContext();
                                            CharSequence text = "An error occurred while uploading/displaying! Try again.";
                                            int duration = Toast.LENGTH_LONG;
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(context, text, duration).show();
                                                }
                                            });
                                        }
                                    }
                                });
                            }else{
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
                        catch(Exception exception){
                            Log.e("TAG", "onOptionsItemSelected: ", exception);
                            Context context = getApplicationContext();
                            CharSequence text = "Check your internet connection! Data may be too long!";
                            int duration = Toast.LENGTH_LONG;
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(context, text, duration).show();
                                }
                            });
                        }
                    }
                }).start();
                imageCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imageCode.setVisibility(View.GONE);
                    }
                });
                return true;
            case R.id.import_game:
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setPrompt("Scan a barcode or QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
                intentIntegrator.initiateScan();
                return true;
            case R.id.switch_team:
                switch_team();
                return true;
            case R.id.export_game_to_file:
                writeToFile(qr, getContext());
                Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                File fileWithinMyDir = new File(getContext().getFilesDir(),"igra.txt");
                if(fileWithinMyDir.exists()) {
                    intentShareFile.setType("text/plain");
                    intentShareFile.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", fileWithinMyDir));

                    intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                            "Sharing File...");
                    intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

                    startActivity(Intent.createChooser(intentShareFile, "Share File"));
                }
                return true;
            /*
            case R.id.begin_live:
                if (id_mog_live_matcha==-1) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            live_upload_game();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (Game_chooser.id_mog_live_matcha != -1) {
                                        live_indicator.setVisibility(View.VISIBLE);
                                    } else {
                                        live_indicator.setVisibility(View.INVISIBLE);
                                    }

                                }
                            });
                        }
                    }).start();
                }
                return true;
            */
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("igra.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write("belot!"+data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    @Override
    public void onBackPressed(){
        adapter.notifyItemChanged(newString);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("TAG1", "onActivityResult: "+intentResult.getContents());
                if (intentResult.getContents().startsWith("belot!")){
                    new Thread(new Runnable() {
                        public void run() {
                            try{
                                String myText = intentResult.getContents().substring(6,intentResult.getContents().length());
                                Integer myInt = (Integer.valueOf(myText)/6781)-1;
                                Map<String, String> parameters = new HashMap<>();
                                parameters.put("id", String.valueOf(myInt));
                                URL url = new URL("https://evaluator.ddns.net/get_game.php");
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setRequestMethod("POST");
                                con.setDoOutput(true);
                                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                                out.flush();
                                out.close();
                                int status = con.getResponseCode();
                                Log.d("TAG", "run: "+String.valueOf(status));
                                if (String.valueOf(status).equals("200")){
                                    BufferedReader in = new BufferedReader(
                                            new InputStreamReader(con.getInputStream()));
                                    String inputLine;
                                    StringBuffer content = new StringBuffer();
                                    while ((inputLine = in.readLine()) != null) {
                                        content.append(inputLine);
                                    }
                                    in.close();
                                    con.disconnect();
                                    String rezultat = String.valueOf(content);
                                    if (rezultat.startsWith("belot!")){
                                        String imena = Game_chooser.readFromFile1(getContext()).trim();
                                        String[] imena1 = imena.split("\\n");
                                        StringBuilder output = new StringBuilder();
                                        for (int i = 0; i < imena1.length; i++){
                                            String s = imena1[i]+"\n";
                                            output.append(s);
                                        }
                                        output.append("Bezimena partija\n");
                                        writeToFile1(output.toString(), getContext());
                                        String datumi = Game_chooser.readFromFile_datum_kreacije(getContext()).trim();
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
                                        datumi = Game_chooser.readFromFile_datum_promijenjeno(getContext()).trim();
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
                                        String igre = readFromFile(getApplicationContext()).trim();
                                        Game_chooser.writeToFile(igre + "|" + rezultat, getApplicationContext());
                                        igre = readFromFile(getApplicationContext());
                                        dataSet = new ArrayList<Partije>();
                                        for (String i : igre.split("\\|")) {
                                            boolean b = !(i.trim().length() <= 1);
                                            if (b){
                                                dataSet.add(new Partije(i));
                                            }
                                        }
                                        runOnUiThread(new Runnable() {
                                            public void run(){
                                                adapter.localDataSet = dataSet;
                                                adapter.notifyDataSetChanged();
                                                Context context = getApplicationContext();
                                                CharSequence text = "Game added!";
                                                int duration = Toast.LENGTH_LONG;
                                                Toast.makeText(context, text, duration).show();
                                                finish();
                                            }
                                        });
                                    }
                                }else{
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
                            catch(Exception exception){
                                Log.e("TAG", "onOptionsItemSelected: ", exception);
                                Context context = getApplicationContext();
                                CharSequence text = "An error occurred! Check your internet connection!";
                                int duration = Toast.LENGTH_LONG;
                                Log.e("TAG", "run: ", exception);
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(context, text, duration).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void live_upload_game() {
        try{
            Map<String, String> parameters = new HashMap<>();
            parameters.put("game", String.valueOf(id_mog_live_matcha));
            String imena = Game_chooser.readFromFile1(getContext()).trim();
            String[] imena1 = imena.split("\\n");
            parameters.put("name", imena1[newString]);
            String datumi = Game_chooser.readFromFile_datum_kreacije(getContext()).trim();
            String[] datumi1 = datumi.split("\\n");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            Date date = dateFormat.parse(datumi1[newString]);
            long unixTime = (long) date.getTime()/1000;
            parameters.put("time_created", String.valueOf(unixTime));
            datumi = Game_chooser.readFromFile_datum_promijenjeno(getContext()).trim();
            datumi1 = datumi.split("\\n");
            dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            date = dateFormat.parse(datumi1[newString]);
            unixTime = (long) date.getTime()/1000;
            parameters.put("time_edited", String.valueOf(unixTime));
            parameters.put("igra", "belot!"+qr);
            URL url = new URL("https://evaluator.ddns.net/live_match.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();
            int status = con.getResponseCode();
            Log.d("TAG", "run: "+String.valueOf(status));
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
                id_mog_live_matcha = Integer.parseInt(String.valueOf(content));
            }else{
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
        catch(Exception exception){
            Log.e("TAG", "onOptionsItemSelected: ", exception);
            Context context = getApplicationContext();
            CharSequence text = "Check your internet connection! Data may be too long!";
            int duration = Toast.LENGTH_LONG;
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, text, duration).show();
                }
            });
        }
        executeTimedJob();
    }
    private void executeTimedJob(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                live_upload_game();
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }
}