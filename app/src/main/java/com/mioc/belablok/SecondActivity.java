package com.mioc.belablok;

import static com.google.android.material.badge.BadgeUtils.attachBadgeDrawable;
import static com.mioc.belablok.FirstFragment.igradapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;

import java.util.Objects;

public class SecondActivity extends AppCompatActivity {

    Button vi_bodovi;
    Button mi_bodovi;
    Button ponisti;
    Button jedan;
    Button dva;
    Button tri;
    Button cetiri;
    Button pet;
    Button sest;
    Button sedam;
    Button osam;
    Button devet;
    Button nula;
    Button brisanje;

    Boolean[] pali = {false};

    Button zvanje_20_button;
    Button zvanje_50_button;
    Button zvanje_100_button;
    Button zvanje_150_button;
    Button zvanje_200_button;

    Button mi_zvali;
    Button vi_zvali;
    Boolean[] zvali = {false, false};

    Button stiglja_button;
    Boolean[] stiglja = {false, false};

    BadgeDrawable mi_20;
    BadgeDrawable vi_20;
    BadgeDrawable mi_50;
    BadgeDrawable vi_50;
    BadgeDrawable mi_100;
    BadgeDrawable vi_100;
    BadgeDrawable mi_150;
    BadgeDrawable vi_150;
    BadgeDrawable mi_200;
    BadgeDrawable vi_200;


    final Integer[] broj_zvanje_20_mi = {0};
    final Integer[] broj_zvanje_20_vi = {0};
    final Integer[] broj_zvanje_50_mi = {0};
    final Integer[] broj_zvanje_50_vi = {0};
    final Integer[] broj_zvanje_100_mi = {0};
    final Integer[] broj_zvanje_100_vi = {0};
    final Integer[] broj_zvanje_150_mi = {0};
    final Integer[] broj_zvanje_150_vi = {0};
    final Integer[] broj_zvanje_200_mi = {0};
    final Integer[] broj_zvanje_200_vi = {0};

    final int[] fokus = new int[1];

    TextView mi_zvanja;
    TextView mi_suma;
    TextView vi_zvanja;
    TextView vi_suma;
    Button x;
    String newString;
    Integer edit;
    Boolean dosao_iz_pobjede;
    Double scaleh;
    Double scalew;
    static int color_text;
    static int color_text1;

    private int dp_to_px(int dp){
        float dip = dp;
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
        return (int) px;
    }
    @SuppressLint({"UnsafeOptInUsageError", "Range"})
    private BadgeDrawable setbadge(Button button, Integer number, Boolean vis, String color, Integer gravity){
        BadgeDrawable badgeDrawable = BadgeDrawable.create(SecondActivity.this);
        badgeDrawable.setNumber(number);
        badgeDrawable.setVisible(vis);
        badgeDrawable.setBackgroundColor(Color.parseColor(color));
        badgeDrawable.setBadgeTextColor(Color.parseColor("#FFFFFF"));
        badgeDrawable.setBadgeGravity(gravity);
        attachBadgeDrawable(badgeDrawable, button);
        return badgeDrawable;
    }

    @SuppressLint("UnsafeOptInUsageError")
    @Override
    protected void onResume() {
        super.onResume();
        this.getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run(){
                Bundle extras = getIntent().getExtras();
                newString = extras.getString(Intent.EXTRA_TEXT);
                edit = extras.getInt("EDIT");
                if (edit!=-1){
                    Igre igra = IgraAdapter.localDataSet.get(edit);
                    zvali = igra.getZvali().clone();
                    stiglja = igra.getStiglja().clone();
                    broj_zvanje_20_mi[0] = igra.getBroj_zvanje_20_mi();
                    broj_zvanje_20_vi[0] = igra.getBroj_zvanje_20_vi();
                    broj_zvanje_50_mi[0] = igra.getBroj_zvanje_50_mi();
                    broj_zvanje_50_vi[0] = igra.getBroj_zvanje_50_vi();
                    broj_zvanje_100_mi[0] = igra.getBroj_zvanje_100_mi();
                    broj_zvanje_100_vi[0] = igra.getBroj_zvanje_100_vi();
                    broj_zvanje_150_mi[0] = igra.getBroj_zvanje_150_mi();
                    broj_zvanje_150_vi[0] = igra.getBroj_zvanje_150_vi();
                    broj_zvanje_200_mi[0] = igra.getBroj_zvanje_200_mi();
                    broj_zvanje_200_vi[0] = igra.getBroj_zvanje_200_vi();
                    mi_zvanja.setText("+"+igra.mi_zvanje);
                    vi_zvanja.setText("+"+igra.vi_zvanje);
                    mi_bodovi.setText(igra.mi_bodovi);
                    vi_bodovi.setText(igra.vi_bodovi);
                    mi_suma.setText("Σ "+igra.mi_suma);
                    vi_suma.setText("Σ "+igra.vi_suma);
                    if (broj_zvanje_20_mi[0]>0) {
                        mi_20.setNumber(broj_zvanje_20_mi[0]);
                        mi_20.setVisible(true);
                        mi_20.setBackgroundColor(Color.parseColor("#2196F3"));
                        mi_20.setBadgeGravity(BadgeDrawable.TOP_END);
                        attachBadgeDrawable(mi_20, zvanje_20_button);
                    }if (broj_zvanje_20_vi[0]>0){
                        vi_20.setNumber(broj_zvanje_20_vi[0]);
                        vi_20.setVisible(true);
                        vi_20.setBackgroundColor(Color.parseColor("#F42414"));
                        vi_20.setBadgeGravity(BadgeDrawable.BOTTOM_END);
                        attachBadgeDrawable(vi_20, zvanje_20_button);
                    }if (broj_zvanje_50_mi[0]>0){
                        mi_50.setNumber(broj_zvanje_50_mi[0]);
                        mi_50.setVisible(true);
                        mi_50.setBackgroundColor(Color.parseColor("#2196F3"));
                        mi_50.setBadgeGravity(BadgeDrawable.TOP_END);
                        attachBadgeDrawable(mi_50, zvanje_50_button);
                    }if (broj_zvanje_50_vi[0]>0){
                        vi_50.setNumber(broj_zvanje_50_vi[0]);
                        vi_50.setVisible(true);
                        vi_50.setBackgroundColor(Color.parseColor("#F42414"));
                        vi_50.setBadgeGravity(BadgeDrawable.BOTTOM_END);
                        attachBadgeDrawable(vi_50, zvanje_50_button);
                    }if (broj_zvanje_100_mi[0]>0){
                        mi_100.setNumber(broj_zvanje_100_mi[0]);
                        mi_100.setVisible(true);
                        mi_100.setBackgroundColor(Color.parseColor("#2196F3"));
                        mi_100.setBadgeGravity(BadgeDrawable.TOP_END);
                        attachBadgeDrawable(mi_100, zvanje_100_button);
                    }if (broj_zvanje_100_vi[0]>0){
                        vi_100.setNumber(broj_zvanje_100_vi[0]);
                        vi_100.setVisible(true);
                        vi_100.setBackgroundColor(Color.parseColor("#F42414"));
                        vi_100.setBadgeGravity(BadgeDrawable.BOTTOM_END);
                        attachBadgeDrawable(vi_100, zvanje_100_button);
                    }if (broj_zvanje_150_mi[0]>0){
                        mi_150.setNumber(broj_zvanje_150_mi[0]);
                        mi_150.setVisible(true);
                        mi_150.setBackgroundColor(Color.parseColor("#2196F3"));
                        mi_150.setBadgeGravity(BadgeDrawable.TOP_END);
                        attachBadgeDrawable(mi_150, zvanje_150_button);
                    }if (broj_zvanje_150_vi[0]>0){
                        vi_150.setNumber(broj_zvanje_150_vi[0]);
                        vi_150.setVisible(true);
                        vi_150.setBackgroundColor(Color.parseColor("#F42414"));
                        vi_150.setBadgeGravity(BadgeDrawable.BOTTOM_END);
                        attachBadgeDrawable(vi_150, zvanje_150_button);
                    }if (broj_zvanje_200_mi[0]>0){
                        mi_200.setNumber(broj_zvanje_200_mi[0]);
                        mi_200.setVisible(true);
                        mi_200.setBackgroundColor(Color.parseColor("#2196F3"));
                        mi_200.setBadgeGravity(BadgeDrawable.TOP_END);
                        attachBadgeDrawable(mi_200, zvanje_200_button);
                    }if (broj_zvanje_200_vi[0]>0){
                        vi_200.setNumber(broj_zvanje_200_vi[0]);
                        vi_200.setVisible(true);
                        vi_200.setBackgroundColor(Color.parseColor("#F42414"));
                        vi_200.setBadgeGravity(BadgeDrawable.BOTTOM_END);
                        attachBadgeDrawable(vi_200, zvanje_200_button);
                    }
                    if (zvali[0]) {
                        if(!zvali[1]){
                            mi_zvali.setBackgroundColor(Color.parseColor("#2196F3"));
                            vi_zvali.setBackgroundColor(color_text1);
                        }
                        else if(zvali[1]){
                            mi_zvali.setBackgroundColor(color_text1);
                            vi_zvali.setBackgroundColor(Color.parseColor("#F42414"));
                        }
                    }

                    if (stiglja[0]){
                        if (!stiglja[1]) {
                            stiglja_button.setBackgroundColor(Color.parseColor("#2196F3"));
                        }
                        if (stiglja[1]) {
                            stiglja_button.setBackgroundColor(Color.parseColor("#F42414"));
                        }
                    }
                    if (Integer.parseInt(igra.mi_suma)>=Integer.parseInt(igra.vi_suma)){
                        fokus[0] = 0;
                    }
                    if (Integer.parseInt(igra.mi_suma)<Integer.parseInt(igra.vi_suma)){
                        fokus[0] = 1;
                    }
                    if (fokus[0]==1){
                        int parentId = ((View) vi_bodovi.getParent()).getId();

                        ConstraintLayout.LayoutParams dividerlayout = new ConstraintLayout.LayoutParams((int)(dp_to_px(409)),(int) (dp_to_px(1)));
                        dividerlayout.topMargin=dp_to_px(8);
                        dividerlayout.bottomMargin=dp_to_px(8);
                        dividerlayout.topToBottom=R.id.vi_suma;
                        dividerlayout.startToStart=parentId;
                        dividerlayout.endToEnd=parentId;
                        dividerlayout.bottomToTop=R.id.button14;
                        View divider = findViewById(R.id.divider2);
                        divider.setLayoutParams(dividerlayout);

                        ConstraintLayout.LayoutParams veci = new ConstraintLayout.LayoutParams((int)(dp_to_px(150)), (int)(dp_to_px(90)));
                        veci.startToStart=R.id.divider;
                        veci.topToTop=parentId;
                        veci.topMargin=dp_to_px(8);
                        vi_bodovi.setTextColor(Color.WHITE);
                        vi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                        ConstraintLayout.LayoutParams manji = new ConstraintLayout.LayoutParams((int)(dp_to_px(110)), (int)(dp_to_px(75)));
                        manji.bottomToBottom=R.id.button_vi_bodovi;
                        manji.endToEnd=R.id.divider;
                        manji.topToTop=R.id.button_vi_bodovi;
                        manji.rightMargin=dp_to_px(24);
                        mi_bodovi.setTextColor(Color.rgb(16,65,104));
                        mi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
                        vi_bodovi.setLayoutParams(veci);
                        mi_bodovi.setLayoutParams(manji);
                        Button[] array = new Button[]{mi_bodovi, vi_bodovi};
                        for (int i=0; i < array.length; i++){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) array[i].getLayoutParams();
                            params.height *= scaleh;
                            params.width *= scalew;
                            params.topMargin *= scaleh;
                            params.bottomMargin *= scaleh;
                            params.rightMargin *= scalew;
                            params.leftMargin *= scalew;
                            array[i].setLayoutParams(params);
                        }
                    }
                    else{
                        int parentId = ((View) mi_bodovi.getParent()).getId();

                        ConstraintLayout.LayoutParams dividerlayout = new ConstraintLayout.LayoutParams((int)(dp_to_px(409)), (int)(dp_to_px(1)));
                        dividerlayout.topMargin=dp_to_px(8);
                        dividerlayout.bottomMargin=dp_to_px(8);
                        dividerlayout.topToBottom=R.id.mi_suma;
                        dividerlayout.startToStart=parentId;
                        dividerlayout.endToEnd=parentId;
                        dividerlayout.bottomToTop=R.id.button14;
                        View divider = findViewById(R.id.divider2);
                        divider.setLayoutParams(dividerlayout);

                        ConstraintLayout.LayoutParams veci = new ConstraintLayout.LayoutParams((int)(dp_to_px(150)), (int)(dp_to_px(90)));
                        veci.endToStart=R.id.divider;
                        veci.topToTop=parentId;
                        veci.topMargin=dp_to_px(8);
                        mi_bodovi.setTextColor(Color.WHITE);
                        mi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);

                        ConstraintLayout.LayoutParams manji = new ConstraintLayout.LayoutParams((int)(dp_to_px(110)), (int)(dp_to_px(75)));
                        manji.bottomToBottom=R.id.button_mi_bodovi;
                        manji.startToEnd=R.id.divider;
                        manji.topToTop=R.id.button_mi_bodovi;
                        manji.leftMargin=dp_to_px(24);

                        vi_bodovi.setTextColor(Color.parseColor("#680E07"));
                        vi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
                        mi_bodovi.setLayoutParams(veci);
                        vi_bodovi.setLayoutParams(manji);
                        Button[] array = new Button[]{mi_bodovi, vi_bodovi};
                        for (int i=0; i < array.length; i++){
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) array[i].getLayoutParams();
                            params.height *= scaleh;
                            params.width *= scalew;
                            params.topMargin *= scaleh;
                            params.bottomMargin *= scaleh;
                            params.rightMargin *= scalew;
                            params.leftMargin *= scalew;
                            array[i].setLayoutParams(params);
                        }
                    }
                }
            }
        });
    }
    public void izracunaj(){
        pali[0]=false;
        int mi_zvanja_int_priznato = broj_zvanje_20_mi[0]*20+broj_zvanje_50_mi[0]*50+broj_zvanje_100_mi[0]*100+broj_zvanje_150_mi[0]*150+broj_zvanje_200_mi[0]*200;
        int vi_zvanja_int_priznato = broj_zvanje_20_vi[0]*20+broj_zvanje_50_vi[0]*50+broj_zvanje_100_vi[0]*100+broj_zvanje_150_vi[0]*150+broj_zvanje_200_vi[0]*200;
        int mi_zvanja_int = broj_zvanje_20_mi[0]*20+broj_zvanje_50_mi[0]*50+broj_zvanje_100_mi[0]*100+broj_zvanje_150_mi[0]*150+broj_zvanje_200_mi[0]*200;
        int vi_zvanja_int = broj_zvanje_20_vi[0]*20+broj_zvanje_50_vi[0]*50+broj_zvanje_100_vi[0]*100+broj_zvanje_150_vi[0]*150+broj_zvanje_200_vi[0]*200;
        if (stiglja[0]){
            if (!stiglja[1]){
                mi_zvanja_int_priznato = mi_zvanja_int+vi_zvanja_int+90;
                mi_zvanja_int += 90;
                vi_zvanja_int_priznato = 0;
                mi_bodovi.setText("162");
                vi_bodovi.setText("0");
            }
            if (stiglja[1]){
                vi_zvanja_int_priznato = mi_zvanja_int+vi_zvanja_int+90;
                vi_zvanja_int += 90;
                mi_zvanja_int_priznato = 0;
                vi_bodovi.setText("162");
                mi_bodovi.setText("0");
            }
        }
        if (zvali[0]){
            if (!zvali[1]){
                if (Integer.parseInt(String.valueOf(mi_bodovi.getText()))+mi_zvanja_int_priznato<=Integer.parseInt(String.valueOf(vi_bodovi.getText()))+vi_zvanja_int_priznato){
                    pali[0]=true;
                    mi_zvanja_int_priznato = 0;
                    vi_zvanja_int_priznato = mi_zvanja_int+vi_zvanja_int;
                    mi_suma.setText("Σ 0");
                    vi_suma.setText("Σ "+String.valueOf(162+mi_zvanja_int+vi_zvanja_int));
                }
                if (Integer.parseInt(String.valueOf(mi_bodovi.getText()))+mi_zvanja_int_priznato>Integer.parseInt(String.valueOf(vi_bodovi.getText()))+vi_zvanja_int_priznato){
                    mi_suma.setText("Σ "+String.valueOf(Integer.parseInt(String.valueOf(mi_bodovi.getText()))+mi_zvanja_int_priznato));
                    vi_suma.setText("Σ "+String.valueOf(Integer.parseInt(String.valueOf(vi_bodovi.getText()))+vi_zvanja_int_priznato));
                }
            }
            if (zvali[1]){
                if (Integer.parseInt(String.valueOf(mi_bodovi.getText()))+mi_zvanja_int_priznato>=Integer.parseInt(String.valueOf(vi_bodovi.getText()))+vi_zvanja_int_priznato){
                    pali[0]=true;
                    vi_zvanja_int_priznato = 0;
                    mi_zvanja_int_priznato = mi_zvanja_int+vi_zvanja_int;
                    vi_suma.setText("Σ 0");
                    mi_suma.setText("Σ "+String.valueOf(162+mi_zvanja_int+vi_zvanja_int));
                }
                if (Integer.parseInt(String.valueOf(mi_bodovi.getText()))+mi_zvanja_int_priznato<Integer.parseInt(String.valueOf(vi_bodovi.getText()))+vi_zvanja_int_priznato){
                    mi_suma.setText("Σ "+String.valueOf(Integer.parseInt(String.valueOf(mi_bodovi.getText()))+mi_zvanja_int_priznato));
                    vi_suma.setText("Σ "+String.valueOf(Integer.parseInt(String.valueOf(vi_bodovi.getText()))+vi_zvanja_int_priznato));
                }
            }
        }
        else {
            mi_suma.setText("Σ "+String.valueOf(Integer.parseInt(String.valueOf(mi_bodovi.getText()))+mi_zvanja_int_priznato));
            vi_suma.setText("Σ "+String.valueOf(Integer.parseInt(String.valueOf(vi_bodovi.getText()))+vi_zvanja_int_priznato));
        }
        mi_zvanja.setText("+"+String.valueOf(mi_zvanja_int_priznato));
        vi_zvanja.setText("+"+String.valueOf(vi_zvanja_int_priznato));
    }
    @SuppressLint("UnsafeOptInUsageError")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        scaleh = getResources().getDisplayMetrics().heightPixels/2072.0;
        scalew = getResources().getDisplayMetrics().widthPixels/1080.0;
        if (scaleh>1.0){
            scaleh = 1.0;
        }
        if (scalew>1.0){
            scalew = 1.0;
        }
        if (scaleh<0.5){
            scaleh = 0.7;
        }
        else if (scaleh<1.0){
            scaleh = 0.8;
        }
        if (scalew<0.5){
            scalew = 0.7;
        }
        else if (scalew<1.0){
            scalew = 0.8;
        }
        Bundle extras = getIntent().getExtras();
        newString = extras.getString(Intent.EXTRA_TEXT);
        dosao_iz_pobjede = extras.getBoolean("NOVA");
        vi_bodovi = findViewById(R.id.button_vi_bodovi);
        mi_bodovi = findViewById(R.id.button_mi_bodovi);
        ponisti = findViewById(R.id.button20);
        jedan = findViewById(R.id.button);
        dva = findViewById(R.id.button2);
        tri = findViewById(R.id.button3);
        cetiri = findViewById(R.id.button4);
        pet = findViewById(R.id.button5);
        sest = findViewById(R.id.button6);
        sedam = findViewById(R.id.button7);
        osam = findViewById(R.id.button8);
        devet = findViewById(R.id.button9);
        nula = findViewById(R.id.button11);
        brisanje = findViewById(R.id.button12);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = vi_bodovi.getContext().getTheme();
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        int color = ContextCompat.getColor(vi_bodovi.getContext(), typedValue.resourceId);
        color_text = Color.parseColor("#"+Integer.toHexString(color).substring(2));
        TypedValue typedValue1 = new TypedValue();
        Resources.Theme theme1 = vi_bodovi.getContext().getTheme();
        theme1.resolveAttribute(android.R.attr.textColorPrimaryInverse, typedValue1, true);
        int color1 = ContextCompat.getColor(vi_bodovi.getContext(), typedValue1.resourceId);
        color_text1 = Color.parseColor("#"+Integer.toHexString(color1).substring(2));
        zvanje_20_button = findViewById(R.id.button13);
        zvanje_50_button = findViewById(R.id.button14);
        zvanje_100_button = findViewById(R.id.button15);
        zvanje_150_button = findViewById(R.id.button16);
        zvanje_200_button = findViewById(R.id.button17);

        mi_zvali = findViewById(R.id.button21);
        vi_zvali = findViewById(R.id.button22);

        stiglja_button = findViewById(R.id.button18);

        mi_zvanja = findViewById(R.id.mi_zvanja);
        mi_suma = findViewById(R.id.mi_suma);
        vi_zvanja = findViewById(R.id.vi_zvanja);
        vi_suma = findViewById(R.id.vi_suma);
        x = findViewById(R.id.button10);
        mi_20 = setbadge(zvanje_20_button, 0, false, "#000000", BadgeDrawable.TOP_END);
        vi_20 = setbadge(zvanje_20_button, 0, false, "#000000", BadgeDrawable.BOTTOM_END);
        mi_50 = setbadge(zvanje_50_button, 0, false, "#000000", BadgeDrawable.TOP_END);
        vi_50 = setbadge(zvanje_50_button, 0, false, "#000000", BadgeDrawable.BOTTOM_END);
        mi_100 = setbadge(zvanje_100_button, 0, false, "#000000", BadgeDrawable.TOP_END);
        vi_100 = setbadge(zvanje_100_button, 0, false, "#000000", BadgeDrawable.BOTTOM_END);
        mi_150 = setbadge(zvanje_150_button, 0, false, "#000000", BadgeDrawable.TOP_END);
        vi_150 = setbadge(zvanje_150_button, 0, false, "#000000", BadgeDrawable.BOTTOM_END);
        mi_200 = setbadge(zvanje_200_button, 0, false, "#000000", BadgeDrawable.TOP_END);
        vi_200 = setbadge(zvanje_200_button, 0, false, "#000000", BadgeDrawable.BOTTOM_END);

        if (Objects.equals(newString, "VI")){
            fokus[0] = 1;
            int parentId = ((View) vi_bodovi.getParent()).getId();

            ConstraintLayout.LayoutParams dividerlayout = new ConstraintLayout.LayoutParams((int)(dp_to_px(409)), (int)((dp_to_px(1))));
            dividerlayout.topMargin=dp_to_px(8);
            dividerlayout.bottomMargin=dp_to_px(8);
            dividerlayout.topToBottom=R.id.vi_suma;
            dividerlayout.startToStart=parentId;
            dividerlayout.endToEnd=parentId;
            dividerlayout.bottomToTop=R.id.button14;
            View divider = findViewById(R.id.divider2);
            divider.setLayoutParams(dividerlayout);

            ConstraintLayout.LayoutParams veci = new ConstraintLayout.LayoutParams((int)(dp_to_px(150)), (int)(dp_to_px(90)));
            veci.startToStart=R.id.divider;
            veci.topToTop=parentId;
            veci.topMargin=dp_to_px(8);
            vi_bodovi.setTextColor(Color.WHITE);
            vi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
            ConstraintLayout.LayoutParams manji = new ConstraintLayout.LayoutParams((int)(dp_to_px(110)), (int)(dp_to_px(75)));
            manji.bottomToBottom=R.id.button_vi_bodovi;
            manji.endToEnd=R.id.divider;
            manji.topToTop=R.id.button_vi_bodovi;
            manji.rightMargin=dp_to_px(24);
            mi_bodovi.setTextColor(Color.rgb(16,65,104));
            mi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
            vi_bodovi.setLayoutParams(veci);
            mi_bodovi.setLayoutParams(manji);
            Button[] array = new Button[]{mi_bodovi, vi_bodovi};
            for (int i=0; i < array.length; i++){
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) array[i].getLayoutParams();
                params.height *= scaleh;
                params.width *= scalew;
                params.topMargin *= scaleh;
                params.bottomMargin *= scaleh;
                params.rightMargin *= scalew;
                params.leftMargin *= scalew;
                array[i].setLayoutParams(params);
            }
        }
        else{
            fokus[0] = 0;
            int parentId = ((View) mi_bodovi.getParent()).getId();

            ConstraintLayout.LayoutParams dividerlayout = new ConstraintLayout.LayoutParams((int)(dp_to_px(409)), (int)(dp_to_px(1)));
            dividerlayout.topMargin=dp_to_px(8);
            dividerlayout.bottomMargin=dp_to_px(8);
            dividerlayout.topToBottom=R.id.mi_suma;
            dividerlayout.startToStart=parentId;
            dividerlayout.endToEnd=parentId;
            dividerlayout.bottomToTop=R.id.button14;
            View divider = findViewById(R.id.divider2);
            divider.setLayoutParams(dividerlayout);

            ConstraintLayout.LayoutParams veci = new ConstraintLayout.LayoutParams((int)(dp_to_px(150)), (int)(dp_to_px(90)));
            veci.endToStart=R.id.divider;
            veci.topToTop=parentId;
            veci.topMargin=dp_to_px(8);
            mi_bodovi.setTextColor(Color.WHITE);
            mi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);

            ConstraintLayout.LayoutParams manji = new ConstraintLayout.LayoutParams((int)(dp_to_px(110)), (int)(dp_to_px(75)));
            manji.bottomToBottom=R.id.button_mi_bodovi;
            manji.startToEnd=R.id.divider;
            manji.topToTop=R.id.button_mi_bodovi;
            manji.leftMargin=dp_to_px(24);

            vi_bodovi.setTextColor(Color.parseColor("#680E07"));
            vi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
            mi_bodovi.setLayoutParams(veci);
            vi_bodovi.setLayoutParams(manji);
            Button[] array = new Button[]{mi_bodovi, vi_bodovi};
            for (int i=0; i < array.length; i++){
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) array[i].getLayoutParams();
                params.height *= scaleh;
                params.width *= scalew;
                params.topMargin *= scaleh;
                params.bottomMargin *= scaleh;
                params.rightMargin *= scalew;
                params.leftMargin *= scalew;
                array[i].setLayoutParams(params);
            }
        }
        mi_bodovi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fokus[0] = 0;
                int parentId = ((View) mi_bodovi.getParent()).getId();

                ConstraintLayout.LayoutParams dividerlayout = new ConstraintLayout.LayoutParams((int)(dp_to_px(409)), (int)(dp_to_px(1)));
                dividerlayout.topMargin=dp_to_px(8);
                dividerlayout.bottomMargin=dp_to_px(8);
                dividerlayout.topToBottom=R.id.mi_suma;
                dividerlayout.startToStart=parentId;
                dividerlayout.endToEnd=parentId;
                dividerlayout.bottomToTop=R.id.button14;
                View divider = findViewById(R.id.divider2);
                divider.setLayoutParams(dividerlayout);

                ConstraintLayout.LayoutParams veci = new ConstraintLayout.LayoutParams((int)(dp_to_px(150)), (int)(dp_to_px(90)));
                veci.endToStart=R.id.divider;
                veci.topToTop=parentId;
                veci.topMargin=dp_to_px(8);
                mi_bodovi.setTextColor(Color.WHITE);
                mi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);

                ConstraintLayout.LayoutParams manji = new ConstraintLayout.LayoutParams((int)(dp_to_px(110)), (int)(dp_to_px(75)));
                manji.bottomToBottom=R.id.button_mi_bodovi;
                manji.startToEnd=R.id.divider;
                manji.topToTop=R.id.button_mi_bodovi;
                manji.leftMargin=dp_to_px(24);

                vi_bodovi.setTextColor(Color.parseColor("#680E07"));
                vi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
                mi_bodovi.setLayoutParams(veci);
                vi_bodovi.setLayoutParams(manji);
                Button[] array = new Button[]{mi_bodovi, vi_bodovi};
                for (int i=0; i < array.length; i++){
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) array[i].getLayoutParams();
                    params.height *= scaleh;
                    params.width *= scalew;
                    params.topMargin *= scaleh;
                    params.bottomMargin *= scaleh;
                    params.rightMargin *= scalew;
                    params.leftMargin *= scalew;
                    array[i].setLayoutParams(params);
                }
            }
        });
        vi_bodovi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fokus[0] = 1;
                int parentId = ((View) vi_bodovi.getParent()).getId();

                ConstraintLayout.LayoutParams dividerlayout = new ConstraintLayout.LayoutParams((int)(dp_to_px(409)),(int)(dp_to_px(1)));
                dividerlayout.topMargin=dp_to_px(8);
                dividerlayout.bottomMargin=dp_to_px(8);
                dividerlayout.topToBottom=R.id.vi_suma;
                dividerlayout.startToStart=parentId;
                dividerlayout.endToEnd=parentId;
                dividerlayout.bottomToTop=R.id.button14;
                View divider = findViewById(R.id.divider2);
                divider.setLayoutParams(dividerlayout);

                ConstraintLayout.LayoutParams veci = new ConstraintLayout.LayoutParams((int)(dp_to_px(150)), (int)(dp_to_px(90)));
                veci.startToStart=R.id.divider;
                veci.topToTop=parentId;
                veci.topMargin=dp_to_px(8);
                vi_bodovi.setTextColor(Color.WHITE);
                vi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                ConstraintLayout.LayoutParams manji = new ConstraintLayout.LayoutParams((int)(dp_to_px(110)), (int)(dp_to_px(75)));
                manji.bottomToBottom=R.id.button_vi_bodovi;
                manji.endToEnd=R.id.divider;
                manji.topToTop=R.id.button_vi_bodovi;
                manji.rightMargin=dp_to_px(24);
                mi_bodovi.setTextColor(Color.rgb(16,65,104));
                mi_bodovi.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
                vi_bodovi.setLayoutParams(veci);
                mi_bodovi.setLayoutParams(manji);
                Button[] array = new Button[]{mi_bodovi, vi_bodovi};
                for (int i=0; i < array.length; i++){
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) array[i].getLayoutParams();
                    params.height *= scaleh;
                    params.width *= scalew;
                    params.topMargin *= scaleh;
                    params.bottomMargin *= scaleh;
                    params.rightMargin *= scalew;
                    params.leftMargin *= scalew;
                    array[i].setLayoutParams(params);
                }
            }
        });
        ponisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        View.OnClickListener c = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!zvali[0]) {
                    mi_zvali.setBackgroundColor(Color.parseColor("#2196F3"));
                    vi_zvali.setBackgroundColor(color_text1);
                    zvali[0] = true;
                    zvali[1] = false;
                }
                else if(zvali[0] && zvali[1]){
                    mi_zvali.setBackgroundColor(Color.parseColor("#2196F3"));
                    vi_zvali.setBackgroundColor(color_text1);
                    zvali[0] = true;
                    zvali[1] = false;
                }
                else if(zvali[0] && !zvali[1]){
                    mi_zvali.setBackgroundColor(color_text1);
                    vi_zvali.setBackgroundColor(color_text1);
                    zvali[0] = false;
                    zvali[1] = false;
                }
                izracunaj();
            }
        };
        View.OnClickListener d = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!zvali[0]) {
                    mi_zvali.setBackgroundColor(color_text1);
                    vi_zvali.setBackgroundColor(Color.parseColor("#F42414"));
                    zvali[0] = true;
                    zvali[1] = true;
                }
                else if(zvali[0] && !zvali[1]){
                    mi_zvali.setBackgroundColor(color_text1);
                    vi_zvali.setBackgroundColor(Color.parseColor("#F42414"));
                    zvali[0] = true;
                    zvali[1] = true;
                }
                else if(zvali[0] && zvali[1]){
                    mi_zvali.setBackgroundColor(color_text1);
                    vi_zvali.setBackgroundColor(color_text1);
                    zvali[0] = false;
                    zvali[1] = false;
                }
                izracunaj();
            }
        };
        View.OnLongClickListener b = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String number = "0";
                switch(view.getId()) {
                    case R.id.button13:
                        number = "zvanje20";
                        break;
                    case R.id.button14:
                        number = "zvanje50";
                        break;
                    case R.id.button15:
                        number = "zvanje100";
                        break;
                    case R.id.button16:
                        number = "zvanje150";
                        break;
                    case R.id.button17:
                        number = "zvanje200";
                        break;
                }
                if (number.equals("zvanje20")){
                    if (fokus[0] == 0) {
                        Integer mjesto = BadgeDrawable.TOP_END;
                        String boja = "#2196F3";
                        broj_zvanje_20_mi[0] = 0;
                        if (broj_zvanje_20_mi[0]<=0) {
                            broj_zvanje_20_mi[0] = 0;
                            mi_20.setVisible(false);
                        }
                        else{
                            mi_20.setVisible(true);
                        }
                        mi_20.setNumber(broj_zvanje_20_mi[0]);
                        mi_20.setBackgroundColor(Color.parseColor(boja));
                        mi_20.setBadgeGravity(mjesto);
                        attachBadgeDrawable(mi_20, zvanje_20_button);
                    }
                    if (fokus[0] == 1) {
                        Integer mjesto = BadgeDrawable.BOTTOM_END;
                        String boja = "#F42414";
                        broj_zvanje_20_vi[0] = 0;
                        if (broj_zvanje_20_vi[0]<=0) {
                            broj_zvanje_20_vi[0] = 0;
                            vi_20.setVisible(false);
                        }
                        else{
                            vi_20.setVisible(true);
                        }
                        vi_20.setNumber(broj_zvanje_20_vi[0]);
                        vi_20.setBackgroundColor(Color.parseColor(boja));
                        vi_20.setBadgeGravity(mjesto);
                        attachBadgeDrawable(vi_20, zvanje_20_button);
                    }
                }
                else if (number.equals("zvanje50")){
                    if (fokus[0] == 0) {
                        Integer mjesto = BadgeDrawable.TOP_END;
                        String boja = "#2196F3";
                        broj_zvanje_50_mi[0] = 0;
                        if (broj_zvanje_50_mi[0]<=0) {
                            broj_zvanje_50_mi[0] = 0;
                            mi_50.setVisible(false);
                        }
                        else{
                            mi_50.setVisible(true);
                        }
                        mi_50.setNumber(broj_zvanje_50_mi[0]);
                        mi_50.setBackgroundColor(Color.parseColor(boja));
                        mi_50.setBadgeGravity(mjesto);
                        attachBadgeDrawable(mi_50, zvanje_50_button);
                    }
                    if (fokus[0] == 1) {
                        Integer mjesto = BadgeDrawable.BOTTOM_END;
                        String boja = "#F42414";
                        broj_zvanje_50_vi[0] = 0;
                        if (broj_zvanje_50_vi[0]<=0) {
                            broj_zvanje_50_vi[0] = 0;
                            vi_50.setVisible(false);
                        }
                        else{
                            vi_50.setVisible(true);
                        }
                        vi_50.setNumber(broj_zvanje_50_vi[0]);
                        vi_50.setBackgroundColor(Color.parseColor(boja));
                        vi_50.setBadgeGravity(mjesto);
                        attachBadgeDrawable(vi_50, zvanje_50_button);
                    }
                }
                else if (number.equals("zvanje100")){
                    if (fokus[0] == 0) {
                        Integer mjesto = BadgeDrawable.TOP_END;
                        String boja = "#2196F3";
                        broj_zvanje_100_mi[0] = 0;
                        if (broj_zvanje_100_mi[0]<=0) {
                            broj_zvanje_100_mi[0] = 0;
                            mi_100.setVisible(false);
                        }
                        else{
                            mi_100.setVisible(true);
                        }
                        mi_100.setNumber(broj_zvanje_100_mi[0]);
                        mi_100.setBackgroundColor(Color.parseColor(boja));
                        mi_100.setBadgeGravity(mjesto);
                        attachBadgeDrawable(mi_100, zvanje_100_button);
                    }
                    if (fokus[0] == 1) {
                        Integer mjesto = BadgeDrawable.BOTTOM_END;
                        String boja = "#F42414";
                        broj_zvanje_100_vi[0] = 0;
                        if (broj_zvanje_100_vi[0]<=0) {
                            broj_zvanje_100_vi[0] = 0;
                            vi_100.setVisible(false);
                        }
                        else{
                            vi_100.setVisible(true);
                        }
                        vi_100.setNumber(broj_zvanje_100_vi[0]);
                        vi_100.setBackgroundColor(Color.parseColor(boja));
                        vi_100.setBadgeGravity(mjesto);
                        attachBadgeDrawable(vi_100, zvanje_100_button);
                    }
                }
                else if (number.equals("zvanje150")){
                    if (fokus[0] == 0) {
                        Integer mjesto = BadgeDrawable.TOP_END;
                        String boja = "#2196F3";
                        broj_zvanje_150_mi[0] = 0;
                        if (broj_zvanje_150_mi[0]<=0) {
                            broj_zvanje_150_mi[0] = 0;
                            mi_150.setVisible(false);
                        }
                        else{
                            mi_150.setVisible(true);
                        }
                        mi_150.setNumber(broj_zvanje_150_mi[0]);
                        mi_150.setBackgroundColor(Color.parseColor(boja));
                        mi_150.setBadgeGravity(mjesto);
                        attachBadgeDrawable(mi_150, zvanje_150_button);
                    }
                    if (fokus[0] == 1) {
                        Integer mjesto = BadgeDrawable.BOTTOM_END;
                        String boja = "#F42414";
                        broj_zvanje_150_vi[0] = 0;
                        if (broj_zvanje_150_vi[0]<=0) {
                            broj_zvanje_150_vi[0] = 0;
                            vi_150.setVisible(false);
                        }
                        else{
                            vi_150.setVisible(true);
                        }
                        vi_150.setNumber(broj_zvanje_150_vi[0]);
                        vi_150.setBackgroundColor(Color.parseColor(boja));
                        vi_150.setBadgeGravity(mjesto);
                        attachBadgeDrawable(vi_150, zvanje_150_button);
                    }
                }
                else if (number.equals("zvanje200")){
                    if (fokus[0] == 0) {
                        Integer mjesto = BadgeDrawable.TOP_END;
                        String boja = "#2196F3";
                        broj_zvanje_200_mi[0] = 0;
                        if (broj_zvanje_200_mi[0]<=0) {
                            broj_zvanje_200_mi[0] = 0;
                            mi_200.setVisible(false);
                        }
                        else{
                            mi_200.setVisible(true);
                        }
                        mi_200.setNumber(broj_zvanje_200_mi[0]);
                        mi_200.setBackgroundColor(Color.parseColor(boja));
                        mi_200.setBadgeGravity(mjesto);
                        attachBadgeDrawable(mi_200, zvanje_200_button);
                    }
                    if (fokus[0] == 1) {
                        Integer mjesto = BadgeDrawable.BOTTOM_END;
                        String boja = "#F42414";
                        broj_zvanje_200_vi[0] = 0;
                        if (broj_zvanje_200_vi[0]<=0) {
                            broj_zvanje_200_vi[0] = 0;
                            vi_200.setVisible(false);
                        }
                        else{
                            vi_200.setVisible(true);
                        }
                        vi_200.setNumber(broj_zvanje_200_vi[0]);
                        vi_200.setBackgroundColor(Color.parseColor(boja));
                        vi_200.setBadgeGravity(mjesto);
                        attachBadgeDrawable(vi_200, zvanje_200_button);
                    }
                }
                izracunaj();
                return true;
            }
        };
        View.OnClickListener a = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "0";
                switch(view.getId()) {
                    case R.id.button:
                        number = "1";
                        break;
                    case R.id.button2:
                        number = "2";
                        break;
                    case R.id.button3:
                        number = "3";
                        break;
                    case R.id.button4:
                        number = "4";
                        break;
                    case R.id.button5:
                        number = "5";
                        break;
                    case R.id.button6:
                        number = "6";
                        break;
                    case R.id.button7:
                        number = "7";
                        break;
                    case R.id.button8:
                        number = "8";
                        break;
                    case R.id.button9:
                        number = "9";
                        break;
                    case R.id.button11:
                        number = "0";
                        break;
                    case R.id.button12:
                        number = "-";
                        break;
                    case R.id.button10:
                        number = "x";
                        break;
                    case R.id.button13:
                        number = "zvanje20";
                        break;
                    case R.id.button14:
                        number = "zvanje50";
                        break;
                    case R.id.button15:
                        number = "zvanje100";
                        break;
                    case R.id.button16:
                        number = "zvanje150";
                        break;
                    case R.id.button17:
                        number = "zvanje200";
                        break;
                    case R.id.button18:
                        number = "stiglja";
                        break;
                }
                if (!number.equals("-") && !number.equals("x") && !number.equals("zvanje20") && !number.equals("zvanje50") && !number.equals("zvanje100") && !number.equals("zvanje150") && !number.equals("zvanje200") && !number.equals("stiglja")) {
                    if (fokus[0] == 0) {
                        CharSequence bodovi = mi_bodovi.getText() + number;
                        int number_int = Integer.parseInt(bodovi.toString());
                        if (number_int > 162) {
                            number = "162";
                            number_int = 162;
                        } else {
                            number = String.valueOf(number_int);
                        }
                        mi_bodovi.setText(number);
                        vi_bodovi.setText(String.valueOf(162 - number_int));
                    }
                    if (fokus[0] == 1) {
                        CharSequence bodovi = vi_bodovi.getText() + number;
                        int number_int = Integer.parseInt(bodovi.toString());
                        if (number_int > 162) {
                            number = "162";
                            number_int = 162;
                        } else {
                            number = String.valueOf(number_int);
                        }
                        vi_bodovi.setText(number);
                        mi_bodovi.setText(String.valueOf(162 - number_int));
                    }
                    stiglja[0] = false;
                }
                else if (!number.equals("x") && !number.equals("zvanje20") && !number.equals("zvanje50") && !number.equals("zvanje100") && !number.equals("zvanje150") && !number.equals("zvanje200") && !number.equals("stiglja")) {
                    if (fokus[0] == 0) {
                        CharSequence bodovi = mi_bodovi.getText();
                        int number_int = Integer.parseInt(bodovi.toString());
                        number_int = number_int/10;
                        Log.d("aaa", String.valueOf(bodovi));
                        Log.d("aaa", String.valueOf(number_int));
                        mi_bodovi.setText(String.valueOf(number_int));
                        vi_bodovi.setText(String.valueOf(162 - number_int));
                    }
                    if (fokus[0] == 1) {
                        CharSequence bodovi = vi_bodovi.getText();
                        int number_int = Integer.parseInt(bodovi.toString());
                        number_int = number_int/10;
                        Log.d("aaa", String.valueOf(bodovi));
                        Log.d("aaa", String.valueOf(number_int));
                        vi_bodovi.setText(String.valueOf(number_int));
                        mi_bodovi.setText(String.valueOf(162 - number_int));
                    }
                    stiglja[0] = false;
                }
                else if (!number.equals("zvanje20") && !number.equals("zvanje50") && !number.equals("zvanje100") && !number.equals("zvanje150") && !number.equals("zvanje200") && !number.equals("stiglja")) {
                    vi_bodovi.setText(String.valueOf(0));
                    mi_bodovi.setText(String.valueOf(0));
                    mi_zvanja.setText("+0");
                    vi_zvanja.setText("+0");
                    mi_suma.setText("Σ 0");
                    vi_suma.setText("Σ 0");
                    broj_zvanje_20_mi[0] = 0;
                    broj_zvanje_20_vi[0] = 0;
                    broj_zvanje_50_mi[0] = 0;
                    broj_zvanje_50_vi[0] = 0;
                    broj_zvanje_100_mi[0] = 0;
                    broj_zvanje_100_vi[0] = 0;
                    broj_zvanje_150_mi[0] = 0;
                    broj_zvanje_150_vi[0] = 0;
                    broj_zvanje_200_mi[0] = 0;
                    broj_zvanje_200_vi[0] = 0;
                    mi_20.setVisible(false);
                    mi_20.setNumber(0);
                    vi_20.setVisible(false);
                    vi_20.setNumber(0);
                    mi_50.setVisible(false);
                    mi_50.setNumber(0);
                    vi_50.setVisible(false);
                    vi_50.setNumber(0);
                    mi_100.setVisible(false);
                    mi_100.setNumber(0);
                    vi_100.setVisible(false);
                    vi_100.setNumber(0);
                    mi_150.setVisible(false);
                    mi_150.setNumber(0);
                    vi_150.setVisible(false);
                    vi_150.setNumber(0);
                    mi_200.setVisible(false);
                    mi_200.setNumber(0);
                    vi_200.setVisible(false);
                    vi_200.setNumber(0);
                    stiglja[0] = false;
                }
                else if (number.equals("zvanje20")){
                    if (fokus[0] == 0) {
                        Integer mjesto = BadgeDrawable.TOP_END;
                        String boja = "#2196F3";
                        broj_zvanje_20_mi[0] += 1;
                        if (broj_zvanje_20_mi[0]>5){
                            broj_zvanje_20_mi[0]=5;
                        }
                        mi_20.setVisible(true);
                        mi_20.setNumber(broj_zvanje_20_mi[0]);
                        mi_20.setBackgroundColor(Color.parseColor(boja));
                        mi_20.setBadgeGravity(mjesto);
                        attachBadgeDrawable(mi_20, zvanje_20_button);
                    }
                    if (fokus[0] == 1) {
                        Integer mjesto = BadgeDrawable.BOTTOM_END;
                        String boja = "#F42414";
                        broj_zvanje_20_vi[0] += 1;
                        if (broj_zvanje_20_vi[0]>5){
                            broj_zvanje_20_vi[0]=5;
                        }
                        vi_20.setVisible(true);
                        vi_20.setNumber(broj_zvanje_20_vi[0]);
                        vi_20.setBackgroundColor(Color.parseColor(boja));
                        vi_20.setBadgeGravity(mjesto);
                        attachBadgeDrawable(vi_20, zvanje_20_button);
                    }
                }
                else if (number.equals("zvanje50")){
                    if (fokus[0] == 0) {
                        Integer mjesto = BadgeDrawable.TOP_END;
                        String boja = "#2196F3";
                        broj_zvanje_50_mi[0] += 1;
                        if (broj_zvanje_50_mi[0] >4){
                            broj_zvanje_50_mi[0] =4;
                        }
                        mi_50.setVisible(true);
                        mi_50.setNumber(broj_zvanje_50_mi[0]);
                        mi_50.setBackgroundColor(Color.parseColor(boja));
                        mi_50.setBadgeGravity(mjesto);
                        attachBadgeDrawable(mi_50, zvanje_50_button);
                    }
                    if (fokus[0] == 1) {
                        Integer mjesto = BadgeDrawable.BOTTOM_END;
                        String boja = "#F42414";
                        broj_zvanje_50_vi[0] += 1;
                        if (broj_zvanje_50_vi[0] >4){
                            broj_zvanje_50_vi[0] =4;
                        }
                        vi_50.setVisible(true);
                        vi_50.setNumber(broj_zvanje_50_vi[0]);
                        vi_50.setBackgroundColor(Color.parseColor(boja));
                        vi_50.setBadgeGravity(mjesto);
                        attachBadgeDrawable(vi_50, zvanje_50_button);
                    }
                }
                else if (number.equals("zvanje100")){
                    if (fokus[0] == 0) {
                        Integer mjesto = BadgeDrawable.TOP_END;
                        String boja = "#2196F3";
                        broj_zvanje_100_mi[0] += 1;
                        if (broj_zvanje_100_mi[0] >4){
                            broj_zvanje_100_mi[0] =4;
                        }
                        mi_100.setVisible(true);
                        mi_100.setNumber(broj_zvanje_100_mi[0]);
                        mi_100.setBackgroundColor(Color.parseColor(boja));
                        mi_100.setBadgeGravity(mjesto);
                        attachBadgeDrawable(mi_100, zvanje_100_button);
                    }
                    if (fokus[0] == 1) {
                        Integer mjesto = BadgeDrawable.BOTTOM_END;
                        String boja = "#F42414";
                        broj_zvanje_100_vi[0] += 1;
                        if (broj_zvanje_100_vi[0] >4){
                            broj_zvanje_100_vi[0] =4;
                        }
                        vi_100.setVisible(true);
                        vi_100.setNumber(broj_zvanje_100_vi[0]);
                        vi_100.setBackgroundColor(Color.parseColor(boja));
                        vi_100.setBadgeGravity(mjesto);
                        attachBadgeDrawable(vi_100, zvanje_100_button);
                    }
                }
                else if (number.equals("zvanje150")){
                    if (fokus[0] == 0) {
                        Integer mjesto = BadgeDrawable.TOP_END;
                        String boja = "#2196F3";
                        broj_zvanje_150_mi[0] += 1;
                        if (broj_zvanje_150_mi[0] >1){
                            broj_zvanje_150_mi[0] =1;
                        }
                        mi_150.setVisible(true);
                        mi_150.setNumber(broj_zvanje_150_mi[0]);
                        mi_150.setBackgroundColor(Color.parseColor(boja));
                        mi_150.setBadgeGravity(mjesto);
                        attachBadgeDrawable(mi_150, zvanje_150_button);
                    }
                    if (fokus[0] == 1) {
                        Integer mjesto = BadgeDrawable.BOTTOM_END;
                        String boja = "#F42414";
                        broj_zvanje_150_vi[0] += 1;
                        if (broj_zvanje_150_vi[0] >1){
                            broj_zvanje_150_vi[0] =1;
                        }
                        vi_150.setVisible(true);
                        vi_150.setNumber(broj_zvanje_150_vi[0]);
                        vi_150.setBackgroundColor(Color.parseColor(boja));
                        vi_150.setBadgeGravity(mjesto);
                        attachBadgeDrawable(vi_150, zvanje_150_button);
                    }
                }
                else if (number.equals("zvanje200")){
                    if (fokus[0] == 0) {
                        Integer mjesto = BadgeDrawable.TOP_END;
                        String boja = "#2196F3";
                        broj_zvanje_200_mi[0] += 1;
                        if (broj_zvanje_200_mi[0] >1){
                            broj_zvanje_200_mi[0] =1;
                        }
                        mi_200.setVisible(true);
                        mi_200.setNumber(broj_zvanje_200_mi[0]);
                        mi_200.setBackgroundColor(Color.parseColor(boja));
                        mi_200.setBadgeGravity(mjesto);
                        attachBadgeDrawable(mi_200, zvanje_200_button);
                    }
                    if (fokus[0] == 1) {
                        Integer mjesto = BadgeDrawable.BOTTOM_END;
                        String boja = "#F42414";
                        broj_zvanje_200_vi[0] += 1;
                        if (broj_zvanje_200_vi[0] >1){
                            broj_zvanje_200_vi[0] =1;
                        }
                        vi_200.setVisible(true);
                        vi_200.setNumber(broj_zvanje_200_vi[0]);
                        vi_200.setBackgroundColor(Color.parseColor(boja));
                        vi_200.setBadgeGravity(mjesto);
                        attachBadgeDrawable(vi_200, zvanje_200_button);
                    }
                }
                else if (number.equals("stiglja")){
                    if (stiglja[0]){
                        stiglja[0] = false;
                        stiglja_button.setBackgroundColor(color_text1);
                    }
                    else if (!stiglja[0]){
                        if (fokus[0] == 0) {
                            vi_bodovi.setText(String.valueOf(0));
                            mi_bodovi.setText(String.valueOf(162));
                            stiglja[0] = true;
                            stiglja[1] = false;
                            stiglja_button.setBackgroundColor(Color.parseColor("#2196F3"));
                        }
                        if (fokus[0] == 1) {
                            mi_bodovi.setText(String.valueOf(0));
                            vi_bodovi.setText(String.valueOf(162));
                            stiglja[0] = true;
                            stiglja[1] = true;
                            stiglja_button.setBackgroundColor(Color.parseColor("#F42414"));
                        }
                    }

                }
                if (!stiglja[0]){
                    stiglja_button.setBackgroundColor(color_text1);
                }
                izracunaj();
            }
        };
        jedan.setOnClickListener(a);
        dva.setOnClickListener(a);
        tri.setOnClickListener(a);
        cetiri.setOnClickListener(a);
        pet.setOnClickListener(a);
        sest.setOnClickListener(a);
        sedam.setOnClickListener(a);
        osam.setOnClickListener(a);
        devet.setOnClickListener(a);
        nula.setOnClickListener(a);
        brisanje.setOnClickListener(a);
        x.setOnClickListener(a);
        zvanje_20_button.setOnClickListener(a);
        zvanje_50_button.setOnClickListener(a);
        zvanje_100_button.setOnClickListener(a);
        zvanje_150_button.setOnClickListener(a);
        zvanje_200_button.setOnClickListener(a);
        zvanje_20_button.setOnLongClickListener(b);
        zvanje_50_button.setOnLongClickListener(b);
        zvanje_100_button.setOnLongClickListener(b);
        zvanje_150_button.setOnLongClickListener(b);
        zvanje_200_button.setOnLongClickListener(b);
        stiglja_button.setOnClickListener(a);
        vi_zvali.setOnClickListener(d);
        mi_zvali.setOnClickListener(c);
        findViewById(R.id.button19).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if (!(String.valueOf(mi_bodovi.getText()).equals("0") && String.valueOf(vi_bodovi.getText()).equals("0"))) {
                    izracunaj();
                    if (edit == -1) {
                        if (dosao_iz_pobjede){
                            FirstFragment.recyclerView.setVisibility(View.VISIBLE);
                            FirstFragment.sv.setVisibility(View.GONE);
                            FirstFragment.pobjedaa = false;
                            FirstFragment.igre.clear();
                            igradapter.notifyDataSetChanged();
                        }
                        if (FirstFragment.dijeli != -1) {
                            FirstFragment.dijeli += 1;
                        }
                        Igre igra = new Igre(pali, zvali, stiglja, broj_zvanje_20_mi, broj_zvanje_20_vi, broj_zvanje_50_mi, broj_zvanje_50_vi, broj_zvanje_100_mi, broj_zvanje_100_vi, broj_zvanje_150_mi, broj_zvanje_150_vi, broj_zvanje_200_mi, broj_zvanje_200_vi, mi_zvanja.getText().subSequence(1, mi_zvanja.getText().length()), vi_zvanja.getText().subSequence(1, vi_zvanja.getText().length()), mi_bodovi.getText(), vi_bodovi.getText(), mi_suma.getText().subSequence(2, mi_suma.getText().length()), vi_suma.getText().subSequence(2, vi_suma.getText().length()));
                        FirstFragment.igre.add(igra);
                        Log.d("bug", String.valueOf(IgraAdapter.localDataSet.size() - 1));
                        igradapter.notifyItemInserted(IgraAdapter.localDataSet.size() - 1);
                        finish();
                    } else {
                        Igre igra = new Igre(pali, zvali, stiglja, broj_zvanje_20_mi, broj_zvanje_20_vi, broj_zvanje_50_mi, broj_zvanje_50_vi, broj_zvanje_100_mi, broj_zvanje_100_vi, broj_zvanje_150_mi, broj_zvanje_150_vi, broj_zvanje_200_mi, broj_zvanje_200_vi, mi_zvanja.getText().subSequence(1, mi_zvanja.getText().length()), vi_zvanja.getText().subSequence(1, vi_zvanja.getText().length()), mi_bodovi.getText(), vi_bodovi.getText(), mi_suma.getText().subSequence(2, mi_suma.getText().length()), vi_suma.getText().subSequence(2, vi_suma.getText().length()));
                        FirstFragment.igre.set(edit, igra);
                        igradapter.notifyItemChanged(edit, igra);
                        finish();
                    }
                }
            }
        });
        Log.d("aaaaaaaaaaaa", String.valueOf(scalew));
        Log.d("aaaaaaaaaaaa", String.valueOf(scaleh));
        Button[] array = new Button[]{ponisti, jedan, dva, tri, cetiri, pet, sest, sedam, osam, devet, nula, brisanje, zvanje_20_button, zvanje_50_button, zvanje_100_button, zvanje_150_button, zvanje_200_button, mi_zvali, vi_zvali, stiglja_button, x, findViewById(R.id.button19)};
        for (int i=0; i < array.length; i++){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) array[i].getLayoutParams();
            params.height *= scaleh;
            params.width *= scalew;
            params.topMargin *= scaleh;
            params.bottomMargin *= scaleh;
            params.rightMargin *= scalew;
            params.leftMargin *= scalew;
            array[i].setLayoutParams(params);
        }
        izracunaj();
    }
}