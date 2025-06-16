package com.example.myapplication.AvicaPatient.Listener;

public interface ServiceListener<T,E> {

    public void success(T success);
    public void error(E error);
}