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
import com.example.covidstatus.databinding.LocationFragmentBinding;
import com.example.covidstatus.model.State;
import com.example.covidstatus.view.adapter.IndianListAdapter;
import com.example.covidstatus.view.viewModel.IndiaViewModel;

import java.util.List;

public class IndiaFragment extends Fragment {
    private LocationFragmentBinding binding;
    private IndiaViewModel viewModel;

    public IndiaFragment(){
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(IndiaViewModel.class);
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
                viewModel.getIndianStates(IndiaViewModel.RELOAD, new IndiaViewModel.OnViewModelResponseListener<List<State>>() {
                    @Override
                    public void onViewModelResponse(List<State> response) {
                        if(binding.list.getAdapter() != null)
                            ((IndianListAdapter)binding.list.getAdapter()).setIndianStates(response);
                        binding.swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });

        viewModel.getIndianStates(IndiaViewModel.LOAD, new IndiaViewModel.OnViewModelResponseListener<List<State>>() {
            @Override
            public void onViewModelResponse(List<State> response) {
                initialize(response);
            }
        });

        return binding.getRoot();
    }

    private void initialize(final List<State> indianStates){
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.list.setAdapter(new IndianListAdapter(indianStates));
        binding.list.setHasFixedSize(true);
        binding.progressBar.setVisibility(View.GONE);

        if(binding.list.getAdapter() != null){
            ((IndianListAdapter)binding.list.getAdapter()).setOnViewHolderClickListener(new IndianListAdapter.OnViewHolderClickListener() {
                @Override
                public void onViewHolderClick(IndianListAdapter.ViewHolder viewHolder) {
                    Intent indianStateIntent = new Intent(getContext(), IndianStateActivity.class);
                    indianStateIntent.putExtra(IndianStateActivity.INDIAN_STATE_KEY, viewHolder.state);
                    startActivity(indianStateIntent);
                }
            });
        }
    }
}