package com.example.wear;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    DatabaseReference databasePatients;
    List<Patient> patients;
    WearableRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String message = getIntent().getStringExtra("ID");
        if (message == null) {

            databasePatients = FirebaseDatabase.getInstance().getReference("patients");

            recyclerView = findViewById(R.id.main_menu_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setEdgeItemsCenteringEnabled(true);
            recyclerView.setLayoutManager(new WearableLinearLayoutManager(this));


            patients = new ArrayList<>();
            databasePatients.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    patients.clear();

                    for (DataSnapshot patientSnapshot : dataSnapshot.getChildren()) {
                        Patient patient = patientSnapshot.getValue(Patient.class);
                        patients.add(patient);
                    }

                    ArrayList<MenuItem> menuItems = new ArrayList<>();
                    for (Patient patient : patients) {
                        if (patient.getStatus().equals("0")) {
                            menuItems.add(new MenuItem(R.drawable.ic_status_green, patient.getName()));
                        } else if (patient.getStatus().equals("1")) {
                            menuItems.add(new MenuItem(R.drawable.ic_status_yellow, patient.getName()));
                        } else {
                            menuItems.add(new MenuItem(R.drawable.ic_status_red, patient.getName()));
                        }


                    }

                    recyclerView.setAdapter(new MainMenuAdapter(getApplicationContext(), menuItems, new MainMenuAdapter.AdapterCallback() {
                        @Override
                        public void onItemClicked(final Integer menuPosition) {
                            switch (menuPosition) {
                                case 0:
                                    action_1();
                                    break;
                                case 1:
                                    action_2();
                                    break;
                                case 2:
                                    action_3();
                                    break;
                                case 3:
                                    action_4();
                                    break;
                                default:
                                    cancelMenu();
                            }
                        }
                    }));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });
        }
        else {
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText("NOT");
        }
    }



    public void action_1(){
        Log.i("ACTION", "action_1()");
    }

    public void action_2(){
        Log.i("ACTION", "action_2()");
    }

    public void action_3(){
        Log.i("ACTION", "action_3()");
    }

    public void action_4(){
        Log.i("ACTION", "action_4()");
    }

    public void cancelMenu(){
        Log.i("ACTION", "cancelMenu()");
    }



    }

