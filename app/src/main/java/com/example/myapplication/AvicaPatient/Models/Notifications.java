package com.example.myapplication.AvicaPatient.Models;

import java.io.Serializable;

public class Notifications implements Serializable {

    public String id;
    public String title;
    public String description;
    public String sender_id;
    public String reciever_id;
    public String created_at;
}
