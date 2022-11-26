package com.mioc.belablok;

import static com.mioc.belablok.App.getContext;
import static com.mioc.belablok.Game_chooser.adapter;
import static com.mioc.belablok.Game_chooser.dataSet;
import static com.mioc.belablok.Game_chooser.readFromFile;
import static com.mioc.belablok.Game_chooser.writeToFile;
import static com.mioc.belablok.Game_chooser.writeToFile1;
import static com.mioc.belablok.Game_chooser.writeToFile_datum_kreacije;
import static com.mioc.belablok.Game_chooser.writeToFile_datum_promijenjeno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        handleIntent();
    }

    private void handleIntent() {

        Uri uri = getIntent().getData();
        if (uri == null) {
            tellUserThatCouldntOpenFile();
            return;
        }

        String text = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            text = getStringFromInputStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (text == null) {
            tellUserThatCouldntOpenFile();
            return;
        }

        if (text.startsWith("belot!")){
            String imena = Game_chooser.readFromFile1(getContext());
            String[] imena1 = imena.split("\\n");
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < imena1.length; i++){
                String s = imena1[i]+"\n";
                output.append(s);
            }
            output.append("Bezimena partija\n");
            writeToFile1(output.toString(), getContext());
            String igre = readFromFile(getApplicationContext());
            writeToFile(igre + "|" + text, getApplicationContext());
            String datumi = Game_chooser.readFromFile_datum_kreacije(getContext()).trim();
            String[] datumi1 = datumi.split("\\n");
            output = new StringBuilder();
            for(int i = 0; i < datumi1.length; i++){
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
            for(int i = 0; i < datumi1.length; i++){
                String s = datumi1[i]+"\n";
                output.append(s);
            }
            dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            date = new Date();
            output.append(dateFormat.format(date)+"\n");
            writeToFile_datum_promijenjeno(output.toString(), getContext());
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
                    Context context = getApplicationContext();
                    CharSequence text = "Game added!";
                    int duration = Toast.LENGTH_LONG;
                    Toast.makeText(context, text, duration).show();
                    Intent myIntent = new Intent(ReaderActivity.this, Game_chooser.class);
                    ReaderActivity.this.startActivity(myIntent);
                }
            });
        }else{
            tellUserThatCouldntOpenFile();
            return;
        }
    }

    private void tellUserThatCouldntOpenFile() {
        Toast.makeText(this, "Can't open this file!", Toast.LENGTH_SHORT).show();
    }

    public static String getStringFromInputStream(InputStream stream) throws IOException {
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, "UTF8");
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
        return writer.toString();
    }

}