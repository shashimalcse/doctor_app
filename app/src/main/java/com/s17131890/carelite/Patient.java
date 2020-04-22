package com.s17131890.carelite;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient {
    private String Name;
    private String ID;
    private String lastUpdate;
    private List<CheckupHistory> History;
    private String Doctor_ID;
    private String Url;


    public Patient(){

    }

    public Patient(String name, String ID, String lastUpdate, List<CheckupHistory> History, String doctor_ID,String url) {
        Name=name;
        this.ID=ID;
        this.lastUpdate=lastUpdate;
        this.History=History;
        Doctor_ID=doctor_ID;
        this.Url=url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<CheckupHistory> getHistory() {
        return History;
    }

    public void setHistory(List<CheckupHistory> history) {
        History = history;
    }

    public String getDoctor_ID() {
        return Doctor_ID;
    }

    public void setDoctor_ID(String doctor_ID) {
        Doctor_ID = doctor_ID;
    }

    public void addHistory(CheckupHistory checkupHistory){
        History.add(checkupHistory);
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
