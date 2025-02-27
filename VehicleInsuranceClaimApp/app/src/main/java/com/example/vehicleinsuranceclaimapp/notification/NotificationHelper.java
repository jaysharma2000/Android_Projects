package com.example.vehicleinsuranceclaimapp.notification;

import static android.os.Build.VERSION.SDK_INT;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.vehicleinsuranceclaimapp.R;
import com.example.vehicleinsuranceclaimapp.fragment.DisplayClaimsFragment;
import com.example.vehicleinsuranceclaimapp.fragment.UpdateFragment;

public class NotificationHelper {

    private static final String CHANNEL_ID = "claim_status_channel";
    public static void createNotificationChannel(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Claim Status Updates";
            String description = "Notifications for claim status updates.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void sendNotification(Context context, String claimStatus){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, UpdateFragment.class), PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Claim Status Update")
                .setContentText("Your claim is now "+ claimStatus)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        notificationManager.notify(0, builder.build());
    }
}

