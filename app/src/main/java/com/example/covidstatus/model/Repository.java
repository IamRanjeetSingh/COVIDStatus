package com.example.covidstatus.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static final String TAG = "Repository";

    private static Repository instance;
    private ExecutorService executorService;
    private RequestQueue requestQueue;

    public interface OnRepositoryResponseListener<T>{
        void onRepositoryResponse(T response);
    }

    private Repository(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        executorService = Executors.newSingleThreadExecutor();
    }

    public static Repository getInstance(Context context){
        if(instance == null)
            instance = new Repository(context);
        return instance;
    }

    public final void getCountries(final OnRepositoryResponseListener<List<Country>> listener){
        Log.i(TAG, "getCountries:");
        requestQueue.add(new JsonArrayRequest(Request.Method.GET, WorldAPI.getAllCountries(false, WorldAPI.SORT_BY_CASES, false), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        createCountries(response, listener);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        throw new RuntimeException("Volley getCountries: "+error.toString());
                    }
                }));
    }

    public final void getIndianStates(final OnRepositoryResponseListener<List<State>> listener){
        Log.i(TAG, "getIndianStates:");
        requestQueue.add(new JsonObjectRequest(Request.Method.GET, IndiaAPI.getIndianStates(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray keys = response.names();
                        if (keys != null) {
                            JSONObject today = response.optJSONObject(keys.optString(keys.length() - 1));
                            JSONObject yesterday = response.optJSONObject(keys.optString(keys.length() - 2));
                            if (today != null && yesterday != null) {
                                createIndianStates(today, yesterday, listener);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        throw new RuntimeException("Volley getCountries: "+error.toString());
                    }
                }));

    }

    private void createCountries(final JSONArray jsonArray,final OnRepositoryResponseListener<List<Country>> listener){
        Log.i(TAG, "createCountries:");
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                final List<Country> countries = new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){
                    try {
                        countries.add(new Country(jsonArray.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                sendResponseToUI(new Runnable() {
                    @Override
                    public void run() {
                        listener.onRepositoryResponse(countries);
                    }
                });
            }
        });
    }

    private void createIndianStates(final JSONObject today, final JSONObject yesterday, final OnRepositoryResponseListener<List<State>> listener){
        Log.i(TAG, "createIndianStates:");
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                final List<State> indianStates = new ArrayList<>();
                try {
                    JSONObject stateCodes = new JSONObject("{\"MH\":\"Maharashtra\",\"TN\":\"Tamil Nadu\",\"DL\":\"Delhi\",\"GJ\":\"Gujarat\",\"UP\":\"Uttar Pradesh\",\"RJ\":\"Rajasthan\",\"MP\":\"Madhya Pradesh\",\"WB\":\"West Bengal\",\"UN\":\"State Unassigned\",\"KA\":\"Karnataka\",\"HR\":\"Haryana\",\"BR\":\"Bihar\",\"AP\":\"Andhra Pradesh\",\"JK\":\"Jammu and Kashmir\",\"TG\":\"Telangana\",\"OR\":\"Odisha\",\"AS\":\"Assam\",\"PB\":\"Punjab\",\"KL\":\"Kerala\",\"UT\":\"Uttarakhand\",\"JH\":\"Jharkhand\",\"CT\":\"Chhattisgarh\",\"TR\":\"Tripura\",\"HP\":\"Himachal Pradesh\",\"GA\":\"Goa\",\"MN\":\"Manipur\",\"CH\":\"Chandigarh\",\"PY\":\"Puducherry\",\"LA\":\"Ladakh\",\"NL\":\"Nagaland\",\"MZ\":\"Mizoram\",\"AR\":\"Arunachal Pradesh\",\"ML\":\"Meghalaya\",\"AN\":\"Andaman and Nicobar Islands\",\"DN\":\"Dadra and Nagar Haveli and Daman and Diu\",\"SK\":\"Sikkim\",\"LD\":\"Lakshadweep\"}\n");
                    JSONArray keys = today.names();
                    if (keys != null) {
                        for(int i=0;i<keys.length();i++){
                            JSONObject jsonObject = new JSONObject();
                            String stateCode = keys.getString(i);

                            JSONObject total = today.optJSONObject(stateCode).optJSONObject("total");
                            if(total != null && stateCodes.has(stateCode)) {

                                jsonObject.put("name", stateCodes.getString(stateCode));

                                jsonObject.put("cases", total.optInt("confirmed", 0));
                                jsonObject.put("deaths", total.optInt("deceased", 0));
                                jsonObject.put("recovered", total.optInt("recovered", 0));
                                jsonObject.put("tested", total.optInt("tested", 0));


                                jsonObject.put("todayCases", Math.max(jsonObject.getInt("cases") - yesterday.getJSONObject(stateCode).getJSONObject("total").optInt("confirmed", 0), 0));
                                jsonObject.put("todayDeaths", Math.max(jsonObject.getInt("deaths") - yesterday.getJSONObject(stateCode).getJSONObject("total").optInt("deceased", 0), 0));
                                jsonObject.put("todayRecovered", Math.max(jsonObject.getInt("recovered") - yesterday.getJSONObject(stateCode).getJSONObject("total").optInt("recovered", 0), 0));

                                jsonObject.put("districts", today.getJSONObject(stateCode).optJSONObject("districts"));

                                indianStates.add(new State(jsonObject));
                                Log.i(TAG, "adding a state: " + stateCode);
                            }
                        }
                    }
                } catch (JSONException e) {
                    Log.i(TAG, "Exception In ExecutorService: "+e.toString());
                }

                Collections.sort(indianStates, new Comparator<State>() {
                    @Override
                    public int compare(State o1, State o2) {
                        if(o1.getTotalCases() < o2.getTotalCases())
                            return 1;
                        else if(o1.getTotalCases() == o2.getTotalCases())
                            return 0;
                        else
                            return -1;
                    }
                });

                sendResponseToUI(new Runnable() {
                    @Override
                    public void run() {
                        listener.onRepositoryResponse(indianStates);
                    }
                });
            }
        });

    }

    private void sendResponseToUI(Runnable runnable){
        Log.i(TAG, "sendResponseToUI: ");
        new Handler(Looper.getMainLooper()).post(runnable);
    }

}
