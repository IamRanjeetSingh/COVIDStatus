package com.example.covidstatus.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class WorldAPI {
    private static final String BASE_URL = "https://disease.sh/v2/";

    private static final String ALL_COUNTRIES = "countries";

    static final String SORT_BY_CASES = "cases";
    static final String SORT_BY_DEATHS = "deaths";
    static final String SORT_BY_RECOVERED = "recovered";
    static final String SORT_BY_TODAY_CASES = "todayCases";
    static final String SORT_BY_TODAY_DEATHS = "todayDeaths";
    static final String SORT_BY_TODAY_RECOVERED = "todayRecovered";

    static String getAllCountries(boolean yesterday, String sortBy, boolean allowNull){
        Map<String,String> queries = new HashMap<>();
        queries.put("yesterday", Boolean.toString(yesterday));
        queries.put("sort",sortBy);
        queries.put("allowNull", Boolean.toString(allowNull));
        return BASE_URL+ALL_COUNTRIES+createQueries(queries);
    }

    private static String createQueries(Map<String, String> queries){
        StringBuilder builder = new StringBuilder("?");

        Map.Entry[] entries = new Map.Entry[queries.entrySet().size()];
        entries = queries.entrySet().toArray(entries);

        builder.append(entries[0].getKey().toString());
        builder.append("=");
        builder.append(entries[0].getValue().toString());
        for(int i=1;i<entries.length;i++){
            builder.append("&");
            builder.append(entries[i].getKey().toString());
            builder.append("=");
            builder.append(entries[i].getValue().toString());
        }
        return builder.toString();
    }

}
