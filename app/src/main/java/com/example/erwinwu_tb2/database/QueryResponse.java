package com.example.erwinwu_tb2.database;

public interface QueryResponse<T> {
    void onSuccess(T data);
    void onFailure(String message);
}
