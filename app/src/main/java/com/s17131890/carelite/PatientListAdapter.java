package com.s17131890.carelite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientViewHolder> {


    Context context;
    List<Patient> patients;
    NavController navController;
    SharedViewModel model;

    public PatientListAdapter(Context context, List<Patient> patients, NavController navController,SharedViewModel model) {
        this.context = context;
        this.patients = patients;
        this.navController=navController;
        this.model=model;
    }


    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.patient_card, parent, false);
        return new PatientViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        holder.PatientName.setText(patients.get(position).getName());
        holder.PatientID.setText(patients.get(position).getID());
        if (patients.get(position).getStatus().equals("0")) {
            holder.PatientStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_status_green));
        }
        else if (patients.get(position).getStatus().equals("1")) {
            holder.PatientStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_status_red));
        }
        else  if (patients.get(position).getStatus().equals("2")) {
            holder.PatientStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_status_yellow));
        }
        holder.ViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.currentPatient=(patients.get(position));
                navController.navigate(R.id.action_patientListFragment_to_patientFragment);
            }
        });
    }


    @Override
    public int getItemCount() {
        return patients.size();
    }

    class PatientViewHolder extends RecyclerView.ViewHolder {

        ImageView PatientPhoto;
        TextView PatientName;
        TextView PatientID;
        ImageView PatientStatus;
        Button ViewButton;

        public PatientViewHolder(@Nullable View itemView) {
            super(itemView);
            PatientPhoto = (ImageView) itemView.findViewById(R.id.patient_image);
            PatientName = (TextView) itemView.findViewById(R.id.patient_name);
            PatientID = (TextView) itemView.findViewById(R.id.patient_id);
            PatientStatus = (ImageView) itemView.findViewById(R.id.patient_status);
            ViewButton = (Button) itemView.findViewById(R.id.viewbutton);
        }
    }


    }







