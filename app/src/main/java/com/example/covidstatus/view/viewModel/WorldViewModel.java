package com.example.covidstatus.view.viewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.covidstatus.controller.DataManager;
import com.example.covidstatus.model.Country;
import com.example.covidstatus.model.State;

import java.util.List;

public class WorldViewModel extends ViewModel {
    public static final int LOAD = 0;
    public static final int RELOAD = 1;
    private boolean initialized = false;
    private DataManager manager;

    private List<Country> countries;

    public interface OnViewModelResponseListener<T>{
        void onViewModelResponse(T response);
    }

    public void initialize(Context context){
        initialized = true;
        manager = DataManager.getInstance(context);
    }

    public void getCountries(int loadType, final WorldViewModel.OnViewModelResponseListener<List<Country>> listener){
        isInitialized();
        if(loadType == RELOAD || countries == null){
            manager.getCountries(new DataManager.OnManagerResponseListener<List<Country>>() {
                @Override
                public void onManagerResponse(List<Country> response) {
                    countries = response;
                    listener.onViewModelResponse(response);
                }
            });
        } else {
            listener.onViewModelResponse(countries);
        }
    }

    private void isInitialized(){
        if(!initialized)
            throw new IllegalStateException(this.getClass()+" not initialized");
    }
}
