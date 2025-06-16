package com.example.myapplication.AvicaPatient.Models;

import java.io.Serializable;

public class DashboardData implements Serializable {

    Dashboard_ECG ecg;
    Dashboard_Spo2 spo2;
    Dashboard_BP bloodpressure;
    Dashboard_BG bloodglucose;
    Dashboard_Temp temperature;

    public Dashboard_ECG getEcg() {
        return ecg;
    }

    public void setEcg(Dashboard_ECG ecg) {
        this.ecg = ecg;
    }

    public Dashboard_Spo2 getSpo2() {
        return spo2;
    }

    public void setSpo2(Dashboard_Spo2 spo2) {
        this.spo2 = spo2;
    }

    public Dashboard_BP getBloodpressure() {
        return bloodpressure;
    }

    public void setBloodpressure(Dashboard_BP bloodpressure) {
        this.bloodpressure = bloodpressure;
    }

    public Dashboard_BG getBloodglucose() {
        return bloodglucose;
    }

    public void setBloodglucose(Dashboard_BG bloodglucose) {
        this.bloodglucose = bloodglucose;
    }

    public Dashboard_Temp getTemperature() {
        return temperature;
    }

    public void setTemperature(Dashboard_Temp temperature) {
        this.temperature = temperature;
    }
}
