package com.example.covidstatus.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.covidstatus.R;
import com.example.covidstatus.databinding.MainScreenBinding;
import com.example.covidstatus.view.adapter.LocationViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainScreenActivity extends AppCompatActivity {

    private MainScreenBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_screen);
        setSupportActionBar(binding.toolbar);

        binding.locationTab.addTab(binding.locationTab.newTab().setText(getResources().getString(R.string.World)), 0, true);
        binding.locationTab.addTab(binding.locationTab.newTab().setText(getResources().getString(R.string.India)), 1, false);

        binding.locationViewPager.setAdapter(new LocationViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        binding.locationViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.locationTab));
        binding.locationTab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.locationViewPager));
    }
}
