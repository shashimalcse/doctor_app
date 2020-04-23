package com.s17131890.carelite;

public class Doctor {
    private String name;
    private String medId;
    private int patientCount;
    private String ID;
    private String Url;

    public Doctor(String name, String medId, int patientCount, String ID ,String Url) {
        this.name = name;
        this.medId = medId;
        this.patientCount = patientCount;
        this.ID = ID;
        this.Url=Url;
    }

    public Doctor(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(int patientCount) {
        this.patientCount = patientCount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
