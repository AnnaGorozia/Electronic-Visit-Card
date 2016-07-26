package com.evc.models;

/**
 * Created by khrak on 7/27/16.
 */
public class HistoryEntry {
    private String url;
    private String partner;
    private String date;

    public HistoryEntry(String url, String partner, String date) {
        this.url = url;
        this.partner = partner;
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getUrl() {

        return url;
    }

    public String getPartner() {
        return partner;
    }

    public String getDate() {
        return date;
    }
}
