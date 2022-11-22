package com.mioc.belablok;

import java.util.Base64;

public class Partije {
    String igra;
    String mi_bodovi;
    String vi_bodovi;
    String mi_pobjede;
    String vi_pobjede;
    String kraj;
    public Partije(String i) {
        this.igra = i;
        String rezultat = i;
        rezultat = rezultat.substring(6, rezultat.length());
        byte[] decodedBytes = Base64.getDecoder().decode(rezultat);
        String decodedString = new String(decodedBytes);
        if (String.valueOf(i).startsWith("belot!")){
            this.mi_pobjede = decodedString.split("\"pobjede_mi\" value=\"")[1].split("\"")[0];
            this.vi_pobjede = decodedString.split("\"pobjede_vi\" value=\"")[1].split("\"")[0];
            this.kraj = decodedString.split("\"kraj\" value=\"")[1].split("\"")[0];
        }
        this.mi_bodovi = "0";
        this.vi_bodovi = "0";
    }
}
