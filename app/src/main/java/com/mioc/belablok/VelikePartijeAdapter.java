package com.mioc.belablok;

import static com.mioc.belablok.MainActivity.load;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView_mi;
        private final TextView textView_vi;
        private final TextView textView_mi_malo;
        private final TextView textView_vi_malo;
        private final TextView textView_kraj;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), MainActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, getAdapterPosition());
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
        viewHolder.mimalogetTextView().setText(localDataSet.get(position).mi_pobjede);
        viewHolder.vimalogetTextView().setText(localDataSet.get(position).vi_pobjede);
        viewHolder.krajgetTextView().setText(localDataSet.get(position).kraj);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (localDataSet!=null){
            return localDataSet.size();
        }
        return 0;
    }
}
