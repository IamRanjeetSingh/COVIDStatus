package com.example.covidstatus.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.covidstatus.R;
import com.example.covidstatus.controller.DataManager;
import com.example.covidstatus.databinding.LocationFragmentBinding;
import com.example.covidstatus.model.Country;
import com.example.covidstatus.model.State;
import com.example.covidstatus.view.adapter.IndianListAdapter;
import com.example.covidstatus.view.adapter.WorldListAdapter;
import com.example.covidstatus.view.viewModel.IndiaViewModel;
import com.example.covidstatus.view.viewModel.WorldViewModel;

import java.util.List;

public class WorldFragment extends Fragment {
    private LocationFragmentBinding binding;
    private WorldViewModel viewModel;

    public WorldFragment(){
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(WorldViewModel.class);
        viewModel.initialize(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.location_fragment, container, false);

        binding.progressBar.setVisibility(View.VISIBLE);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getCountries(WorldViewModel.RELOAD, new WorldViewModel.OnViewModelResponseListener<List<Country>>() {
                    @Override
                    public void onViewModelResponse(List<Country> response) {
                        if(binding.list.getAdapter() != null)
                            ((WorldListAdapter)binding.list.getAdapter()).setCountries(response);
                        binding.swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });

        viewModel.getCountries(WorldViewModel.LOAD, new WorldViewModel.OnViewModelResponseListener<List<Country>>() {
            @Override
            public void onViewModelResponse(List<Country> response) {
                initialize(response);
            }
        });

        return binding.getRoot();
    }

    private void initialize(final List<Country> countries){
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.list.setAdapter(new WorldListAdapter(countries));
        binding.list.setHasFixedSize(true);
        binding.progressBar.setVisibility(View.GONE);

        if(binding.list.getAdapter() != null){
            ((WorldListAdapter)binding.list.getAdapter()).setOnViewHolderClickListener(new WorldListAdapter.OnViewHolderClickListener() {
                @Override
                public void onViewHolderClick(WorldListAdapter.ViewHolder viewHolder) {
                    Intent countryIntent = new Intent(getContext(), CountryActivity.class);
                    countryIntent.putExtra(CountryActivity.COUNTRY_KEY, viewHolder.country);
                    startActivity(countryIntent);
                }
            });
        }
    }
}