package com.s17131890.carelite;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s17131890.carelite.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    NavController navController;
    private FirebaseAuth mAuth;
    DatabaseReference databaseDoctors;
    FirebaseUser currentUser;
    SharedViewModel model;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseDoctors = FirebaseDatabase.getInstance().getReference("doctors");
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        binding.patientbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeFragment_to_patientListFragment);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        ProgressDialog Dialog = new ProgressDialog(getContext());
        Dialog.setMessage("Loading...");
        Dialog.show();


        databaseDoctors.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot doctorSnapshot:dataSnapshot.getChildren()){
                    Doctor doctor = doctorSnapshot.getValue(Doctor.class);
                    if (doctor.getID().equals(currentUser.getUid())){
                        binding.docId.setText(doctor.getMedId());
                        binding.docName.setText(doctor.getName());
                        binding.patientCount.setText("Patient Count : "+Integer.toString(doctor.getPatientCount()));
                        model.Doctor_ID = currentUser.getUid();
                    }

                    Dialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}
