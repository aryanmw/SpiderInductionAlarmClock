package com.wadhavekar.clockit.Stopwatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wadhavekar.clockit.R;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    ArrayList<TimeFormat> lapTimeList;

    public RecyclerViewAdapter(ArrayList<TimeFormat> lapTimes) {
        this.lapTimeList = lapTimes;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        RecyclerViewHolder rvh =new RecyclerViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        if (getItemCount()>0 && position >0){
            long t = lapTimeList.get(position).getMilliSec() - lapTimeList.get(position-1).getMilliSec();
            long tMin = t/1000/60;
            long tSec = (t/1000)%60;
            long tMillisec = t%100;
            holder.lapTime.setText(String.format(Locale.getDefault(),"%02d:%02d:%02d",tMin,tSec,tMillisec));

        }
        else{
            holder.lapTime.setText(lapTimeList.get(position).getLapTimeInString());
        }

        int id = position +1;
        holder.lapNumber.setText(""+id);


    }

    @Override
    public int getItemCount() {
        return lapTimeList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView lapNumber,lapTime;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            lapNumber = itemView.findViewById(R.id.tv_lapNumber);
            lapTime = itemView.findViewById(R.id.tv_lap);
        }
    }
}
