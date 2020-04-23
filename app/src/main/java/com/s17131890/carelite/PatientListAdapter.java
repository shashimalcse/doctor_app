package com.s17131890.carelite;

import android.content.Context;
import android.util.Log;
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

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        holder.PatientID.setText("Patient ID: "+patients.get(position).getID());

        Date date = null;
        try {
            date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(patients.get(position).getLastUpdate());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Date now = new Date();
        long diff = now.getTime()-date.getTime();

        long diffMinutes = diff / (60 * 1000);

        if (diffMinutes>60*6) {
            holder.PatientStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_status_red));
        }
        else if (diffMinutes>60*2) {
            holder.PatientStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_status_yellow));
        }
        else   {
            holder.PatientStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_status_green));
        }


        Picasso.get().load(patients.get(position).getUrl()).fit().into(holder.PatientPhoto);

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
            PatientName = (TextView) itemView.findViewById(R.id.patient_name2);
            PatientID = (TextView) itemView.findViewById(R.id.patient_id);
            PatientStatus = (ImageView) itemView.findViewById(R.id.patient_status);
            ViewButton = (Button) itemView.findViewById(R.id.viewbutton);
        }
    }


    }







