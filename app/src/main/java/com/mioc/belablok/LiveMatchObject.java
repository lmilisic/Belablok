package com.mioc.belablok;

public class LiveMatchObject {


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getTime_created() {
        return time_created;
    }

    public Integer getTime_edited() {
        return time_edited;
    }

    public String getIgra() {
        return igra;
    }

    public LiveMatchObject(Integer id, String name, Integer time_created, Integer time_edited, String igra) {
        this.id = id;
        this.name = name;
        this.time_created = time_created;
        this.time_edited = time_edited;
        this.igra = igra;
    }

    private Integer id;
    private String name;
    private Integer time_created;
    private Integer time_edited;
    private String igra;

}
