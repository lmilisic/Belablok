package com.mioc.belablok;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

public class LiveMatchesAdapter extends RecyclerView.Adapter<LiveMatchesAdapter.ViewHolder> {
    public static ArrayList<Partije> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView_mi;
        private final TextView textView_vi;
        private final TextView textView_mi_malo;
        private final TextView textView_vi_malo;
        private final TextView textView_kraj;
        private final TextView textView_ime;
        private final TextView textView_datum_created;
        private final TextView textView_datum_edited;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer position1 = getItemCount1()-1-getAdapterPosition();
                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), MainActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, position1);
                    intent.putExtra("isLive", "true");
                    view.getContext().startActivity(intent);
                }
            });
            textView_mi = (TextView) view.findViewById(R.id.partija_mi);
            textView_vi = (TextView) view.findViewById(R.id.partija_vi);
            textView_mi_malo = (TextView) view.findViewById(R.id.partija_mi_malo);
            textView_vi_malo = (TextView) view.findViewById(R.id.partija_vi_malo);
            textView_kraj = (TextView) view.findViewById(R.id.do_kolko_partija);
            textView_ime = (TextView) view.findViewById(R.id.ime_partije);
            textView_datum_created = (TextView) view.findViewById(R.id.date_created);
            textView_datum_edited = (TextView) view.findViewById(R.id.date_changed);
        }
        public TextView migetTextView() {
            return textView_mi;
        }
        public TextView vigetTextView() {
            return textView_vi;
        }
        public TextView mimalogetTextView() {
            return textView_mi_malo;
        }
        public TextView vimalogetTextView() {
            return textView_vi_malo;
        }
        public TextView krajgetTextView() {
            return textView_kraj;
        }
        public TextView ime_partije() {
            return textView_ime;
        }
        public TextView datum_kreacije() {
            return textView_datum_created;
        }
        public TextView datum_promijenjeno() {return textView_datum_edited;}
    }

    public LiveMatchesAdapter(ArrayList<Partije> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.live_partija_row_item, viewGroup, false);
        return new ViewHolder(view);
    }
    public String[] dobi_sumu(String i) {
        String rezultat = i;
        rezultat = rezultat.substring(6, rezultat.length());
        byte[] decodedBytes = Base64.getDecoder().decode(rezultat);
        String decodedString = new String(decodedBytes);
        String igre1 = decodedString.split("<string name=\"Igre\">")[1].split("</string>")[0];
        ArrayList<String> lista = new ArrayList<String>(Arrays.asList(TextUtils.split(igre1, "‚‗‚")));
        Gson gson = new Gson();
        ArrayList<String> objStrings = lista;
        ArrayList<Igre> objects =  new ArrayList<Igre>();
        for(String jObjString : objStrings){
            Object value  = gson.fromJson(jObjString,  Igre.class);
            objects.add((Igre) value);
        }
        Integer mi_suma = 0;
        Integer vi_suma = 0;
        for(Igre igraupartiji : objects){
            mi_suma += Integer.parseInt(igraupartiji.getMi_suma());
            vi_suma += Integer.parseInt(igraupartiji.getVi_suma());
        }
        return new String[] {String.valueOf(mi_suma), String.valueOf(vi_suma)};
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Integer position1 = getItemCount()-1-position;
        String ime = live_matches.dataSet_match_details.get(position1).getName();
        viewHolder.ime_partije().setText(ime);
        Integer datum_k = live_matches.dataSet_match_details.get(position1).getTime_created();
        java.util.Date time = new java.util.Date((long) datum_k * 1000);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
        String s = dateFormat.format(time);
        viewHolder.datum_kreacije().setText(s);
        Integer datum_e = live_matches.dataSet_match_details.get(position1).getTime_edited();
        time = new java.util.Date((long) datum_e * 1000);
        s = dateFormat.format(time);
        viewHolder.datum_promijenjeno().setText(s);
        String[] rezultati = dobi_sumu(localDataSet.get(position1).igra);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = viewHolder.migetTextView().getContext().getTheme();
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        int color = ContextCompat.getColor(viewHolder.migetTextView().getContext(), typedValue.resourceId);
        int color_text = Color.parseColor("#" + Integer.toHexString(color).substring(2));
        if (Integer.parseInt(rezultati[0]) > Integer.parseInt(rezultati[1])) {
            viewHolder.migetTextView().setTextColor(Color.parseColor("#2196F3"));
            viewHolder.vigetTextView().setTextColor(color_text);
        }
        if (Integer.parseInt(rezultati[0]) < Integer.parseInt(rezultati[1])) {
            viewHolder.migetTextView().setTextColor(color_text);
            viewHolder.vigetTextView().setTextColor(Color.parseColor("#F42414"));
        }
        if (Integer.parseInt(rezultati[0]) == Integer.parseInt(rezultati[1])) {
            viewHolder.migetTextView().setTextColor(color_text);
            viewHolder.vigetTextView().setTextColor(color_text);
        }
        viewHolder.migetTextView().setText(rezultati[0]);
        viewHolder.vigetTextView().setText(rezultati[1]);
        viewHolder.mimalogetTextView().setTextColor(Color.parseColor("#2196F3"));
        viewHolder.vimalogetTextView().setTextColor(Color.parseColor("#F42414"));
        viewHolder.mimalogetTextView().setText(localDataSet.get(position1).mi_pobjede);
        viewHolder.vimalogetTextView().setText(localDataSet.get(position1).vi_pobjede);
        viewHolder.krajgetTextView().setText(localDataSet.get(position1).kraj);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
    public static int getItemCount1() {
        return localDataSet.size();
    }
}
