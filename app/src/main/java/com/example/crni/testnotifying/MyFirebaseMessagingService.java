package com.example.crni.testnotifying;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN), token);
        editor.apply();

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        Bundle notify = new Bundle();
        notify.putString("TITLE", title);
        notify.putString("MESSAGE", message);

        Intent intent = new Intent(this, AlarmActivity.class);
        intent.putExtra("NOTIFY", notify);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //TODO Ovo bi moglo da se napise malo bolje
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBilder = new NotificationCompat.Builder(this);
        notificationBilder.setContentTitle(title);
        notificationBilder.setContentText(message);
        notificationBilder.setSmallIcon(R.mipmap.ic_app_icon);
        notificationBilder.setAutoCancel(true);
        notificationBilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBilder.build());
    }
}


