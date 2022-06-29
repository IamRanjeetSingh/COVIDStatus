package com.example.covidstatus.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidstatus.R;
import com.example.covidstatus.databinding.LocationListItemBinding;
import com.example.covidstatus.model.Country;

import java.util.List;

public class WorldListAdapter extends RecyclerView.Adapter<WorldListAdapter.ViewHolder> {

    private List<Country> countries;
    private OnViewHolderClickListener viewHolderClickListener;

    public interface OnViewHolderClickListener{
        void onViewHolderClick(ViewHolder viewHolder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public Country country;
        private LocationListItemBinding binding;
        private ViewHolder(@NonNull LocationListItemBinding binding) {
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

        private void setCountry(Country country){
            this.country = country;

            binding.name.setText(country.getName()+"");
            binding.todayCases.setText("+"+country.getTodayCases());
            binding.totalCases.setText(country.getTotalCases()+"");
            binding.totalDeaths.setText(country.getTotalDeaths()+"");
            binding.totalRecovered.setText(country.getTotalRecovered()+"");
        }
    }

    public WorldListAdapter(List<Country> countries){
        this.countries = countries;
    }

    public void setCountries(List<Country> countries){
        this.countries = countries;
        notifyDataSetChanged();
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener listener){
        this.viewHolderClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((LocationListItemBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.location_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setCountry(countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }
}
