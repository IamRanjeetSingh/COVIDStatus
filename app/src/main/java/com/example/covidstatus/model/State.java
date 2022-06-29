package com.example.covidstatus.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class State implements Parcelable {
    public static final String TAG = "State";
    private String name;
    private int totalCases;
    private int totalDeaths;
    private int totalRecovered;
    private int totalTested;

    private int todayCases;
    private int todayDeaths;
    private int todayRecovered;

    private List<District> districts;

    public static class District implements Parcelable{
        private String name;
        private int totalCases;
        private int totalDeaths;
        private int totalRecovered;

        District(String name, JSONObject jsonObject){
            Log.i(TAG, "District:");
            this.name = name;
            this.totalCases = jsonObject.optInt("confirmed", 0);
            this.totalDeaths = jsonObject.optInt("deceased", 0);
            this.totalRecovered = jsonObject.optInt("recovered", 0);
        }

        private District(Parcel in) {
            name = in.readString();
            totalCases = in.readInt();
            totalDeaths = in.readInt();
            totalRecovered = in.readInt();
        }

        public static final Creator<District> CREATOR = new Creator<District>() {
            @Override
            public District createFromParcel(Parcel in) {
                return new District(in);
            }

            @Override
            public District[] newArray(int size) {
                return new District[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeInt(totalCases);
            dest.writeInt(totalDeaths);
            dest.writeInt(totalRecovered);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTotalCases() {
            return totalCases;
        }

        public void setTotalCases(int totalCases) {
            this.totalCases = totalCases;
        }

        public int getTotalDeaths() {
            return totalDeaths;
        }

        public void setTotalDeaths(int totalDeaths) {
            this.totalDeaths = totalDeaths;
        }

        public int getTotalRecovered() {
            return totalRecovered;
        }

        public void setTotalRecovered(int totalRecovered) {
            this.totalRecovered = totalRecovered;
        }
    }

    public State(JSONObject jsonObject){
        Log.i(TAG, "State:");
        name = jsonObject.optString("name", "");
        totalCases = jsonObject.optInt("cases", 0);
        totalDeaths = jsonObject.optInt("deaths", 0);
        totalRecovered = jsonObject.optInt("recovered", 0);
        totalTested = jsonObject.optInt("tested", 0);

        todayCases = jsonObject.optInt("todayCases", 0);
        todayDeaths = jsonObject.optInt("todayDeaths", 0);
        todayRecovered = jsonObject.optInt("todayRecovered", 0);

        districts = new ArrayList<>();
        JSONObject districtsJSON = jsonObject.optJSONObject("districts");
        if(districtsJSON != null) {
            JSONArray keys = districtsJSON.names();
            if(keys != null){
                for(int i=0;i<keys.length();i++){
                    String districtName = keys.optString(i, "");
                    JSONObject district = districtsJSON.optJSONObject(districtName).optJSONObject("total");
                    if(district != null)
                        districts.add(new District(districtName, district));
                }
            }
        }
    }

    protected State(Parcel in) {
        name = in.readString();
        totalCases = in.readInt();
        totalDeaths = in.readInt();
        totalRecovered = in.readInt();
        totalTested = in.readInt();

        todayCases = in.readInt();
        todayDeaths = in.readInt();
        todayRecovered = in.readInt();

        districts = in.readArrayList(District.class.getClassLoader());
    }

    public static final Creator<State> CREATOR = new Creator<State>() {
        @Override
        public State createFromParcel(Parcel in) {
            return new State(in);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(totalCases);
        dest.writeInt(totalDeaths);
        dest.writeInt(totalRecovered);
        dest.writeInt(totalTested);

        dest.writeInt(todayCases);
        dest.writeInt(todayDeaths);
        dest.writeInt(todayRecovered);

        dest.writeList(districts);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public int getTotalTested() {
        return totalTested;
    }

    public void setTotalTested(int totalTested) {
        this.totalTested = totalTested;
    }

    public int getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(int todayCases) {
        this.todayCases = todayCases;
    }

    public int getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(int todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public int getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(int todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
