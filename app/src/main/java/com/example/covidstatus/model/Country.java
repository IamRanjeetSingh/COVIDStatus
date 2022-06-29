package com.example.covidstatus.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Country implements Parcelable{
    private String name;
    private int totalCases;
    private int totalDeaths;
    private int totalRecovered;
    private int totalTested;

    private int todayCases;
    private int todayDeaths;
    private int todayRecovered;

    private double pomCases;
    private double pomDeaths;
    private double pomRecovered;
    private double pomTested;

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[0];
        }
    };

    protected Country(Parcel in){
        name = in.readString();
        totalCases = in.readInt();
        totalDeaths = in.readInt();
        totalRecovered = in.readInt();
        totalTested = in.readInt();

        todayCases = in.readInt();
        todayDeaths = in.readInt();
        todayRecovered = in.readInt();

        pomCases = in.readDouble();
        pomDeaths = in.readDouble();
        pomRecovered = in.readDouble();
        pomTested = in.readDouble();
    }

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

        dest.writeDouble(pomCases);
        dest.writeDouble(pomDeaths);
        dest.writeDouble(pomRecovered);
        dest.writeDouble(pomTested);
    }

    public Country(JSONObject jsonObject){
        name = jsonObject.optString("country", "");
        totalCases = jsonObject.optInt("cases", 0);
        totalDeaths = jsonObject.optInt("deaths", 0);
        totalRecovered = jsonObject.optInt("recovered", 0);
        totalTested = jsonObject.optInt("tests", 0);

        todayCases = jsonObject.optInt("todayCases", 0);
        todayDeaths = jsonObject.optInt("todayDeaths", 0);
        todayRecovered = jsonObject.optInt("todayRecovered", 0);

        pomCases = jsonObject.optDouble("casesPerOneMillion", 0);
        pomDeaths = jsonObject.optDouble("deathsPerOneMillion", 0);
        pomRecovered = jsonObject.optDouble("recoveredPerOneMillion", 0);
        pomTested = jsonObject.optDouble("testsPerOneMillion");
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

    public double getPomCases() {
        return pomCases;
    }

    public void setPomCases(double pomCases) {
        this.pomCases = pomCases;
    }

    public double getPomDeaths() {
        return pomDeaths;
    }

    public void setPomDeaths(double pomDeaths) {
        this.pomDeaths = pomDeaths;
    }

    public double getPomRecovered() {
        return pomRecovered;
    }

    public void setPomRecovered(double pomRecovered) {
        this.pomRecovered = pomRecovered;
    }

    public double getPomTested() {
        return pomTested;
    }

    public void setPomTested(double pomTested) {
        this.pomTested = pomTested;
    }
}
