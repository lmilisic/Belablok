package com.mioc.belablok;

import static com.mioc.belablok.FirstFragment.igradapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransitionImpl;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mioc.belablok.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public static TinyDB tinydb = new TinyDB(App.getContext());

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}