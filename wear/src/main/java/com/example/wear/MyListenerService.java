package com.example.wear;

import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

public class MyListenerService extends WearableListenerService {

    String TAG = "wear listener";
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        if (messageEvent.getPath().equals("/message_path")) {
            final String message = new String(messageEvent.getData());
            Log.d(TAG, "Message path received is: " + messageEvent.getPath());
            Log.d(TAG, "Message received is: " + message);
        }
        else {
            super.onMessageReceived(messageEvent);
        }
    }
}
