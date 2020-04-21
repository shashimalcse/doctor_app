package com.s17131890.carelite;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {

    DatabaseReference databasePatients = FirebaseDatabase.getInstance().getReference("patients");
    Patient currentPatient = new Patient();
    public List<Patient> patients;
    Context context;
    NavController navController;
    SharedViewModel model;
    RecyclerView recyclerView;


    public SharedViewModel() {
        patients =new ArrayList<>();
    }

    public void setPatient(Patient patient){
        currentPatient=patient;
    }
    public  Patient getPatient(){
        return currentPatient;
    }



    public void updatePatient(CheckupHistory checkupHistory){
        try{
        currentPatient.addHistory(checkupHistory);
        updatePatient();}
        catch (Exception e){

        }


    }

    public void updatePatient(){

        try{
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("patients").child(currentPatient.getID());

        reference.setValue(currentPatient);

        getPatientList(context,navController,model,recyclerView);}
        catch (Exception e){

        }
    }



    public void getPatientList(Context context, NavController navController, SharedViewModel model, RecyclerView recyclerView){
        this.context=context;
        this.navController=navController;
        this.model=model;
        this.recyclerView=recyclerView;
        patients = new ArrayList<>();

        List<Patient> patients1 = new ArrayList<>();
        databasePatients.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patients.clear();

                for(DataSnapshot patientSnapshot:dataSnapshot.getChildren()){
                    Patient patient = patientSnapshot.getValue(Patient.class);
                    patients1.add(patient);
                }
                Log.d("PATIENTS",Integer.toString(patients.size()));

                PatientListAdapter patientListAdapter = new PatientListAdapter(context,patients,navController,model);
                recyclerView.setAdapter(patientListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        this.patients=patients1;

    }

    }
