package com.mioc.belablok;

public class Igre {
    private Integer vi_suma_counter = -1;
    private Integer mi_suma_counter = -1;
    private Boolean[] pali;
    private Boolean[] zvali;
    private Boolean[] stiglja;
    Integer broj_zvanje_20_mi;
    Integer broj_zvanje_20_vi;
    Integer broj_zvanje_50_mi;
    Integer broj_zvanje_50_vi;
    Integer broj_zvanje_100_mi;
    Integer broj_zvanje_100_vi;
    Integer broj_zvanje_150_mi;
    Integer broj_zvanje_150_vi;
    Integer broj_zvanje_200_mi;
    Integer broj_zvanje_200_vi;
    String mi_zvanje;
    String vi_zvanje;
    String mi_bodovi;
    String vi_bodovi;
    String mi_suma;
    String vi_suma;

    public Igre(Boolean[] new_pali, Boolean[] new_zvali, Boolean[] new_stiglja, Integer broj_zvanje_20_mi, Integer broj_zvanje_20_vi, Integer broj_zvanje_50_mi, Integer broj_zvanje_50_vi, Integer broj_zvanje_100_mi, Integer broj_zvanje_100_vi, Integer broj_zvanje_150_mi, Integer broj_zvanje_150_vi, Integer broj_zvanje_200_mi, Integer broj_zvanje_200_vi, String mi_zvanje, String vi_zvanje, String mi_bodovi, String vi_bodovi, String mi_suma, String vi_suma, Integer mi_suma_counter, Integer vi_suma_counter) {
        this.pali = new_pali;
        this.zvali = new_zvali;
        this.stiglja = new_stiglja;
        this.broj_zvanje_20_mi = broj_zvanje_20_mi;
        this.broj_zvanje_20_vi = broj_zvanje_20_vi;
        this.broj_zvanje_50_mi = broj_zvanje_50_mi;
        this.broj_zvanje_50_vi = broj_zvanje_50_vi;
        this.broj_zvanje_100_mi = broj_zvanje_100_mi;
        this.broj_zvanje_100_vi = broj_zvanje_100_vi;
        this.broj_zvanje_150_mi = broj_zvanje_150_mi;
        this.broj_zvanje_150_vi = broj_zvanje_150_vi;
        this.broj_zvanje_200_mi = broj_zvanje_200_mi;
        this.broj_zvanje_200_vi = broj_zvanje_200_vi;
        this.mi_zvanje = mi_zvanje;
        this.vi_zvanje = vi_zvanje;
        this.mi_bodovi = mi_bodovi;
        this.vi_bodovi = vi_bodovi;
        this.mi_suma = mi_suma;
        this.vi_suma = vi_suma;
        this.mi_suma_counter = mi_suma_counter;
        this.vi_suma_counter = vi_suma_counter;
    }
    public Integer getVi_suma_counter() {return vi_suma_counter;}

    public Integer getMi_suma_counter() {return mi_suma_counter;}

    public Boolean[] getPali() {
        return pali;
    }

    public Boolean[] getZvali() {
        return zvali;
    }

    public Boolean[] getStiglja() {
        return stiglja;
    }

    public Integer getBroj_zvanje_20_mi() {
        return broj_zvanje_20_mi;
    }

    public Integer getBroj_zvanje_20_vi() {
        return broj_zvanje_20_vi;
    }

    public Integer getBroj_zvanje_50_mi() {
        return broj_zvanje_50_mi;
    }

    public Integer getBroj_zvanje_50_vi() {
        return broj_zvanje_50_vi;
    }

    public Integer getBroj_zvanje_100_mi() {
        return broj_zvanje_100_mi;
    }

    public Integer getBroj_zvanje_100_vi() {
        return broj_zvanje_100_vi;
    }

    public Integer getBroj_zvanje_150_mi() {
        return broj_zvanje_150_mi;
    }

    public Integer getBroj_zvanje_150_vi() {
        return broj_zvanje_150_vi;
    }

    public Integer getBroj_zvanje_200_mi() {
        return broj_zvanje_200_mi;
    }

    public Integer getBroj_zvanje_200_vi() {
        return broj_zvanje_200_vi;
    }

    public String getMi_zvanje() {
        return mi_zvanje;
    }

    public String getVi_zvanje() {
        return vi_zvanje;
    }

    public String getMi_bodovi() {
        return mi_bodovi;
    }

    public String getVi_bodovi() {
        return vi_bodovi;
    }

    public String getMi_suma() {
        return mi_suma;
    }

    public String getVi_suma() {
        return vi_suma;
    }

    public String getBodoviMi() {
        if (pali[0] && zvali[0] && !zvali[1]){
            return "-";
        }
        else {
            return mi_suma;
        }
    }
    public String getBodoviVi() {
        if (pali[0] && zvali[0] && zvali[1]){
            return "-";
        }
        else {
            return vi_suma;
        }
    }

    public Igre(Boolean[] pali, Boolean[] zvali, Boolean[] stiglja, Integer[] broj_zvanje_20_mi, Integer[] broj_zvanje_20_vi, Integer[] broj_zvanje_50_mi, Integer[] broj_zvanje_50_vi, Integer[] broj_zvanje_100_mi, Integer[] broj_zvanje_100_vi, Integer[] broj_zvanje_150_mi, Integer[] broj_zvanje_150_vi, Integer[] broj_zvanje_200_mi, Integer[] broj_zvanje_200_vi, CharSequence subSequence, CharSequence subSequence1, CharSequence text, CharSequence text1, CharSequence subSequence2, CharSequence subSequence3, CharSequence subSequence4, CharSequence subSequence5) {
        this.pali = pali;
        this.zvali = zvali;
        this.stiglja = stiglja;
        this.broj_zvanje_20_mi = broj_zvanje_20_mi[0];
        this.broj_zvanje_20_vi = broj_zvanje_20_vi[0];
        this.broj_zvanje_50_mi = broj_zvanje_50_mi[0];
        this.broj_zvanje_50_vi = broj_zvanje_50_vi[0];
        this.broj_zvanje_100_mi = broj_zvanje_100_mi[0];
        this.broj_zvanje_100_vi = broj_zvanje_100_vi[0];
        this.broj_zvanje_150_mi = broj_zvanje_150_mi[0];
        this.broj_zvanje_150_vi = broj_zvanje_150_vi[0];
        this.broj_zvanje_200_mi = broj_zvanje_200_mi[0];
        this.broj_zvanje_200_vi = broj_zvanje_200_vi[0];
        this.mi_zvanje = (String) subSequence;
        this.vi_zvanje = (String) subSequence1;
        this.mi_bodovi = (String) text;
        this.vi_bodovi = (String) text1;
        this.mi_suma = (String) subSequence2;
        this.vi_suma = (String) subSequence3;
        this.mi_suma_counter = Integer.parseInt((String) subSequence4);
        this.vi_suma_counter = Integer.parseInt((String) subSequence5);
    }
}
