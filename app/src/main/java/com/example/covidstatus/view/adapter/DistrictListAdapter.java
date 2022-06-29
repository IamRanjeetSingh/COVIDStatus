package com.example.covidstatus.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidstatus.R;
import com.example.covidstatus.databinding.DistrictListItemBinding;
import com.example.covidstatus.model.State;

import java.util.List;

public class DistrictListAdapter extends RecyclerView.Adapter<DistrictListAdapter.ViewHolder>{

    private List<State.District> districts;
    private OnViewHolderClickListener viewHolderClickListener;

    public interface OnViewHolderClickListener{
        void onViewHolderClick(ViewHolder viewHolder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public State.District district;
        DistrictListItemBinding binding;

        private ViewHolder(@NonNull DistrictListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewHolderClickListener != null)
                        viewHolderClickListener.onViewHolderClick(ViewHolder.this);
                }
            });
        }

        private void setDistrict(State.District district){
            this.district = district;

            binding.name.setText(district.getName());
            binding.totalCases.setText(district.getTotalCases()+"");
            binding.totalDeaths.setText(district.getTotalDeaths()+"");
            binding.totalRecovered.setText(district.getTotalRecovered()+"");
        }
    }

    public DistrictListAdapter(List<State.District> districts){
        this.districts = districts;
    }

    public void setDistricts(List<State.District> districts){
        this.districts = districts;
        notifyDataSetChanged();
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener listener){
        this.viewHolderClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((DistrictListItemBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.district_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setDistrict(districts.get(position));
    }

    @Override
    public int getItemCount() {
        return districts.size();
    }
}
