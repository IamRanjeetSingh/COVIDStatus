package com.example.covidstatus.controller;

import android.content.Context;

import com.example.covidstatus.model.Country;
import com.example.covidstatus.model.Repository;
import com.example.covidstatus.model.State;

import java.util.List;

public class DataManager {
    private static DataManager instance;

    private Repository repository;

    public interface OnManagerResponseListener<T>{
        void onManagerResponse(T response);
    }

    private DataManager(Context context){
        repository = Repository.getInstance(context);
    }

    public static DataManager getInstance(Context context){
        if(instance == null)
            instance = new DataManager(context);
        return instance;
    }

    public void getIndianStates(final OnManagerResponseListener<List<State>> listener){
        repository.getIndianStates(new Repository.OnRepositoryResponseListener<List<State>>() {
            @Override
            public void onRepositoryResponse(List<State> response) {
                listener.onManagerResponse(response);
            }
        });
    }

    public void getCountries(final OnManagerResponseListener<List<Country>> listener){
        repository.getCountries(new Repository.OnRepositoryResponseListener<List<Country>>() {
            @Override
            public void onRepositoryResponse(List<Country> response) {
                listener.onManagerResponse(response);
            }
        });
    }
}
