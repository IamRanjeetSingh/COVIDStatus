package com.example.covidstatus.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.covidstatus.R;
import com.example.covidstatus.databinding.StateBinding;
import com.example.covidstatus.model.State;
import com.example.covidstatus.view.adapter.DistrictListAdapter;

public class IndianStateActivity extends AppCompatActivity {
    public static final String INDIAN_STATE_KEY = "indian_state_key";

    private StateBinding binding;
    private State state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.state);
        state = getIntent().getParcelableExtra(INDIAN_STATE_KEY);
        binding.setState(state);

        binding.districtsList.setLayoutManager(new LinearLayoutManager(this));
        binding.districtsList.setAdapter(new DistrictListAdapter(state.getDistricts()));
        binding.districtsList.setHasFixedSize(true);

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

        binding.nestedScrollView.scrollTo(0, 0);
    }

    private void displayNumbers(){

    }

    private void displayPercentage(){

    }
}
