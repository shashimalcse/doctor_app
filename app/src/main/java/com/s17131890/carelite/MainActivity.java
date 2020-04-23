package com.s17131890.carelite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Handler handler;
    public static String id = "test_channel_01";
    int notificationID = 1;
    DatabaseReference databasePatients;
    SharedViewModel model;
    NavController navController;
    NavigationView navigationView;
    AppBarConfiguration appBarConfiguration;
    DrawerLayout drawerLayout;
    List<String> patient_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_draw);
        navController = Navigation.findNavController(this,R.id.nav_host_fragment);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).setDrawerLayout(drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);

        patient_ids = new ArrayList<>();

        model = new ViewModelProvider(this).get(SharedViewModel.class);
        databasePatients = FirebaseDatabase.getInstance().getReference("patients");
        createchannel();
        handler = new Handler();
        update();
        handler.postDelayed(new Runnable(){
            public void run(){
                patient_ids.clear();
                update();
                handler.postDelayed(this, 1000*60*5);
            }
        }, 1000*60*5);



    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,drawerLayout);
    }


    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
        super.onBackPressed();
    }
    }

    public void simpleNoti(Patient patient) {

        String message = patient.getName()+" ID : "+patient.getID()+" is not checked";
        Intent viewIntent = new Intent(this, NotiActivity.class);
        viewIntent.putExtra("NotiID", message);

        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(getApplicationContext(), 0, viewIntent, 0);

        //Now create the notification.  We must use the NotificationCompat or it will not work on the wearable.
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), id)
                        .setSmallIcon(R.drawable.ic_status_red)
                        .setContentTitle("6h Alert")
                        .setContentText(message)
                        .setChannelId(id)
                        .setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getApplicationContext());

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationID, notificationBuilder.build());
        notificationID++;
    }

    private void createchannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
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


    public void update(){
        List<Patient> patients1 = new ArrayList<>();
        databasePatients.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot patientSnapshot:dataSnapshot.getChildren()){
                    Patient patient = patientSnapshot.getValue(Patient.class);
                    if(patient.getDoctor_ID().equals(model.Doctor_ID)){
                        patients1.add(patient);
                        Date date = null;
                        try {
                            date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(patient.getLastUpdate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        Date now = new Date();
                        long diff = now.getTime()-date.getTime();

                        long diffMinutes = diff / (60 * 1000);

                        if (diffMinutes>60*6) {
                            if (!patient_ids.contains(patient.getID())){
                                simpleNoti(patient);
                                patient_ids.add(patient.getID());
                            }

                        }
                    }
                }
                model.patients.clear();

                model.patients=patients1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

}
