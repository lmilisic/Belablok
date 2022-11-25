package com.mioc.belablok;

import static com.mioc.belablok.FirstFragment.dark;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IgraAdapter extends RecyclerView.Adapter<IgraAdapter.ViewHolder> {

    static ArrayList<Igre> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView_mi;
        private final TextView textView_vi;
        private final ImageView imageViewzvali;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), SecondActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, "MI");
                    intent.putExtra("EDIT", getAdapterPosition());
                    view.getContext().startActivity(intent);
                }
            });

            textView_mi = (TextView) view.findViewById(R.id.textView1);
            imageViewzvali = (ImageView) view.findViewById(R.id.imageView3);
            textView_vi = (TextView) view.findViewById(R.id.textView2);
        }

        public TextView migetTextView() {
            return textView_mi;
        }
        public ImageView getImageView() {
            return imageViewzvali;
        }
        public TextView vigetTextView() {
            return textView_vi;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public IgraAdapter(ArrayList<Igre> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }
    int color_text;
    int color_text1;
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.migetTextView().setText(localDataSet.get(position).getBodoviMi());
        viewHolder.vigetTextView().setText(localDataSet.get(position).getBodoviVi());
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = viewHolder.vigetTextView().getContext().getTheme();
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        int color = ContextCompat.getColor(viewHolder.vigetTextView().getContext(), typedValue.resourceId);
        color_text = Color.parseColor("#"+Integer.toHexString(color).substring(2));
        TypedValue typedValue1 = new TypedValue();
        Resources.Theme theme1 = viewHolder.vigetTextView().getContext().getTheme();
        theme1.resolveAttribute(android.R.attr.textColorPrimaryInverse, typedValue1, true);
        int color1 = ContextCompat.getColor(viewHolder.vigetTextView().getContext(), typedValue1.resourceId);
        color_text1 = Color.parseColor("#"+Integer.toHexString(color1).substring(2));
        if (localDataSet.get(position).getZvali()[0]){
            if (!localDataSet.get(position).getZvali()[1]){
                if(!dark){viewHolder.getImageView().setImageResource(R.mipmap.mi);}else{viewHolder.getImageView().setImageResource(R.mipmap.mi_dark_foreground);}
                viewHolder.getImageView().setVisibility(View.VISIBLE);
            }
            if (localDataSet.get(position).getZvali()[1]){
                if(!dark){viewHolder.getImageView().setImageResource(R.mipmap.vi);}else{viewHolder.getImageView().setImageResource(R.mipmap.vi_dark_foreground);}
                viewHolder.getImageView().setVisibility(View.VISIBLE);
            }
        }
        if (!localDataSet.get(position).getZvali()[0]){
            viewHolder.getImageView().setVisibility(View.GONE);
        }

        String mi_bodovi = localDataSet.get(position).getBodoviMi();
        String vi_bodovi = localDataSet.get(position).getBodoviVi();
        if (Objects.equals(mi_bodovi, "-")){
            mi_bodovi = "0";
        }
        if (Objects.equals(vi_bodovi, "-")){
            vi_bodovi = "0";
        }
        Integer mi_int = Integer.parseInt(mi_bodovi);
        Integer vi_int = Integer.parseInt(vi_bodovi);
        if (mi_int>vi_int){
            viewHolder.migetTextView().setTextColor(Color.parseColor("#2196F3"));
            viewHolder.vigetTextView().setTextColor(color_text);
        }
        if (vi_int>mi_int){
            viewHolder.vigetTextView().setTextColor(Color.parseColor("#F42414"));
            viewHolder.migetTextView().setTextColor(color_text);
        }
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
