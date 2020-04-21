package com.s17131890.carelite;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.s17131890.carelite.databinding.FragmentPatientListBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientListFragment extends Fragment{

    FragmentPatientListBinding binding;
    NavController navController;
    DatabaseReference databasePatients;
    List<Patient> patients;
    RecyclerView recyclerView;
    SharedViewModel model;
    public static String id = "test_channel_01";
    int notificationID = 1;
    Query myTopPostsQuery;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPatientListBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        patients = new ArrayList<>();

        recyclerView = binding.recycleview;
        navController = Navigation.findNavController(view);
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        databasePatients = FirebaseDatabase.getInstance().getReference("patients");
        myTopPostsQuery = databasePatients
                .orderByChild("name");

        createchannel();









    }

    @Override
    public void onStart() {
        super.onStart();
        ProgressDialog Dialog = new ProgressDialog(getContext());
        Dialog.setMessage("Loading...");
        Dialog.show();

        List<Patient> patients1 = new ArrayList<>();
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patients.clear();

                for(DataSnapshot patientSnapshot:dataSnapshot.getChildren()){
                    Patient patient = patientSnapshot.getValue(Patient.class);
                    if (patient.getStatus().equals("2")){
                        simpleNoti(patient);
                    }
                    patients1.add(patient);
                }
                model.patients.clear();

                model.patients=patients1;
                Log.d("PATIENTS",Integer.toString(patients.size()));

                PatientListAdapter patientListAdapter = new PatientListAdapter(getContext(),model.patients,navController,model);
                recyclerView.setAdapter(patientListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                Dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void simpleNoti(Patient patient) {

        String message = patient.getName()+" ID : "+patient.getID()+" is not checked";
        Intent viewIntent = new Intent(getActivity(), NotiActivity.class);
        viewIntent.putExtra("NotiID", message);

        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(getContext(), 0, viewIntent, 0);

        //Now create the notification.  We must use the NotificationCompat or it will not work on the wearable.
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getContext(), id)
                        .setSmallIcon(R.drawable.ic_status_red)
                        .setContentTitle("6h Alert")
                        .setContentText(message)
                        .setChannelId(id)
                        .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getContext());

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationID, notificationBuilder.build());
        notificationID++;
    }
    private void createchannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(id,
                    getString(R.string.channel_name),  //name of the channel
                    NotificationManager.IMPORTANCE_DEFAULT);   //importance level
            //important level: default is is high on the phone.  high is urgent on the phone.  low is medium, so none is low?
            // Configure the notification channel.
            mChannel.setDescription(getString(R.string.channel_description));
            mChannel.enableLights(true);
            //Sets the notification light color for notifications posted to this channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            nm.createNotificationChannel(mChannel);

        }
    }

}
