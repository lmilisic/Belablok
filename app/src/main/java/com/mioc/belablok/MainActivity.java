package com.mioc.belablok;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static androidx.core.graphics.TypefaceCompatUtil.getTempFile;
import static com.mioc.belablok.FirstFragment.igradapter;

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
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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
        FirstFragment.igre = tinydb.getListObject("Igre", Igre.class);
        FirstFragment.pobjedaa = tinydb.getBoolean("pobjedaa");
        FirstFragment.pobjednik = tinydb.getBoolean("pobjednik");
        FirstFragment.dijeli_prosli = tinydb.getInt("dijeli_prosli");
        FirstFragment.dijeli = tinydb.getInt("dijeli");
        FirstFragment.kraj = tinydb.getInt("kraj");
        FirstFragment.pobjede_mi = tinydb.getInt("pobjede_mi");
        FirstFragment.pobjede_vi = tinydb.getInt("pobjede_vi");
    }
    public static void switch_team(){
        FirstFragment.igre = tinydb.getListObject("Igre", Igre.class);
        ArrayList<Igre> igre  = new ArrayList<>();
        for (int i = 0; i < FirstFragment.igre.size(); i++){
            Igre prev = FirstFragment.igre.get(i);
            Boolean[] new_pali = prev.getPali().clone();
            Boolean[] new_zvali = prev.getZvali().clone();
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
                    prev.getMi_suma()));
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.izbrisi:
                save(new ArrayList<Igre>(), false, false, 0, -1, 1001, 0, 0);
                FirstFragment.recyclerView.setVisibility(View.VISIBLE);
                FirstFragment.sv.setVisibility(View.GONE);
                FirstFragment.pobjedaa = false;
                FirstFragment.dijeli = -1;
                FirstFragment.igre.clear();
                FirstFragment.pobjednik = false;
                FirstFragment.dijeli_prosli = 0;
                FirstFragment.kraj = 1001;
                FirstFragment.pobjede_mi = 0;
                FirstFragment.pobjede_vi = 0;
                igradapter.notifyDataSetChanged();
                FirstFragment.Resume();
                return true;
            case R.id.export_game:
                ImageView imageCode = FirstFragment.imageView4;
                String myText = FirstFragment.qr;
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
                                    try{
                                        BitMatrix mMatrix = mWriter.encode("belot!"+String.valueOf(content), BarcodeFormat.QR_CODE, 1000,1000);
                                        BarcodeEncoder mEncoder = new BarcodeEncoder();
                                        Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
                                        imageCode.setImageBitmap(mBitmap);
                                        imageCode.setVisibility(View.VISIBLE);
                                    } catch(Exception exception){
                                        Log.e("TAG", "onOptionsItemSelected: ", exception);
                                        Context context = getApplicationContext();
                                        CharSequence text = "An error occurred while uploading/displaying! Try again.";
                                        int duration = Toast.LENGTH_LONG;
                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                    }
                                }
                            });
                        }
                        catch(Exception exception){
                            Log.e("TAG", "onOptionsItemSelected: ", exception);
                            Context context = getApplicationContext();
                            CharSequence text = "Data too long! Internet connection required!";
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


/*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
*/
    @Override
    public void onBackPressed()
    {
        Game_chooser.adapter.notifyItemChanged(newString);
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
                                rezultat = rezultat.substring(6, rezultat.length());
                                byte[] decodedBytes = Base64.getDecoder().decode(rezultat);
                                String decodedString = new String(decodedBytes);
                                if (String.valueOf(content).startsWith("belot!")){
                                    WriteToFile("/data/data/com.mioc.belablok/shared_prefs/com.mioc.belablok_preferences.xml", decodedString);
                                    WriteToFile("/data/user/0/com.mioc.belablok/shared_prefs/com.mioc.belablok_preferences.xml", decodedString);
                                    tinydb = new TinyDB(App.getContext());
                                    load();
                                    Context context = getApplicationContext();
                                    Intent mStartActivity = new Intent(context, Loading.class);
                                    int mPendingIntentId = 123456;
                                    PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                                    AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                                    System.exit(0);
                                }
                            }
                            catch(Exception exception){
                                Log.e("TAG", "onOptionsItemSelected: ", exception);
                                Context context = getApplicationContext();
                                CharSequence text = "An error occurred!";
                                int duration = Toast.LENGTH_LONG;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                    }).start();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void WriteToFile(String fileName, String content){
        try{
            FileOutputStream writer = new FileOutputStream(fileName);
            writer.write(content.getBytes());
            writer.close();
            Log.e("TAG", "Wrote to file: "+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}