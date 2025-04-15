package com.optimum.Avica.Listener;

public interface ServiceListener<T,E> {

    public void success(T success);
    public void error(E error);
}