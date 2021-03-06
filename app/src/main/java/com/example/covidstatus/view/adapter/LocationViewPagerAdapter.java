package com.example.covidstatus.view.adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.covidstatus.view.IndiaFragment;
import com.example.covidstatus.view.WorldFragment;

public class LocationViewPagerAdapter extends FragmentStatePagerAdapter {
    public LocationViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new WorldFragment();
        else
            return new IndiaFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
