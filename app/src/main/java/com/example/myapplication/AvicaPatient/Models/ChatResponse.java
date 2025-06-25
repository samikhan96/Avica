package com.example.myapplication.AvicaPatient.Models;

import java.util.List;

public class ChatResponse {
    private List<Chat> chats;

    public List<Chat> getChats() {
        return chats;
    }

    // Inner Chat class that represents individual chat messages
    public static class Chat {
        private String author_id;
        private String message;

        public String getSender() {
            return author_id;
        }

        public String getMessage() {
            return message;
        }
    }
}


