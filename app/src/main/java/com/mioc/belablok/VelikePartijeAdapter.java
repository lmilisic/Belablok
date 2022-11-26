package com.mioc.belablok;

import static com.mioc.belablok.App.getContext;
import static com.mioc.belablok.Game_chooser.adapter;
import static com.mioc.belablok.Game_chooser.color_text;
import static com.mioc.belablok.Game_chooser.writeToFile1;
import static com.mioc.belablok.Game_chooser.writeToFile_datum_kreacije;
import static com.mioc.belablok.Game_chooser.writeToFile_datum_promijenjeno;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    static Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView_mi;
        private final TextView textView_vi;
        private final TextView textView_mi_malo;
        private final TextView textView_vi_malo;
        private final TextView textView_kraj;
        private final TextView textView_ime;
        private final TextView textView_datum_created;
        private final TextView textView_datum_edited;
        private final Button settings;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Integer position1 = getItemCount1()-1-getAdapterPosition();
                    String imena = Game_chooser.readFromFile1(view.getContext()).trim();
                    String[] imena1 = imena.split("\\n");
                    StringBuilder output = new StringBuilder();
                    for(int i = 0; i < adapter.localDataSet.size(); i++){
                        if (i!=position1){
                            String s = imena1[i]+"\n";
                            output.append(s);
                        }
                    }
                    writeToFile1(output.toString(), getContext());
                    String datumi = Game_chooser.readFromFile_datum_kreacije(view.getContext()).trim();
                    String[] datumi1 = datumi.split("\\n");
                    output = new StringBuilder();
                    for(int i = 0; i < adapter.localDataSet.size(); i++){
                        if (i!=position1){
                            String s = datumi1[i]+"\n";
                            output.append(s);
                        }
                    }
                    writeToFile_datum_kreacije(output.toString(), getContext());
                    datumi = Game_chooser.readFromFile_datum_promijenjeno(view.getContext()).trim();
                    datumi1 = datumi.split("\\n");
                    output = new StringBuilder();
                    for(int i = 0; i < adapter.localDataSet.size(); i++){
                        if (i!=position1){
                            String s = datumi1[i]+"\n";
                            output.append(s);
                        }
                    }
                    writeToFile_datum_promijenjeno(output.toString(), getContext());
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
                    adapter.notifyItemRemoved(getAdapterPosition());
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
                    intent.putExtra("isLive", "false");
                    view.getContext().startActivity(intent);
                }
            });
            view.findViewById(R.id.button25).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view.findViewById(R.id.button25));
                    popup.getMenuInflater().inflate(R.menu.settings_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(this::onMenuItemClick);
                    popup.show();
                }

                private boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.rename:
                            openDialog(getAdapterPosition());
                            break;
                        case R.id.kraj:
                            openDialog1(getAdapterPosition());
                            break;
                        case R.id.pobjede:
                            openDialog2(getAdapterPosition());
                            break;
                        default:
                            break;
                    }
                    return false;
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
            settings = (Button) view.findViewById(R.id.button25);
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
        public Button settings() {
            return settings;
        }
    }
    public static void openDialog(int adapterPosition)
    {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        linearLayout.setPadding(30, 0, 30, 0);

        final EditText saveas = new EditText(context);
        saveas.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        saveas.setImeOptions(EditorInfo.IME_ACTION_DONE);
        saveas.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        saveas.setHint("Bezimena partija");
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Preimenuj partiju");

        linearLayout.addView(saveas);

        dialog.setView(linearLayout);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialoginterface, int buttons)
            {
                if (saveas.getText().length()!=0){
                    String imena = Game_chooser.readFromFile1(context).trim();
                    String[] imena1 = imena.split("\\n");
                    StringBuilder output = new StringBuilder();
                    for(int i = 0; i < adapter.localDataSet.size(); i++){
                        String s = "Bezimena partija\n";
                        if (i==localDataSet.size()-1-adapterPosition){
                            s = String.valueOf(saveas.getText())+"\n";
                        }else {
                            s = imena1[i]+"\n";
                        }
                        output.append(s);
                    }
                    writeToFile1(output.toString(), context);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "Name can't be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int buttons)
            {
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(color_text);
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(color_text);
    }
    public static void openDialog1(int adapterPosition)
    {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        linearLayout.setPadding(30, 0, 30, 0);

        final EditText saveas = new EditText(context);
        saveas.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        saveas.setImeOptions(EditorInfo.IME_ACTION_DONE);
        saveas.setInputType(InputType.TYPE_CLASS_NUMBER);
        saveas.setHint("1001");
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Promijeni bodovnu granicu");

        linearLayout.addView(saveas);

        dialog.setView(linearLayout);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialoginterface, int buttons)
            {
                if (String.valueOf(saveas.getText()).length()!=0 && String.valueOf(saveas.getText()).length()<=5){
                    if (Integer.parseInt(String.valueOf(saveas.getText()))>0 && Integer.parseInt(String.valueOf(saveas.getText()))<10000){
                        String novi_kraj = String.valueOf(Integer.parseInt(String.valueOf(saveas.getText())));
                        String qr = Base64.getEncoder().encodeToString(shared_prefs(
                                localDataSet.get(getItemCount1()-1-adapterPosition).igre,
                                localDataSet.get(getItemCount1()-1-adapterPosition).dijeli,
                                localDataSet.get(getItemCount1()-1-adapterPosition).pobjedaa,
                                novi_kraj,
                                localDataSet.get(getItemCount1()-1-adapterPosition).mi_pobjede,
                                localDataSet.get(getItemCount1()-1-adapterPosition).vi_pobjede,
                                localDataSet.get(getItemCount1()-1-adapterPosition).pobjednik,
                                localDataSet.get(getItemCount1()-1-adapterPosition).dijeli_prosli).getBytes());
                        localDataSet.set(getItemCount1()-1-adapterPosition, new Partije("belot!"+qr));
                        String partije_edit = "";
                        for (Partije partija : localDataSet){
                            partije_edit += "|"+partija.igra;
                        }
                        Game_chooser.writeToFile(partije_edit, getContext());
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(context, "Invalid number (0<number<10000)!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Invalid input!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int buttons)
            {
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(color_text);
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(color_text);
    }
    public static void openDialog2(int adapterPosition)
    {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        linearLayout.setPadding(30, 0, 30, 0);

        final EditText saveas = new EditText(context);
        saveas.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        saveas.setImeOptions(EditorInfo.IME_ACTION_DONE);
        saveas.setInputType(InputType.TYPE_CLASS_TEXT);
        saveas.setHint("0:0");
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Promijeni rezultat pobjeda");

        linearLayout.addView(saveas);

        dialog.setView(linearLayout);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialoginterface, int buttons)
            {
                if (String.valueOf(saveas.getText()).length()!=0){
                    if (String.valueOf(saveas.getText()).matches("(\\d)+:(\\d)+")) {
                        String unos = String.valueOf(saveas.getText());
                        if (unos.split(":")[0].length()<=4 && unos.split(":")[1].length()<=4){
                            String novo_mi_pobjede = String.valueOf(Integer.valueOf(unos.split(":")[0]));
                            String novo_vi_pobjede = String.valueOf(Integer.valueOf(unos.split(":")[1]));
                            String qr = Base64.getEncoder().encodeToString(shared_prefs(
                                    localDataSet.get(getItemCount1() - 1 - adapterPosition).igre,
                                    localDataSet.get(getItemCount1() - 1 - adapterPosition).dijeli,
                                    localDataSet.get(getItemCount1() - 1 - adapterPosition).pobjedaa,
                                    localDataSet.get(getItemCount1() - 1 - adapterPosition).kraj,
                                    novo_mi_pobjede,
                                    novo_vi_pobjede,
                                    localDataSet.get(getItemCount1() - 1 - adapterPosition).pobjednik,
                                    localDataSet.get(getItemCount1() - 1 - adapterPosition).dijeli_prosli).getBytes());
                            localDataSet.set(getItemCount1() - 1 - adapterPosition, new Partije("belot!" + qr));
                            String partije_edit = "";
                            for (Partije partija : localDataSet) {
                                partije_edit += "|" + partija.igra;
                            }
                            Game_chooser.writeToFile(partije_edit, getContext());
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(context, "Invalid input!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "Invalid format!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Invalid input!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int buttons)
            {
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(color_text);
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(color_text);
    }
    public static String shared_prefs(String igre, String dijeli, String pobjedaa, String kraj, String pobjede_mi, String pobjede_vi, String pobjednik, String dijeli_prosli){
        String contents = "";
        String igra = igre;
        contents = "<?xml version='1.0' encoding='utf-8' standalone='yes' ?> \n" +
                "<map> \n" +
                "    <int name=\"dijeli\" value=\""+String.valueOf(dijeli)+"\" /> \n" +
                "    <boolean name=\"pobjedaa\" value=\""+String.valueOf(pobjedaa)+"\" /> \n" +
                "    <int name=\"kraj\" value=\""+String.valueOf(kraj)+"\" /> \n" +
                "    <string name=\"Igre\">"+igra+"</string>\n" +
                "    <int name=\"pobjede_mi\" value=\""+String.valueOf(pobjede_mi)+"\" />\n" +
                "    <int name=\"pobjede_vi\" value=\""+String.valueOf(pobjede_vi)+"\" />\n" +
                "    <boolean name=\"pobjednik\" value=\""+String.valueOf(pobjednik)+"\" />\n" +
                "    <int name=\"dijeli_prosli\" value=\""+String.valueOf(dijeli_prosli)+"\" />\n" +
                "</map>\n";
        Log.d("TAG1111", "shared_prefs: "+contents);
        return contents;
    }
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
        String imena = Game_chooser.readFromFile1(viewHolder.migetTextView().getContext()).trim();
        String[] imena1 = imena.split("\\n");
        viewHolder.ime_partije().setText(imena1[position1]);
        String datumi = Game_chooser.readFromFile_datum_kreacije(viewHolder.migetTextView().getContext()).trim();
        String[] datumi1 = datumi.split("\\n");
        viewHolder.datum_kreacije().setText(datumi1[position1]);
        datumi = Game_chooser.readFromFile_datum_promijenjeno(viewHolder.migetTextView().getContext()).trim();
        datumi1 = datumi.split("\\n");
        viewHolder.datum_promijenjeno().setText(datumi1[position1]);
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
