package com.example.covidstatus.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidstatus.R;
import com.example.covidstatus.databinding.LocationListItemBinding;
import com.example.covidstatus.model.State;
import com.google.gson.Gson;

import java.util.List;

public class IndianListAdapter extends RecyclerView.Adapter<IndianListAdapter.ViewHolder> {

    private List<State> indianStates;
    private OnViewHolderClickListener viewHolderClickListener;

    public interface OnViewHolderClickListener{
        void onViewHolderClick(ViewHolder viewHolder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public State state;
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

        private void setState(State state){
            this.state = state;
            Log.i("MyTag", "setState: "+new Gson().toJson(state));
            binding.name.setText(state.getName());
            binding.todayCases.setText("+"+state.getTodayCases());
            binding.totalCases.setText(state.getTotalCases()+"");
            binding.totalDeaths.setText(state.getTotalDeaths()+"");
            binding.totalRecovered.setText(state.getTotalRecovered()+"");
        }
    }

    public IndianListAdapter(List<State> indianStates){
        this.indianStates = indianStates;
    }

    public void setIndianStates(List<State> indianStates){
        this.indianStates = indianStates;
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
        holder.setState(indianStates.get(position));
    }

    @Override
    public int getItemCount() {
        return this.indianStates.size();
    }
}
