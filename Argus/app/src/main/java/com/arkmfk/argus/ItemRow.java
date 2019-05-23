package com.arkmfk.argus;

public class ItemRow {
    private int id;
    private String title;
    private String company;
    private String date;
    private String distance;

    public ItemRow(int id, String title, String company, String date, String distance) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.date = date;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getDate() {
        return date;
    }

    public String getDistance() {
        return distance;
    }
}
