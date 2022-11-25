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
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class VelikePartijeAdapter extends RecyclerView.Adapter<VelikePartijeAdapter.ViewHolder> {

    static ArrayList<Partije> localDataSet;
    public static void WriteToFile(String fileName, String content){
        try{
            FileOutputStream writer = new FileOutputStream(fileName);
            writer.write(content.getBytes());
            writer.close();
            Log.e("TAG", "Wrote to file: "+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView_mi;
        private final TextView textView_vi;
        private final TextView textView_mi_malo;
        private final TextView textView_vi_malo;
        private final TextView textView_kraj;

        public ViewHolder(View view) {
            super(view);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Integer position1 = getItemCount1()-1-getAdapterPosition();
                    ArrayList<Partije> dataSet = new ArrayList<Partije>();
                    String igre = Game_chooser.readFromFile(view.getContext());
                    String igrenovo = "";
                    Integer j = 0;
                    for (String i : igre.split("\\|")) {
                        boolean b = !(i.trim().length() <= 1);
                        if (b) {
                            if (!(j.equals(position1))){
                                igrenovo += "|"+i;
                                dataSet.add(new Partije(i));
                            }
                            j++;
                        }

                    }
                    localDataSet = dataSet;
                    Game_chooser.dataSet = dataSet;
                    Game_chooser.adapter.notifyItemRemoved(getAdapterPosition());
                    Game_chooser.writeToFile(igrenovo, view.getContext());
                    return true;
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer position1 = getItemCount1()-1-getAdapterPosition();
                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), MainActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, position1);
                    view.getContext().startActivity(intent);
                }
            });


            textView_mi = (TextView) view.findViewById(R.id.partija_mi);
            textView_vi = (TextView) view.findViewById(R.id.partija_vi);
            textView_mi_malo = (TextView) view.findViewById(R.id.partija_mi_malo);
            textView_vi_malo = (TextView) view.findViewById(R.id.partija_vi_malo);
            textView_kraj = (TextView) view.findViewById(R.id.do_kolko_partija);
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
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public VelikePartijeAdapter(ArrayList<Partije> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.partija_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Integer position1 = getItemCount()-1-position;
        String[] rezultati = dobi_sumu(localDataSet.get(position1).igra);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = viewHolder.migetTextView().getContext().getTheme();
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        int color = ContextCompat.getColor(viewHolder.migetTextView().getContext(), typedValue.resourceId);
        int color_text = Color.parseColor("#"+Integer.toHexString(color).substring(2));
        //Log.d("color_text", String.valueOf(color_text));
        if (Integer.parseInt(rezultati[0])>Integer.parseInt(rezultati[1])){
            viewHolder.migetTextView().setTextColor(Color.parseColor("#2196F3"));
            viewHolder.vigetTextView().setTextColor(color_text);
        }
        if (Integer.parseInt(rezultati[0])<Integer.parseInt(rezultati[1])){
            viewHolder.migetTextView().setTextColor(color_text);
            viewHolder.vigetTextView().setTextColor(Color.parseColor("#F42414"));
        }
        if (Integer.parseInt(rezultati[0])==Integer.parseInt(rezultati[1])){
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

    // Return the size of your dataset (invoked by the layout manager)
    public static int getItemCount1() {
        if (localDataSet!=null){
            return localDataSet.size();
        }
        return 0;
    }
    @Override
    public int getItemCount() {
        if (localDataSet!=null){
            return localDataSet.size();
        }
        return 0;
    }
}
