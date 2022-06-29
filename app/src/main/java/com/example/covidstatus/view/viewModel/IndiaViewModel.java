package com.example.covidstatus.view.viewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.covidstatus.controller.DataManager;
import com.example.covidstatus.model.State;

import java.util.List;

public class IndiaViewModel extends ViewModel {
    public static final int LOAD = 0;
    public static final int RELOAD = 1;
    private boolean initialized = false;
    private DataManager manager;

    private List<State> indianStates;

    public interface OnViewModelResponseListener<T>{
        void onViewModelResponse(T response);
    }

    public void initialize(Context context){
        initialized = true;
        manager = DataManager.getInstance(context);
    }

    public void getIndianStates(int loadType, final OnViewModelResponseListener<List<State>> listener){
        isInitialized();
        if(loadType == RELOAD || indianStates == null){
            manager.getIndianStates(new DataManager.OnManagerResponseListener<List<State>>() {
                @Override
                public void onManagerResponse(List<State> response) {
                    indianStates = response;
                    listener.onViewModelResponse(response);
                }
            });
        } else {
            listener.onViewModelResponse(indianStates);
        }
    }

    private void isInitialized(){
        if(!initialized)
            throw new IllegalStateException(this.getClass()+" not initialized");
    }
}
