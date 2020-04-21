package com.s17131890.carelite;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String Name;
    private String ID;
    private String Status;
    private List<CheckupHistory> History;
    private String Doctor_ID;

    public Patient(){

    }

    public Patient(String name, String ID, String status, List<CheckupHistory> History,String doctor_ID) {
        Name=name;
        this.ID=ID;
        this.Status=status;
        this.History=History;
        Doctor_ID=doctor_ID;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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

}
