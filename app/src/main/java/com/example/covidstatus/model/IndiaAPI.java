package com.example.covidstatus.model;

class IndiaAPI {
    private static final String BASE_URL = "https://api.covid19india.org/v3/data-all.json";

    static String getIndianStates(){
        return BASE_URL;

    }
}
