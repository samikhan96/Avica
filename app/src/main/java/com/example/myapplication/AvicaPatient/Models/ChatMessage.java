package com.example.myapplication.AvicaPatient.Models;

public class ChatMessage {
    private String message;
    private long created_at;
    private String author_id;
    private Author author;
    private String room_id;
    private String media;

    public ChatMessage(String message, long created_at, String author_id, String room_id, Author author, String media) {
        this.message = message;
        this.created_at = created_at;
        this.author_id = author_id;
        this.room_id = room_id;
        this.author = author;
    }


    public ChatMessage() {
    }
}

