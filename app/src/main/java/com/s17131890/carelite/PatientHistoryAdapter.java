package com.s17131890.carelite;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PatientHistoryAdapter extends RecyclerView.Adapter<PatientHistoryAdapter.HistoryViewHolder> {


    Context context;
    List<CheckupHistory> historyList;
    SharedViewModel model;

    public PatientHistoryAdapter(Context context, List<CheckupHistory> historyList, SharedViewModel model) {
        this.context = context;
        this.historyList = historyList;
        this.model = model;
    }

    @NonNull
    @Override
    public PatientHistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.patient_history_card, parent, false);
        return new PatientHistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientHistoryAdapter.HistoryViewHolder holder, int position) {
        Log.d("HIstory",Float.toString(historyList.get(position).getTemperature()));
            holder.Temp.setText(Float.toString(historyList.get(position).getTemperature())+" C");
            holder.Pressure.setText(Float.toString(historyList.get(position).getBloodPressure())+"mmHg");
            holder.HeartRate.setText(Float.toString(historyList.get(position).getHeartRate())+" bpm");
            holder.Date.setText(historyList.get(position).getDate());
            holder.Time.setText(historyList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView Temp;
        TextView Pressure;
        TextView HeartRate;
        TextView Date;
        TextView Time;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            Temp =(TextView) itemView.findViewById(R.id.temp);
            Pressure =(TextView) itemView.findViewById(R.id.pressure);
            HeartRate =(TextView) itemView.findViewById(R.id.heartrate);
            Date =(TextView) itemView.findViewById(R.id.date);
            Time =(TextView) itemView.findViewById(R.id.time);
        }
    }
}
