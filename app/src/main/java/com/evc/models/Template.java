package com.evc.models;

import java.util.ArrayList;

public class Template {

    private String id;
    private String owner;
    private String ownerCompanyid;

    private ArrayList<Field> fields = new ArrayList<Field>();

    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setOwnerCompanyid(String ownerCompanyid) {
        this.ownerCompanyid = ownerCompanyid;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getOwnerCompanyid() {
        return ownerCompanyid;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }
}
