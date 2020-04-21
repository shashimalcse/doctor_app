package com.s17131890.carelite;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotiActivity extends AppCompatActivity {
    String info;
    TextView logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            info = "No information";
        } else {
            info = extras.getString("NotiID");
        }
        logger = findViewById(R.id.logger);
        logger.setText(info);
    }

}

