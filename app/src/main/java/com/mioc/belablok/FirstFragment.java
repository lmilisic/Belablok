package com.mioc.belablok;

import static com.google.android.material.badge.BadgeUtils.attachBadgeDrawable;
import static com.mioc.belablok.MainActivity.save;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.mioc.belablok.databinding.FragmentFirstBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FirstFragment extends Fragment {


    private FragmentFirstBinding binding;
    static ArrayList<Igre> igre  = new ArrayList<>();
    static IgraAdapter igradapter;
    static RecyclerView recyclerView;
    static TextView text_mi = null;
    static TextView text_vi = null;
    static TextView razlika = null;
    static TextView razlika_mi = null;
    static TextView razlika_vi = null;
    static TextView mi_badge;
    static TextView vi_badge;
    static TextView pobjeda;
    static TextView mi_sve_suma;
    static TextView vi_sve_suma;
    static TextView mi_sve_zvanja;
    static TextView vi_sve_zvanja;
    static TextView mi_sve_stiglje;
    static TextView vi_sve_stiglje;
    static TextView mi_sve_pali;
    static TextView vi_sve_pali;
    Button ponisti;
    static ScrollView sv;
    static ImageView djelitelj;


    static Boolean pobjedaa = false;
    public static Boolean pobjednik = false;
    public static Integer dijeli_prosli = 0;
    public static Integer dijeli = -1;
    public static Integer kraj = 1001;
    public static Integer pobjede_mi = 0;
    public static Integer pobjede_vi = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        recyclerView = (RecyclerView) binding.getRoot().findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MainActivity.load();
        igradapter = new IgraAdapter(igre);
        recyclerView.setAdapter(igradapter);
        return binding.getRoot();
    }

    @SuppressLint("UnsafeOptInUsageError")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text_mi = view.findViewById(R.id.textview_mi_bodovi);
        text_vi = view.findViewById(R.id.textview_vi_bodovi);
        razlika = view.findViewById(R.id.razlika);
        razlika_mi = view.findViewById(R.id.razlika_mi);
        razlika_vi = view.findViewById(R.id.razlika_vi);
        Button mi = view.findViewById(R.id.button_mi);
        mi_badge = view.findViewById(R.id.textView17);
        vi_badge = view.findViewById(R.id.textView18);
        pobjeda = view.findViewById(R.id.textView4);
        mi_sve_suma = view.findViewById(R.id.textView8);
        vi_sve_suma = view.findViewById(R.id.textView10);
        mi_sve_zvanja = view.findViewById(R.id.textView11);
        vi_sve_zvanja = view.findViewById(R.id.textView13);
        mi_sve_stiglje = view.findViewById(R.id.textView14);
        vi_sve_stiglje = view.findViewById(R.id.textView16);
        sv = view.findViewById(R.id.sv);
        mi_sve_pali = view.findViewById(R.id.textView3);
        vi_sve_pali = view.findViewById(R.id.textView6);
        ponisti = view.findViewById(R.id.button24);
        djelitelj = view.findViewById(R.id.imageView2);
        mi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SecondActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "MI");
                intent.putExtra("EDIT", -1);
                if (pobjedaa){
                    intent.putExtra("NOVA", pobjedaa);
                }else{
                    intent.putExtra("NOVA", pobjedaa);
                }
                getActivity().startActivity(intent);
            }
        });
        Button vi = view.findViewById(R.id.button_vi);
        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SecondActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "VI");
                intent.putExtra("EDIT", -1);
                if (pobjedaa){
                    intent.putExtra("NOVA", pobjedaa);
                }else{
                    intent.putExtra("NOVA", pobjedaa);
                }
                getActivity().startActivity(intent);
            }
        });
        ponisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                sv.setVisibility(View.GONE);
                FirstFragment.igre.remove(FirstFragment.igre.size()-1);
                igradapter.notifyItemRemoved(IgraAdapter.localDataSet.size());
                Boolean temp = pobjednik;
                dijeli = dijeli_prosli;
                pobjedaa = false;
                if (!temp){
                    pobjede_mi -= 1;
                }
                if (temp){
                    pobjede_vi -= 1;
                }
                Resume();
            }
        });
        djelitelj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dijeli<=-1){dijeli = -1;}
                dijeli += 1;
                dijeli = dijeli%4;
                if (dijeli==-1){djelitelj.setImageResource(R.mipmap.selected_no);}
                if (dijeli==0){djelitelj.setImageResource(R.mipmap.selected_0);}
                if (dijeli==1){djelitelj.setImageResource(R.mipmap.selected_1);}
                if (dijeli==2){djelitelj.setImageResource(R.mipmap.selected_2);}
                if (dijeli==3){djelitelj.setImageResource(R.mipmap.selected_3);}
                save(igre, pobjedaa, pobjednik, dijeli_prosli, dijeli, kraj, pobjede_mi, pobjede_vi);
            }
        });
        djelitelj.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dijeli = -1;
                djelitelj.setImageResource(R.mipmap.selected_no);
                save(igre, pobjedaa, pobjednik, dijeli_prosli, dijeli, kraj, pobjede_mi, pobjede_vi);
                return true;
            }
        });
        Resume();
    }
    Integer last = 0;
    @SuppressLint("UnsafeOptInUsageError")
    @Override
    public void onResume() {
        super.onResume();
        Resume();
    }
    public static void Resume(){
        save(igre, pobjedaa, pobjednik, dijeli_prosli, dijeli, kraj, pobjede_mi, pobjede_vi);
        Log.d("aaa", "resume");
        Log.d("aaa", String.valueOf(igradapter.getItemCount()));
        IgraAdapter.localDataSet = igre;
        Integer zbroj_mi = 0;
        Integer zbroj_vi = 0;
        for (int i = 0; i < IgraAdapter.localDataSet.size(); i++) {
            zbroj_mi += Integer.parseInt(IgraAdapter.localDataSet.get(i).mi_suma);
            zbroj_vi += Integer.parseInt(IgraAdapter.localDataSet.get(i).vi_suma);
        }
        if (text_mi != null && text_vi != null) {
            text_mi.setText(String.valueOf(zbroj_mi));
            text_vi.setText(String.valueOf(zbroj_vi));
            if (zbroj_mi > zbroj_vi) {
                text_mi.setTextColor(Color.parseColor("#2196F3"));
                text_vi.setTextColor(Color.parseColor("#000000"));
            }
            if (zbroj_vi > zbroj_mi) {
                text_vi.setTextColor(Color.parseColor("#F42414"));
                text_mi.setTextColor(Color.parseColor("#000000"));
            }
            if (zbroj_vi.equals(zbroj_mi)) {
                text_mi.setTextColor(Color.parseColor("#000000"));
                text_vi.setTextColor(Color.parseColor("#000000"));
            }
        }
        if (!pobjedaa){
            if (zbroj_mi >= kraj && zbroj_vi <= kraj) {
                pobjede_mi += 1;
                pobjeda.setText("MI su pobijedili");
                pobjeda.setTextColor(Color.parseColor("#2196F3"));
                pobjedaa = true;
                pobjednik = false;
            } else if (zbroj_vi >= kraj && zbroj_mi <= kraj) {
                pobjede_vi += 1;
                pobjeda.setText("VI su pobijedili");
                pobjeda.setTextColor(Color.parseColor("#F42414"));
                pobjedaa = true;
                pobjednik = true;
            } else if (zbroj_vi >= kraj && zbroj_mi >= kraj) {
                if (zbroj_vi > zbroj_mi) {
                    pobjede_vi += 1;
                    pobjeda.setText("VI su pobijedili");
                    pobjeda.setTextColor(Color.parseColor("#F42414"));
                    pobjedaa = true;
                    pobjednik = true;
                } else if (zbroj_mi < zbroj_vi) {
                    pobjede_mi += 1;
                    pobjeda.setText("MI su pobijedili");
                    pobjeda.setTextColor(Color.parseColor("#2196F3"));
                    pobjedaa = true;
                    pobjednik = false;
                } else {
                    if (IgraAdapter.localDataSet.get(IgraAdapter.localDataSet.size() - 1).getZvali()[0] == true) {
                        if (IgraAdapter.localDataSet.get(IgraAdapter.localDataSet.size() - 1).getZvali()[1] == false) {
                            pobjede_mi += 1;
                            pobjeda.setText("MI su pobijedili");
                            pobjeda.setTextColor(Color.parseColor("#2196F3"));
                            pobjedaa = true;
                            pobjednik = false;
                        }
                        if (IgraAdapter.localDataSet.get(IgraAdapter.localDataSet.size() - 1).getZvali()[1] == true) {
                            pobjede_vi += 1;
                            pobjeda.setText("VI su pobijedili");
                            pobjeda.setTextColor(Color.parseColor("#F42414"));
                            pobjedaa = true;
                            pobjednik = true;
                        }
                    } else {
                        if (Integer.parseInt(IgraAdapter.localDataSet.get(IgraAdapter.localDataSet.size() - 1).mi_suma) > Integer.parseInt(IgraAdapter.localDataSet.get(IgraAdapter.localDataSet.size() - 1).vi_suma)) {
                            pobjede_mi += 1;
                            pobjeda.setText("MI su pobijedili");
                            pobjeda.setTextColor(Color.parseColor("#2196F3"));
                            pobjedaa = true;
                            pobjednik = false;
                        }
                        if (Integer.parseInt(IgraAdapter.localDataSet.get(IgraAdapter.localDataSet.size() - 1).mi_suma) < Integer.parseInt(IgraAdapter.localDataSet.get(IgraAdapter.localDataSet.size() - 1).vi_suma)) {
                            pobjede_vi += 1;
                            pobjeda.setText("VI su pobijedili");
                            pobjeda.setTextColor(Color.parseColor("#F42414"));
                            pobjedaa = true;
                            pobjednik = true;
                        }
                    }
                }
            }

        }
        if (pobjedaa){
            recyclerView.setVisibility(View.INVISIBLE);
            sv.setVisibility(View.VISIBLE);
            mi_sve_suma.setText(String.valueOf(zbroj_mi));
            vi_sve_suma.setText(String.valueOf(zbroj_vi));
            Integer zbroj_mi_zvanja = 0;
            Integer zbroj_vi_zvanja = 0;
            for (int i = 0; i < IgraAdapter.localDataSet.size(); i++) {
                zbroj_mi_zvanja += IgraAdapter.localDataSet.get(i).broj_zvanje_20_mi*20
                                  +IgraAdapter.localDataSet.get(i).broj_zvanje_50_mi*50
                                  +IgraAdapter.localDataSet.get(i).broj_zvanje_100_mi*100
                                  +IgraAdapter.localDataSet.get(i).broj_zvanje_150_mi*150
                                  +IgraAdapter.localDataSet.get(i).broj_zvanje_200_mi*200;
                zbroj_vi_zvanja += IgraAdapter.localDataSet.get(i).broj_zvanje_20_vi*20
                                  +IgraAdapter.localDataSet.get(i).broj_zvanje_50_vi*50
                                  +IgraAdapter.localDataSet.get(i).broj_zvanje_100_vi*100
                                  +IgraAdapter.localDataSet.get(i).broj_zvanje_150_vi*150
                                  +IgraAdapter.localDataSet.get(i).broj_zvanje_200_vi*200;
            }
            mi_sve_zvanja.setText(String.valueOf(zbroj_mi_zvanja));
            vi_sve_zvanja.setText(String.valueOf(zbroj_vi_zvanja));
            Integer zbroj_mi_stiglja = 0;
            Integer zbroj_vi_stiglja = 0;
            for (int i = 0; i < IgraAdapter.localDataSet.size(); i++) {
                if (IgraAdapter.localDataSet.get(i).getStiglja()[0]==true && IgraAdapter.localDataSet.get(i).getStiglja()[1]==false){
                    zbroj_mi_stiglja += 1;
                }
                if (IgraAdapter.localDataSet.get(i).getStiglja()[0]==true && IgraAdapter.localDataSet.get(i).getStiglja()[1]==true){
                    zbroj_vi_stiglja += 1;
                }
            }
            mi_sve_stiglje.setText(String.valueOf(zbroj_mi_stiglja));
            vi_sve_stiglje.setText(String.valueOf(zbroj_vi_stiglja));
            Integer zbroj_mi_pali = 0;
            Integer zbroj_vi_pali = 0;
            for (int i = 0; i < IgraAdapter.localDataSet.size(); i++) {
                String mi_bodovi = IgraAdapter.localDataSet.get(i).getBodoviMi();
                String vi_bodovi = IgraAdapter.localDataSet.get(i).getBodoviVi();
                if (Objects.equals(mi_bodovi, "-")){
                    zbroj_mi_pali += 1;
                }
                if (Objects.equals(vi_bodovi, "-")){
                    zbroj_vi_pali += 1;
                }
            }
            mi_sve_pali.setText(String.valueOf(zbroj_mi_pali));
            vi_sve_pali.setText(String.valueOf(zbroj_vi_pali));
            dijeli = dijeli%4;
            if (dijeli<=-1){dijeli_prosli = -1;}
            if (dijeli==3){dijeli_prosli = 2;}
            if (dijeli==2){dijeli_prosli = 1;}
            if (dijeli==1){dijeli_prosli = 0;}
            if (dijeli==0){dijeli_prosli = 3;}
            dijeli = -1;
        }
        mi_badge.setText(String.valueOf(pobjede_mi));
        vi_badge.setText(String.valueOf(pobjede_vi));
        razlika.setText(String.valueOf(Math.abs(zbroj_mi-zbroj_vi)));
        razlika_mi.setText(String.valueOf(Math.abs(1001-zbroj_mi)));
        razlika_vi.setText(String.valueOf(Math.abs(1001-zbroj_vi)));
        dijeli = dijeli%4;
        if (dijeli<=-1){djelitelj.setImageResource(R.mipmap.selected_no);}
        if (dijeli==0){djelitelj.setImageResource(R.mipmap.selected_0);}
        if (dijeli==1){djelitelj.setImageResource(R.mipmap.selected_1);}
        if (dijeli==2){djelitelj.setImageResource(R.mipmap.selected_2);}
        if (dijeli==3){djelitelj.setImageResource(R.mipmap.selected_3);}
        recyclerView.scrollToPosition(igre.size() - 1);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}