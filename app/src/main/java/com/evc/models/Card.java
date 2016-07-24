package com.evc.models;

public class Card {

    private String path;
    private String id;


    public Card(String path) {
        this.path = path;
    }

    public Card(String id, String path) {
        this.path = path;
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getid(){
        return id;
    }

    public String getPath() {
        return path;
    }
}
