package com.example.covidstatus.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.covidstatus.R;
import com.example.covidstatus.databinding.CountryBinding;
import com.example.covidstatus.model.Country;

public class CountryActivity extends AppCompatActivity {
    public static final String COUNTRY_KEY = "country_key";

    private CountryBinding binding;
    private Country country;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.country);
        country = getIntent().getParcelableExtra(COUNTRY_KEY);
        binding.setCountry(country);

        binding.displayNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumbers();
            }
        });

        binding.displayPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPercentage();
            }
        });
    }

    private void displayNumbers(){

    }

    private void displayPercentage(){

    }
}
